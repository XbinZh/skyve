package org.skyve.impl.web;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.skyve.EXT;
import org.skyve.content.AttachmentContent;
import org.skyve.content.ContentManager;
import org.skyve.content.MimeType;
import org.skyve.impl.bind.BindUtil;
import org.skyve.impl.domain.messages.SecurityException;
import org.skyve.impl.metadata.repository.AbstractRepository;
import org.skyve.impl.metadata.view.DownloadAreaType;
import org.skyve.impl.persistence.AbstractPersistence;
import org.skyve.impl.util.UtilImpl;
import org.skyve.impl.web.AbstractWebContext;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.metadata.user.User;
import org.skyve.util.Binder.TargetMetaData;
import org.skyve.util.Util;
import org.skyve.web.WebContext;
		
public class CustomerResourceServlet extends HttpServlet {
	/**
	 * For Serialization
	 */
	private static final long serialVersionUID = 1L;

	protected final class Resource {
		private ContentManager cm;
		private AttachmentContent content;
		private File file;
		int imageWidth = 0;
		int imageHeight = 0;

		void dispose() throws Exception {
			if (cm != null) {
				cm.close();
				cm = null;
			}
		}

		public long getLastModified() {
			long result = -1;

			if (file != null) {
				result = file.lastModified();
			}
			else if (content != null) {
				result = content.getLastModified().getTime();
			}

			return result;
		}

