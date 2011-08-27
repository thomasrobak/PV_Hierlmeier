import org.springframework.context.i18n.LocaleContextHolder as LCH

/* Zum Filtern aller Requests die ein Datum enthalten. 
 * Wenn Datum == String, konvertieren in Date()
 * JQuery Datepicker schickt zB Strings, app erwartet aber Date()
 */
class DatumFilters {
    def messageSource  // for fetching the default.date.format from i18n message.properties
    
    def filters = {
        saveInActionName(action:'save', find:true) {
            before = {
                println("******* DatumFilters.saveInActionName caught: $controllerName.$actionName")
                println("***** DatumFilters.saveInActionName.before START")
                println("** params: " + params)
                if(params.datum) {
                    println("** params.datum found: " + params.datum.getClass().toString() + ": " + params.datum)
                    if(params.datum instanceof java.lang.String) {
                        println("** params.datum is a java.lang.String!")
                        try {
                            def expectedDateFormat =  messageSource.getMessage("default.date.format", null, LCH.getLocale())
                            println("** trying to convert params.datum to java.util.Date using this parseFormat: " + expectedDateFormat)
                            params.datum = Date.parse(expectedDateFormat, params.datum)
                        } catch (Exception e) {
                            println("** Exception ocurred during conversion:")
                            println(e)
                            println("***** DatumFilters.saveInActionName.before END")
                            return
                        }
                        println("** conversion successful: " + params.datum.getClass().toString() + ": " + params.datum)
                    }                    
                }
                println("***** DatumFilters.saveInActionName.before END")
            }
        }
        // more filters here
    }
    
}

