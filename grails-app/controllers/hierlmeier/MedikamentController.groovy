package hierlmeier

import grails.converters.JSON

class MedikamentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[medikamentInstanceList: Medikament.list(params), medikamentInstanceTotal: Medikament.count()]
    }

    def create = {
        def medikamentInstance = new Medikament()
        medikamentInstance.properties = params
        return [medikamentInstance: medikamentInstance]
    }

    def save = {
        def medikamentInstance = new Medikament(params)
        if (medikamentInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'medikament.label', default: 'Medikament'), medikamentInstance.id])
            redirect(action: "create")
        }
        else {
            render(view: "create", model: [medikamentInstance: medikamentInstance])
        }
    }

    def show = {
        def medikamentInstance = Medikament.get(params.id)
        if (!medikamentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'medikament.label', default: 'Medikament'), params.id])
            redirect(action: "list")
        }
        else {
            [medikamentInstance: medikamentInstance]
        }
    }

    def edit = {
        def medikamentInstance = Medikament.get(params.id)
        if (!medikamentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'medikament.label', default: 'Medikament'), params.id])
            redirect(action: "list")
        }
        else {
            return [medikamentInstance: medikamentInstance]
        }
    }

    def update = {
        def medikamentInstance = Medikament.get(params.id)
        if (medikamentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (medikamentInstance.version > version) {
                    
                    medikamentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'medikament.label', default: 'Medikament')] as Object[], "Another user has updated this Medikament while you were editing")
                    render(view: "edit", model: [medikamentInstance: medikamentInstance])
                    return
                }
            }
            medikamentInstance.properties = params
            if (!medikamentInstance.hasErrors() && medikamentInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'medikament.label', default: 'Medikament'), medikamentInstance.id])
                redirect(action: "show", id: medikamentInstance.id)
            }
            else {
                render(view: "edit", model: [medikamentInstance: medikamentInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'medikament.label', default: 'Medikament'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def medikamentInstance = Medikament.get(params.id)
        if (medikamentInstance) {
            try {
                medikamentInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'medikament.label', default: 'Medikament'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'medikament.label', default: 'Medikament'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'medikament.label', default: 'Medikament'), params.id])
            redirect(action: "list")
        }
    }
}
