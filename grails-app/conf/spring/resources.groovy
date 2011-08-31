// Place your Spring DSL code here
beans = {
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("de","DE")
        java.util.Locale.setDefault(defaultLocale)
    }
    customPropertyEditorRegistrar(util.CustomPropertyEditorRegistrar) {
        dateEditor = { util.CustomDateEditor e ->
            formats = [
                'MM/dd/yyyy',
                'dd.MM.yyyy'
                //messageSource.getMessage("default.date.format", null, new Locale("de","DE")), 
                //messageSource.getMessage("default.date.format", null, new Locale("en","GB"))
            ]
            allowEmpty = true
        } 
    }
}

