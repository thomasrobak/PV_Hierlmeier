package hierlmeier

import grails.converters.JSON

class ZahlungsteilController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[zahlungsteilInstanceList: Zahlungsteil.list(params), zahlungsteilInstanceTotal: Zahlungsteil.count()]
    }

    def create = {
        def zahlungsteilInstance = new Zahlungsteil()
        zahlungsteilInstance.properties = params
        return [zahlungsteilInstance: zahlungsteilInstance]
    }

    def save = {
        def zahlungsteilInstance = new Zahlungsteil(params)
        if (zahlungsteilInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), zahlungsteilInstance.id])
            redirect(action: "show", id: zahlungsteilInstance.id)
        }
        else {
            render(view: "create", model: [zahlungsteilInstance: zahlungsteilInstance])
        }
    }

    def show = {
        def zahlungsteilInstance = Zahlungsteil.get(params.id)
        if (!zahlungsteilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), params.id])
            redirect(action: "list")
        }
        else {
            [zahlungsteilInstance: zahlungsteilInstance]
        }
    }

    def edit = {
        def zahlungsteilInstance = Zahlungsteil.get(params.id)
        if (!zahlungsteilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), params.id])
            redirect(action: "list")
        }
        else {
            return [zahlungsteilInstance: zahlungsteilInstance]
        }
    }

    def update = {
        def zahlungsteilInstance = Zahlungsteil.get(params.id)
        if (zahlungsteilInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (zahlungsteilInstance.version > version) {
                    
                    zahlungsteilInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'zahlungsteil.label', default: 'Zahlungsteil')] as Object[], "Another user has updated this Zahlungsteil while you were editing")
                    render(view: "edit", model: [zahlungsteilInstance: zahlungsteilInstance])
                    return
                }
            }
            zahlungsteilInstance.properties = params
            if (!zahlungsteilInstance.hasErrors() && zahlungsteilInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), zahlungsteilInstance.id])
                redirect(action: "show", id: zahlungsteilInstance.id)
            }
            else {
                render(view: "edit", model: [zahlungsteilInstance: zahlungsteilInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def zahlungsteilInstance = Zahlungsteil.get(params.id)
        if (zahlungsteilInstance) {
            try {
                zahlungsteilInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil'), params.id])
            redirect(action: "list")
        }
    }
}
