package hierlmeier   //Maybe NetBeans shows an java.lang.Enum related error here (IDE Bug)

import grails.converters.JSON

class PositionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", dataTableJSON:"POST"]
    static defaultAction = "index"
        
    enum Filter {  // possible filters for the dataTableJSON method, filter is set in the view and submitted by the ajax call
        NOFILTER("filter.NOFILTER"),
        UPP("filter.UPP")

        private final String value //value is a message.properties code
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
        
        if(params.filter == g.message(code: Filter.NOFILTER.value())) {
            if(params.kundeId) {
                def kunde = Kunde.get(params.kundeId)
                results = Position.findAllByKunde(kunde)
            }
            if(params.belegId) {
                def beleg = Beleg.get(params.belegId)
                results = Position.findAllByBeleg(beleg)
            }
            else {
                results = Position.list()
            }
        }
        else if(params.filter == g.message(code: Filter.UPP.value())) {
            if(params.kundeId) {
                def kunde = Kunde.get(params.kundeId)
                results = Position.findAllByKundeAndBelegIsNull(kunde)
            }
            else {
                results = Position.findAllByBelegIsNull();
            }
        }
        else {
            println("** params.filter not set or invalid value, showing all for $controllerName")
            flash.message = "Filter not found. Showing all records (same as 'Filter.NOFILTER')."  //@todo message code dafür fehlt
            results = Position.list()
        }

        foundRecords = results.size();
        
        println("** results Class: " + results.getClass().toString())
        println("** foundRecords: " + foundRecords)
        println("** db query results: " + results)
        
        def data
        JSON.use("deep"){ data = [aoData: results] as JSON }
        
        println("** data after JSON rendering: " + data)
        
        println("**** $controllerName.$actionName END")
        render data
    }  
    
    def dataTableJSONByKunde = {
        println("**** $controllerName.$actionName START")
        println("params: " + params)
        
        def kunde = Kunde.get(params.kundeId)
        
        /*
        def criteria = Positionen.createCriteria()
        def poslist = criteria.listDistinct {
            isNotEmpty("positionen")
            positionen {
                isNull("beleg")
            }
        }
        */
        
        def positionen = Position.findAllByKunde(kunde, params)
        def foundRecords = Position.countByKunde(kunde)
        
        println("foundRecords: " + foundRecords)
        
        def formattedResults = positionen.collect {
            [
                datum: new java.text.SimpleDateFormat(message(code:"default.date.format")).format(it.datum),
                typ: it.typ.toString(),
                tier: it.tier.toString(),
                menge: it.menge.toString(),
                beleg: it.beleg.toString()
            ]
        }
        
        def data = [
            totalRecords: foundRecords,
            results: formattedResults
        ]
        
        println("db query results: " + positionen)
        println("JSON: " + data)
        println("**** $controllerName.$actionName END")
        
        render data as JSON
    }
    
    def dataTableJSONByBeleg = {
        println("**** $controllerName.$actionName START")
        println("params: " + params)
        
        def beleg = Beleg.get(params.belegId)
        
        /*
        def criteria = Positionen.createCriteria()
        def poslist = criteria.listDistinct {
        isNotEmpty("positionen")
        positionen {
        isNull("beleg")
        }
        }
         */
        
        def positionen = Position.findAllByBeleg(beleg, params)
        def foundRecords = Position.countByBeleg(beleg)
        
        println("foundRecords: " + foundRecords)
        
        def formattedResults = positionen.collect {
            [
                datum: new java.text.SimpleDateFormat(message(code:"default.date.format")).format(it.datum),
                typ: it.typ.toString(),
                tier: it.tier.toString(),
                menge: it.menge.toString(),
                beleg: it.beleg.toString()
            ]
        }
        
        def data = [
            totalRecords: foundRecords,
            results: formattedResults
        ]
        
        println("db query results: " + positionen)
        println("JSON: " + data)
        println("**** $controllerName.$actionName END")
        
        render data as JSON
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[positionInstanceList: Position.list(params), positionInstanceTotal: Position.count()]
    }

    def create = {
        def positionInstance = new Position()
        positionInstance.properties = params
        return [positionInstance: positionInstance]
    }

    def save = {
        def positionInstance = new Position(params)
        if (positionInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'position.label', default: 'Position'), positionInstance.id])
            redirect(action: "show", id: positionInstance.id)
        }
        else {
            render(view: "create", model: [positionInstance: positionInstance])
        }
    }

    def show = {
        def positionInstance = Position.get(params.id)
        if (!positionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'position.label', default: 'Position'), params.id])
            redirect(action: "list")
        }
        else {
            [positionInstance: positionInstance]
        }
    }

    def edit = {
        def positionInstance = Position.get(params.id)
        if (!positionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'position.label', default: 'Position'), params.id])
            redirect(action: "list")
        }
        else {
            return [positionInstance: positionInstance]
        }
    }

    def update = {
        def positionInstance = Position.get(params.id)
        if (positionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (positionInstance.version > version) {
                    
                    positionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'position.label', default: 'Position')] as Object[], "Another user has updated this Position while you were editing")
                    render(view: "edit", model: [positionInstance: positionInstance])
                    return
                }
            }
            positionInstance.properties = params
            if (!positionInstance.hasErrors() && positionInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'position.label', default: 'Position'), positionInstance.id])
                redirect(action: "show", id: positionInstance.id)
            }
            else {
                render(view: "edit", model: [positionInstance: positionInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'position.label', default: 'Position'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def positionInstance = Position.get(params.id)
        if (positionInstance) {
            try {
                positionInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'position.label', default: 'Position'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'position.label', default: 'Position'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'position.label', default: 'Position'), params.id])
            redirect(action: "list")
        }
    }
}
