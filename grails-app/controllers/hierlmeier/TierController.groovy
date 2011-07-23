package hierlmeier

import grails.converters.JSON

class TierController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[tierInstanceList: Tier.list(params), tierInstanceTotal: Tier.count()]
    }

    def create = {
        def tierInstance = new Tier()
        tierInstance.properties = params
        return [tierInstance: tierInstance]
    }

    def save = {
        def tierInstance = new Tier(params)
        if (tierInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])
            redirect(action: "show", id: tierInstance.id)
        }
        else {
            render(view: "create", model: [tierInstance: tierInstance])
        }
    }

    def show = {
        def tierInstance = Tier.get(params.id)
        if (!tierInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tier.label', default: 'Tier'), params.id])
            redirect(action: "list")
        }
        else {
            [tierInstance: tierInstance]
        }
    }

    def edit = {
        def tierInstance = Tier.get(params.id)
        if (!tierInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tier.label', default: 'Tier'), params.id])
            redirect(action: "list")
        }
        else {
            return [tierInstance: tierInstance]
        }
    }

    def update = {
        def tierInstance = Tier.get(params.id)
        if (tierInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tierInstance.version > version) {
                    
                    tierInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tier.label', default: 'Tier')] as Object[], "Another user has updated this Tier while you were editing")
                    render(view: "edit", model: [tierInstance: tierInstance])
                    return
                }
            }
            tierInstance.properties = params
            if (!tierInstance.hasErrors() && tierInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])
                redirect(action: "show", id: tierInstance.id)
            }
            else {
                render(view: "edit", model: [tierInstance: tierInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tier.label', default: 'Tier'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def tierInstance = Tier.get(params.id)
        if (tierInstance) {
            try {
                tierInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tier.label', default: 'Tier'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tier.label', default: 'Tier'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tier.label', default: 'Tier'), params.id])
            redirect(action: "list")
        }
    }
}
