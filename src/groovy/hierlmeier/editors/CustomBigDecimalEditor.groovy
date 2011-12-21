package hierlmeier.editors

import java.beans.PropertyEditorSupport
import java.text.ParseException
import java.text.NumberFormat
import java.text.DecimalFormat

import org.springframework.context.i18n.LocaleContextHolder; 


class CustomBigDecimalEditor extends PropertyEditorSupport {
    def outputformat
    def inputformat
    
    CustomBigDecimalEditor() {
        outputformat = DecimalFormat.getInstance(LocaleContextHolder.locale)
        outputformat.setGroupingUsed(false)
        outputformat.setMinimumFractionDigits(2)
        
        inputformat = DecimalFormat.getInstance(Locale.GERMAN)
        inputformat.setParseBigDecimal(true)
    }

    
    //Parse the Currency Number from the given text
    void setAsText(String text) throws IllegalArgumentException {
        text = text.trim()
        if(text.length() == 0) {
            throw new IllegalArgumentException("*!*!* CustomBigDecimalEditor empty input string")
            return
        }
        boolean haspoint = text.contains('.')
        boolean hascomma = text.contains(',')
        if(haspoint && hascomma) {
            throw new IllegalArgumentException("*!*!* CustomBigDecimalEditor input string contained both decimalseparator signs: " + text)
            return
        }
        BigDecimal parsed
        if(haspoint || !hascomma) {
            parsed = new BigDecimal(text)
            this.setValue(parsed)
            return
        }
        if(hascomma) {
            try {
                parsed = inputformat.parse(text)
                setValue(parsed)
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("*!*!* CustomBigDecimalEditor could not parse input string: " + ex.getMessage(), ex)   
            }
        }
    }
    
    // Format the Currency Number as String, using the specified format
    String getAsText() {
        def number = getValue()
        outputformat.format(number)
    }

    void setValue(Object value) {
        if(value.scale() < 2)
        value = value.setScale(2)  //@todo rounding mode missing?
        super.setValue(value)
    }
}