		public byte[] getBytes() 
		throws FileNotFoundException, IOException {
			byte[] result = null;

			if (file != null) {
				try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
					// A thumbnail image
					if ((imageWidth > 0) && (imageHeight > 0)) {
						BufferedImage image = ImageIO.read(bis);
						image = Thumbnails.of(image).size(imageWidth, imageHeight).keepAspectRatio(true).asBufferedImage();
						try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
							Thumbnails.of(image).scale(1.0).outputFormat("png").toOutputStream(baos);
							result = baos.toByteArray();
						}
					}
					// Full content
					else {
						try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
							result = new byte[1024]; // 1K
							int bytesRead = 0;
							while ((bytesRead = bis.read(result)) > 0) {
								baos.write(result, 0, bytesRead);
							}
							result = baos.toByteArray();
						}
					}
				}
			}
			else if (content != null) {
				// A thumbnail image
				if ((imageWidth > 0) && (imageHeight > 0)) {
					try (InputStream stream = content.getContentStream()) {
						BufferedImage image = ImageIO.read(stream);
						image = Thumbnails.of(image).size(imageWidth, imageHeight).keepAspectRatio(true).asBufferedImage();
						try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
							Thumbnails.of(image).scale(1.0).outputFormat("jpg").toOutputStream(baos);
							result = baos.toByteArray();
						}
					}
				}
				// Full content
				else {
					result = content.getContentBytes();
				}
			}

			return result;
		}

		public boolean isContent() {
			return (content != null);
		}
		
		public AttachmentContent getContent() {
			return content;
		}
		
		public File getFile() {
			return file;
		}

		public String getContentType() {
			String result = null;

			if ((imageWidth > 0) && (imageHeight > 0)) {
				result = MimeType.png.toString();
			}
			else if (file != null) {
				MimeType mimeType = MimeType.fromFileName(file.getName());
				if (mimeType != null) {
					result = mimeType.toString();
				}
			}
			else if (content != null) {
				result = content.getContentType();
			}
			
			return result;
		}
		
		public String getFileName() {
			String result = null;
			
			if ((imageWidth > 0) && (imageHeight > 0)) {
				result = "thumbnail.png";
			}
			else if (file != null) {
				result = file.getName();
			}
			else if (content != null) {
				result = content.getFileName();
			}

			return result;
		}

		Resource(HttpServletRequest request)
		throws Exception {
			String resourceArea = request.getServletPath();
			if (resourceArea.startsWith("/images/")) { // SC looks for images in /images
				resourceArea = resourceArea.substring(8); // get rid of slash at front
			}
			else {
				resourceArea = resourceArea.substring(1); // get rid of slash at front
			}
			
			String documentName = request.getParameter(AbstractWebContext.DOCUMENT_NAME);
			String moduleName = null;
			if ((documentName == null) || (documentName.length() == 0)) {
				System.err.println("No modoc in the URL");
			}
			else {
				int dotIndex = documentName.indexOf('.');
				moduleName = documentName.substring(0, dotIndex);
				documentName = documentName.substring(dotIndex + 1);
			}
			String binding = request.getParameter(AbstractWebContext.BINDING_NAME);
			String resourceFileName = request.getParameter(AbstractWebContext.RESOURCE_FILE_NAME);

			try {
				String imageWidthParam = UtilImpl.processStringValue(request.getParameter(DynamicImageServlet.IMAGE_WIDTH_NAME));
				if (imageWidthParam != null) {
					imageWidth = Integer.parseInt(imageWidthParam);
				}
				String imageHeightParam = UtilImpl.processStringValue(request.getParameter(DynamicImageServlet.IMAGE_HEIGHT_NAME));
				if (imageWidthParam != null) {
					imageHeight = Integer.parseInt(imageHeightParam);
				}
			}
			catch (@SuppressWarnings("unused") NumberFormatException e) {
				imageWidth = 0;
				imageHeight = 0;
				System.err.println("Width/Height is malformed in the URL");
			}
			
			if ((resourceFileName == null) || (resourceFileName.length() == 0)) {
				System.err.println("No resource file name or data file name in the URL");
			}
			else {
				User user = (User) request.getSession().getAttribute(WebContext.USER_SESSION_ATTRIBUTE_NAME);
				Customer customer = null;
				String customerName = null;
				if (user != null) { // we are logged in
					customer = user.getCustomer();
					customerName = customer.getName();
					AbstractPersistence.get().setUser(user);
				}
				else { // not logged in
					// Get the customer name if it is in a cookie from the HomeServlet
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (int i = 0, l = cookies.length; i < l; i++) {
							Cookie cookie = cookies[i];
							if (AbstractWebContext.CUSTOMER_COOKIE_NAME.equals(cookie.getName())) {
								customerName = cookie.getValue();
								break;
							}
						}
					}
				}

				if (DownloadAreaType.content.toString().equals(resourceArea)) {
					if ((user != null) && (customer != null)) {
						cm = EXT.newContentManager();
						content = cm.get(resourceFileName);
					}
				} 
				else if (DownloadAreaType.resources.toString().equals(resourceArea)) {
					AbstractRepository repository = AbstractRepository.get();
					File tempFile = repository.findResourceFile(resourceFileName, customerName, moduleName);
					if (tempFile.exists()) {
						file = tempFile;
					}
					else {
						int underscoreIndex = resourceFileName.lastIndexOf('_');
						if (underscoreIndex > 0) {
							int dotIndex = resourceFileName.lastIndexOf('.');
							if (dotIndex > underscoreIndex) {
								String baseFileName = resourceFileName.substring(0, underscoreIndex) + 
														resourceFileName.substring(dotIndex);
								tempFile = repository.findResourceFile(baseFileName, customerName, moduleName);
								if (tempFile.exists()) {
									file = tempFile;
								}
							}
						}
					}
				} 
				else {
					throw new IllegalStateException("Unsupported resource area " + resourceArea);
				}
				CustomerResourceServlet.this.secure(this, moduleName, documentName, binding, resourceFileName, user);
			}
		}
	}

	/**
	 * Used to hold the agent per thread
	 */
	private static final ThreadLocal<Resource> RESOURCES = new ThreadLocal<>();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try {
			super.service(request, response);
		}
		finally {
			Resource resource = RESOURCES.get();
			if (resource != null) {
				try {
					resource.dispose();
				}
				catch (Exception e) {
					UtilImpl.LOGGER.severe("Could not dispose of the thread-local content resource properly.  It has been removed from the thread local storage.");
					e.printStackTrace();
				}
				finally {
					RESOURCES.remove();
				}
			}
		}
	}

	@Override
	protected long getLastModified(HttpServletRequest request) {
		try {
			Resource resource = RESOURCES.get();
			if (resource == null) {
				resource = new Resource(request);
				RESOURCES.set(resource);
			}

			return resource.getLastModified();
		} 
		catch (Exception e) {
			System.err.println("Problem getting the customer resource - " + e.toString());
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			Resource resource = RESOURCES.get();
			if (resource == null) {
				resource = new Resource(request);
				RESOURCES.set(resource);
			}

			String contentType = resource.getContentType();
			if (contentType != null) {
				response.setContentType(contentType);
			}
			response.setCharacterEncoding(Util.UTF8);
			if (resource.isContent()) {
				StringBuilder disposition = new StringBuilder(32);
				disposition.append("inline; filename=\"");
				disposition.append(resource.getFileName());
				disposition.append('"');
				response.setHeader("Content-Disposition", disposition.toString());
			}
			
			byte[] bytes = resource.getBytes();
			if (bytes == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				System.err.println(String.format("Problem getting the customer resource - %s was not found.", resource.getFileName()));
				return;
			}
	
			response.setContentLength(bytes.length);
			try (OutputStream out = response.getOutputStream()) {
				if (resource.isContent()) {
					// NOTE - the image is not cached unless there is a content length, and the header following headers
					// NOTE - THIS MUST BE SET FIRST BEFORE WRITING TO THE STREAM
					response.setHeader("Cache-Control", "cache");
			        response.setHeader("Pragma", "cache");
			        response.addDateHeader("Expires", System.currentTimeMillis() + (60000)); // 1 minute
				}

				out.write(bytes, 0, bytes.length);
				out.flush();
			}
		} 
		catch (SecurityException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			System.err.println("Problem getting the customer resource - " + e.toString());
			e.printStackTrace();
		}
		catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			System.err.println("Problem getting the customer resource - " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * Throws SecurityException if the resource should not be served.
	 * 
	 * This implementation checks that a request for content is from an authenticated user
	 * who has privileges to access the that content.
	 * There is no securing of file requests.
	 * 
	 * @param resource	The file or content resource found - never null.
	 * @param moduleName	The module name in the request - never null.
	 * @param documentName	The document name in the request - never null.
	 * @param binding	The binding in the request - can be null.
	 * @param resourceFileName	The file/content identifier - never null.
	 * @param user	The logged in user or null if not logged in
	 * @param intendedCustomerName	The customer name from a customer cookie (if no principal).
	 * @throws SecurityException
	 */
	@SuppressWarnings({"static-method", "unused"})
	protected void secure(Resource resource, 
							String moduleName, 
							String documentName, 
							String binding, 
							String resourceFileName,
							User user)
	throws SecurityException {
		// Content can only be accessed if we have an authenticated user that has access
		if (resource.isContent()) {
			if (user == null) {
				throw new SecurityException(moduleName + '.' + documentName + '.' + binding, "anonymous");
			}
			Customer customer = user.getCustomer();
			Module module = customer.getModule(moduleName);
			Document document = module.getDocument(customer, documentName);
			TargetMetaData target = BindUtil.getMetaDataForBinding(customer,
																	module,
																	document,
																	binding);
			// Check that the user has access
			AttachmentContent content = resource.getContent();
			if (! user.canAccessContent(content.getBizId(),
											content.getBizModule(),
											content.getBizDocument(),
											content.getBizCustomer(),
											content.getBizDataGroupId(),
											content.getBizUserId(),
											target.getAttribute().getName())) {
				throw new SecurityException(moduleName + '.' + documentName + '.' + binding, user.getName());
			}
		}
	}
}
