package hierlmeier

import grails.converters.JSON

class PositionstypController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[positionstypInstanceList: Positionstyp.list(params), positionstypInstanceTotal: Positionstyp.count()]
    }
    
    def preisJSON = {
        def typ = Positionstyp.get(params.id)
	render typ as JSON
    }

    def create = {
        def positionstypInstance = new Positionstyp()
        positionstypInstance.properties = params
        return [positionstypInstance: positionstypInstance]
    }

    def save = {
        def positionstypInstance = new Positionstyp(params)
        if (positionstypInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), positionstypInstance.id])
            redirect(action: "show", id: positionstypInstance.id)
        }
        else {
            render(view: "create", model: [positionstypInstance: positionstypInstance])
        }
    }

    def show = {
        def positionstypInstance = Positionstyp.get(params.id)
        if (!positionstypInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), params.id])
            redirect(action: "list")
        }
        else {
            [positionstypInstance: positionstypInstance]
        }
    }

    def edit = {
        def positionstypInstance = Positionstyp.get(params.id)
        if (!positionstypInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), params.id])
            redirect(action: "list")
        }
        else {
            return [positionstypInstance: positionstypInstance]
        }
    }

    def update = {
        def positionstypInstance = Positionstyp.get(params.id)
        if (positionstypInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (positionstypInstance.version > version) {
                    
                    positionstypInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'positionstyp.label', default: 'Positionstyp')] as Object[], "Another user has updated this Positionstyp while you were editing")
                    render(view: "edit", model: [positionstypInstance: positionstypInstance])
                    return
                }
            }
            positionstypInstance.properties = params
            if (!positionstypInstance.hasErrors() && positionstypInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), positionstypInstance.id])
                redirect(action: "show", id: positionstypInstance.id)
            }
            else {
                render(view: "edit", model: [positionstypInstance: positionstypInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def positionstypInstance = Positionstyp.get(params.id)
        if (positionstypInstance) {
            try {
                positionstypInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'positionstyp.label', default: 'Positionstyp'), params.id])
            redirect(action: "list")
        }
    }
}
