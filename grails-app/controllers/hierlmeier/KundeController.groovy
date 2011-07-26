package hierlmeier

import grails.converters.JSON

class KundeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
        Kunde.count()
	[kundeInstanceList: Kunde.list(params), kundeInstanceTotal: Kunde.count()]
    }
    
    def listBelegCanditates = {
        def criteria = Kunde.createCriteria()
        def results = criteria.listDistinct {
            positionen {
                isNull("beleg")
            }
            //@todo order("nachname", "asc")
        }
        
        return [applicableKundenList:results]
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
