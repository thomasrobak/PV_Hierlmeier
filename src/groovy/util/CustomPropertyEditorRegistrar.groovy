package util

import java.beans.PropertyEditorSupport
import java.text.ParseException
import org.apache.commons.lang.time.DateUtils
import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry

class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
	def dateEditor

	void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Date.class, dateEditor)
	}
}

        //@todo JQuery DatePicker doesnt change DateFormat according to locale, check this
class CustomDateEditor extends PropertyEditorSupport {
	boolean allowEmpty
	String[] formats

	/**
	 * Parse the Date from the given text
	 */
	void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !text) {
			// Treat empty String as null value.
			setValue(null)
		}
		else {
			try {
				setValue(DateUtils.parseDate(text, formats))
			}
			catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex)
			}
		}
	}

	/**
	 * Format the Date as String, using the first specified format
	 */
	String getAsText() {
		def val = getValue()
		val?.respondsTo('format') ? val.format(formats[0]) : ''
	}
}