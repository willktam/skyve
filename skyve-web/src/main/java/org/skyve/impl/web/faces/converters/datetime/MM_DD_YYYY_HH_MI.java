package org.skyve.impl.web.faces.converters.datetime;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.skyve.domain.types.DateTime;
import org.skyve.impl.util.UtilImpl;

public class MM_DD_YYYY_HH_MI extends org.skyve.domain.types.converters.datetime.MM_DD_YYYY_HH_MI implements Converter {
	@Override
	public Object getAsObject(FacesContext fc, UIComponent component, String value) {
    	String processedValue = UtilImpl.processStringValue(value);
    	if (processedValue != null) {
			try {
				return fromDisplayValue(processedValue);
			}
			catch (Exception e) {
				String message = String.format("Invalid date/time (use %s format)",
						org.skyve.domain.types.converters.datetime.MM_DD_YYYY_HH_MI.PATTERN);
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message), e);
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent component, Object value) {
		try {
			return toDisplayValue((DateTime) value);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return null;
		}
	}
}
