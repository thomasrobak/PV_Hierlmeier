package hierlmeier

import grails.converters.JSON

class PositionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }
    
    def dataTableJSONByKunde = {
        println("****** Position.dataTableJSONForKunde() START")
        println("params: " + params)
        
        def kunde = Kunde.get(params.kundeid)
        
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
        
        def formattedPositionen = positionen.collect {
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
            results: formattedPositionen
        ]
        
        println("db query results: " + positionen)
        println("JSON: " + data)
        println("****** Position.dataTableJSONForKunde() END")
        
        render data as JSON
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[positionInstanceList: Position.list(params), positionInstanceTotal: Position.count()]
    }
    
    def listBelegCanditates = {
        def criteria = Position.createCriteria()
        def results = criteria.list {
            isNotEmpty("positionen")
            positionen {
                isNull("beleg")
            }
            //@todo order("nachname", "asc")
        }
        
        return results
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
