package modules.admin.Configuration;

import modules.admin.domain.Configuration;
import modules.admin.domain.Contact;
import modules.admin.domain.User;

import org.skyve.CORE;
import org.skyve.domain.Bean;
import org.skyve.metadata.controller.ImplicitActionName;
import org.skyve.metadata.model.document.Bizlet;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;
import org.skyve.util.Binder;
import org.skyve.web.WebContext;

public class ConfigurationBizlet extends Bizlet<Configuration> {
	/**
	 * For Serialization
	 */
	private static final long serialVersionUID = -1282437688681930236L;
	
	public static final String SYSTEM_USER_INVITATION = "SYSTEM User Invitation";
	public static final String SYSTEM_USER_INVITATION_DEFAULT_SUBJECT = "Invitation to join";
	public static final String SYSTEM_USER_INVITATION_DEFAULT_BODY = "Hi {contact.name}, a user account has been created for you.";
	
	
	@Override
	public Configuration newInstance(Configuration bean) throws Exception {
		Persistence persistence = CORE.getPersistence();
		DocumentQuery q = persistence.newDocumentQuery(Configuration.MODULE_NAME, Configuration.DOCUMENT_NAME);
		Configuration result = q.beanResult();
		if (result == null) {
			result = bean;
		}

		if (result.getPasswordComplexityModel() == null) {
			result.setPasswordComplexityModel(ComplexityModel.DEFAULT_COMPLEXITY_MODEL);
		}
		if (result.getFromEmail() == null) {
			result.setFromEmail("mailer@bizhub.com.au");
		}
		if (result.getPasswordResetEmailSubject() == null) {
			result.setPasswordResetEmailSubject("Password Reset");
		}
		if (result.getPasswordResetEmailBody() == null) {
			String body = String.format("<html><head/><body>Hi {%s},<p/>" +
												"Please click below to reset you password.<p/>" +
												"<a href=\"{url}/pages/resetPassword.jsp?t={%s}\">" +
												"Reset Password</a></body></html>",
											Binder.createCompoundBinding(User.contactPropertyName, Contact.namePropertyName),
											User.passwordResetTokenPropertyName);
			result.setPasswordResetEmailBody(body);
		}

		return result;
	}

	@Override
	public Configuration preExecute(ImplicitActionName actionName, Configuration bean, Bean parentBean, WebContext webContext) throws Exception {
		
		if(ImplicitActionName.Save.equals(actionName) || ImplicitActionName.OK.equals(actionName)) {
			if(bean.getUserSelfRegistrationGroup()==null) {
				bean.setAllowUserSelfRegistration(Boolean.FALSE);
			}
		}
		
		return super.preExecute(actionName, bean, parentBean, webContext);
	}
	
	
}
