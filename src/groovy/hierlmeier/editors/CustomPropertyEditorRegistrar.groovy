package hierlmeier.editors

import java.text.SimpleDateFormat
import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry

import org.springframework.beans.propertyeditors.CustomDateEditor

import org.springframework.context.i18n.LocaleContextHolder; 


class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
    def messageSource; 

    void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(messageSource.getMessage("default.date.format", null, "dd.MM.yyyy", LocaleContextHolder.locale)), false));
        registry.registerCustomEditor(BigDecimal.class, new CustomBigDecimalEditor());
    }
}