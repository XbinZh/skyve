package org.skyve.impl.web.faces.converters.timestamp;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.skyve.domain.types.Timestamp;
import org.skyve.impl.util.UtilImpl;

public class DD_MMM_YYYY extends org.skyve.domain.types.converters.timestamp.DD_MMM_YYYY implements Converter {
	@Override
	public Object getAsObject(FacesContext fc, UIComponent component, String value) {
    	String processedValue = UtilImpl.processStringValue(value);
    	if (processedValue != null) {
			try {
				return fromDisplayValue(value);
			}
			catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
																"Invalid date/time (use DD-MON-YYYY format)",
																"Invalid date/time (use DD-MON-YYYY format)"),
												e);
			}
    	}
    	return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent component, Object value) {
		try {
			return toDisplayValue((Timestamp) value);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return null;
		}
	}
}
