package org.skyve.impl.web.faces;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.skyve.impl.web.faces.beans.FacesView;

public class FacesUtil {
	/**
	 * Prevent instantiation
	 */
	private FacesUtil() {
		// nothing to see here
	}

	// used to place a bean temporarily in the session when a redirect is done during navigate
	public static String MANAGED_BEAN_NAME_KEY = "skyveFacesBean";
	// used to place the uxui (renderer) at play during this request as a request attribute
	public static String UX_UI_KEY = "skyveUxUi";
	// used to place the user agent type of the requesting as a request attribute
	public static String USER_AGENT_TYPE_KEY = "skyveUserAgentType";
	// used to get the responsive form grid out of the view root when required
	public static String FORM_STYLES_KEY = "skyveFormStyles";
	
	public static FacesView<?> getManagedBean(final String beanName) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesView<?> result;

		ELContext elContext = fc.getELContext();
		result = (FacesView<?>) elContext.getELResolver().getValue(elContext, null, beanName);

		if (result == null) {
			throw new FacesException("Managed bean with name '" + beanName + "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
		}

		return result;
	}

	public static void set(final Object value, final String valueExpression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELContext elContext = facesContext.getELContext();
		ExpressionFactory ef = facesContext.getApplication().getExpressionFactory();

		ValueExpression targetExpression = ef.createValueExpression(elContext, valueExpression, Object.class);
		targetExpression.setValue(elContext, value);
	}

	public static MethodExpression createMethodExpression(String expression,
															Class<?> expectedReturnType,
															Class<?>[] expectedParamTypes) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExpressionFactory factory = fc.getApplication().getExpressionFactory();
			return factory.createMethodExpression(fc.getELContext(),
													expression,
													expectedReturnType,
													expectedParamTypes);
		}
		catch (Exception e) {
			throw new FacesException("Method expression '" + expression + "' could not be created.");
		}
	}
	
	public static String xmlPartialRedirect(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<partial-response><redirect url=\"").append(url.replace("&", "&amp;")).append("\"/></partial-response>");
		return sb.toString();
	}

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    public static void jsRedirect(String url) {
		RequestContext.getCurrentInstance().execute(String.format("window.location='%s'", url));
    }
}
