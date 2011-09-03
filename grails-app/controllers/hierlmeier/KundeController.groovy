package hierlmeier

import grails.converters.JSON

class KundeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"
    
    enum DataTableFilter {  // filter for the dataTableJSON method, filter is set in the view and submitted by the ajax call
        NONE("kunde.list.filter.NONE"),
        UPP("kunde.list.filter.UPP")

        private final String value //value is a message.properties code
        DataTableFilter(String value) { this.value = value }
        String toString() { value }
        String getKey() { name() }
    }
    
    def index = {
        redirect(action: "list", params: params)
    }

    def dataTableJSON = {
        println("****** $controllerName.$actionName START")
        println("params: " + params)
        
        def results = Kunde.list()
        println("results Class: " + results.getClass().toString())
        println("db query results: " + results)
        def foundRecords = Kunde.count()
        
        println("foundRecords: " + foundRecords)
        
        /*
        def formattedResults = results.collect {
            [
                it.nachname,
                it.bemerkung,
                it.adresse,
                it.wohnort,
                it.mwst,
                it.telefonnummer
            ]
        }
        def data = [aaData: formattedResults]
        */
        def data = [aaData: results]
        
        println("data before JSON rendering: " + data)
        println("****** $controllerName.$actionName END")
        
        render data as JSON
    }
    
    
    def list = {
        def filters = []
        for (filter in DataTableFilter.values()) {
            filters.add(filter)
        }
	[dt_filters: filters]
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
