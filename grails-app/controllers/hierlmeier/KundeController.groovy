package hierlmeier   //Maybe NetBeans shows an java.lang.Enum related error here (IDE Bug)

import grails.converters.JSON

class KundeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", dataTableJSON: "POST"]
    static defaultAction = "index"
    
    enum Filter {  //possible filters for the dataTableJSON method, filter is set in the view and submitted by the ajax call
        NOFILTER("filter.NOFILTER"),    //value is a message.properties code (no filter at all)
        UPP("filter.UPP"),              //value is a message.properties code (unprocessed positionen)
        NPB("filter.NPB")               //value is a message.properties code (has not paid belege)

        private final String value 
        Filter(String value) { this.value = value }
        String toString() { value }
        String value() { value }
        String getKey() { name() }
    }
    
    def index = {
        redirect(action: "list", params: params)
    }

    def dataTableJSON = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def results
        def foundRecords
        
        if(params.filter) {
            if(params.filter == g.message(code: Filter.NOFILTER.value())) {
                results = Kunde.list()  
            }
            else if(params.filter == g.message(code: Filter.UPP.value())) {
                results = Kunde.withUnprocessedPositionen.listDistinct(); //listDistinct als projection in der namedquery behandeln
            }
            else if(params.filter == g.message(code: Filter.NPB.value())) {
                results = Kunde.withUnpaidBelege.listDistinct();
            }
        }
        else {
            println("** params.filter not set or invalid value, showing all for $controllerName")
            //@todo flash.message funzt net mit ajax, js code nötig dafür
            flash.message = "Filter not found. Showing all records (same as 'Filter.NOFILTER')."  //@todo message code dafür fehlt
            results = Kunde.list()
        }
        
        foundRecords = results.size();
        println("** results Class: " + results.getClass().toString())
        println("** foundRecords: " + foundRecords)
        println("** db query results: " + results)

        def data = [aoData: results]
        
        println("** data before JSON rendering: " + data)
                
        println("**** $controllerName.$actionName END")
        render data as JSON
    }
    
    def list = {
	return [InstanceFilters: Filter.values()]
    }
    
    def create = {
        def kundeInstance = new Kunde()
        kundeInstance.properties = params
        return [kundeInstance: kundeInstance]
    }

    def save = {
        def kundeInstance = new Kunde(params)
        if (kundeInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'kunde.label', default: 'Kunde'), kundeInstance.id])
            redirect(action: "show", id: kundeInstance.id)
        }
        else {
            render(view: "create", model: [kundeInstance: kundeInstance])
        }
    }

    def show = {
        def kundeInstance = Kunde.get(params.id)
        if (!kundeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'kunde.label', default: 'Kunde'), params.id])
            redirect(action: "list")
        }
        else {
            [kundeInstance: kundeInstance]
        }
    }

    def edit = {
        def kundeInstance = Kunde.get(params.id)
        if (!kundeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'kunde.label', default: 'Kunde'), params.id])
            redirect(action: "list")
        }
        else {
            return [kundeInstance: kundeInstance]
        }
    }

    def update = {
        def kundeInstance = Kunde.get(params.id)
        if (kundeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (kundeInstance.version > version) {
                    
                    kundeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'kunde.label', default: 'Kunde')] as Object[], "Another user has updated this Kunde while you were editing")
                    render(view: "edit", model: [kundeInstance: kundeInstance])
                    return
                }
            }
            kundeInstance.properties = params
            if (!kundeInstance.hasErrors() && kundeInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'kunde.label', default: 'Kunde'), kundeInstance.id])
                redirect(action: "show", id: kundeInstance.id)
            }
            else {
                render(view: "edit", model: [kundeInstance: kundeInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'kunde.label', default: 'Kunde'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def kundeInstance = Kunde.get(params.id)
        if (kundeInstance) {
            try {
                kundeInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'kunde.label', default: 'Kunde'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'kunde.label', default: 'Kunde'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'kunde.label', default: 'Kunde'), params.id])
            redirect(action: "list")
        }
    }
}
