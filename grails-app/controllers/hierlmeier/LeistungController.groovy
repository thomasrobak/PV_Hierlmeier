package hierlmeier

import grails.converters.JSON

class LeistungController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
    	params.max = Math.min(params.max ? params.int('max') : 10, 100)
    	[leistungInstanceList: Leistung.list(params), leistungInstanceTotal: Leistung.count()]
    }

    def create = {
        def leistungInstance = new Leistung()
        leistungInstance.properties = params
        return [leistungInstance: leistungInstance]
    }

    def save = {
        def leistungInstance = new Leistung(params)
        if (leistungInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'leistung.label', default: 'Leistung'), leistungInstance.id])
            redirect(action: "create")
        }
        else {
            render(view: "create", model: [leistungInstance: leistungInstance])
        }
    }

    def show = {
        def leistungInstance = Leistung.get(params.id)
        if (!leistungInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leistung.label', default: 'Leistung'), params.id])
            redirect(action: "list")
        }
        else {
            [leistungInstance: leistungInstance]
        }
    }

    def edit = {
        def leistungInstance = Leistung.get(params.id)
        if (!leistungInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leistung.label', default: 'Leistung'), params.id])
            redirect(action: "list")
        }
        else {
            return [leistungInstance: leistungInstance]
        }
    }

    def update = {
        def leistungInstance = Leistung.get(params.id)
        if (leistungInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (leistungInstance.version > version) {
                    
                    leistungInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'leistung.label', default: 'Leistung')] as Object[], "Another user has updated this Leistung while you were editing")
                    render(view: "edit", model: [leistungInstance: leistungInstance])
                    return
                }
            }
            leistungInstance.properties = params
            if (!leistungInstance.hasErrors() && leistungInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'leistung.label', default: 'Leistung'), leistungInstance.id])
                redirect(action: "show", id: leistungInstance.id)
            }
            else {
                render(view: "edit", model: [leistungInstance: leistungInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leistung.label', default: 'Leistung'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def leistungInstance = Leistung.get(params.id)
        if (leistungInstance) {
            try {
                leistungInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'leistung.label', default: 'Leistung'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'leistung.label', default: 'Leistung'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leistung.label', default: 'Leistung'), params.id])
            redirect(action: "list")
        }
    }
}
