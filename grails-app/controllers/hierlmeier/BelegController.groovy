package hierlmeier

import grails.converters.JSON

class BelegController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }
    
    def belegCreationFlow = {
        getListKunden {
            action {
                //@todo umschrieben auf listBelegCanditates()
                [ kundenlist:Kunde.list() ]
            }
            on("success").to "determineKunde"
            //@todo on(Exception).to "handleError"   
        }
        determineKunde {
            on("submit") {
                flow.chosenkunde = Kunde.get(params.id)
            }.to "getListPositionen"
            
            on("return").to "determineKunde"
            
        }
        getListPositionen {
            action {
                flow.positionenlist = Position.findAllByKundeAndBelegIsNull(flow.chosenkunde)
            }
            on("success").to "determinePositionen"
            //@todo on(Exception).to "handleError"   
        }
        determinePositionen {
            on("submit").to "processBelegCreation"
            on("return").to "determineKunde"
            
        }
        processBelegCreation {
            action {
                //@todo do smth
            }
            on("success").to "displayCreatedBeleg"
        }
        displayCreatedBeleg ()
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[belegInstanceList: Beleg.list(params), belegInstanceTotal: Beleg.count()]
    }

    def create = {
        def belegInstance = new Beleg()
        belegInstance.properties = params
        return [belegInstance: belegInstance]
    }

    def save = {
        def belegInstance = new Beleg(params)
        if (belegInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'beleg.label', default: 'Beleg'), belegInstance.id])
            redirect(action: "show", id: belegInstance.id)
        }
        else {
            render(view: "create", model: [belegInstance: belegInstance])
        }
    }

    def show = {
        def belegInstance = Beleg.get(params.id)
        if (!belegInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
            redirect(action: "list")
        }
        else {
            [belegInstance: belegInstance]
        }
    }

    def edit = {
        def belegInstance = Beleg.get(params.id)
        if (!belegInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
            redirect(action: "list")
        }
        else {
            return [belegInstance: belegInstance]
        }
    }

    def update = {
        def belegInstance = Beleg.get(params.id)
        if (belegInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (belegInstance.version > version) {
                    
                    belegInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'beleg.label', default: 'Beleg')] as Object[], "Another user has updated this Beleg while you were editing")
                    render(view: "edit", model: [belegInstance: belegInstance])
                    return
                }
            }
            belegInstance.properties = params
            if (!belegInstance.hasErrors() && belegInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'beleg.label', default: 'Beleg'), belegInstance.id])
                redirect(action: "show", id: belegInstance.id)
            }
            else {
                render(view: "edit", model: [belegInstance: belegInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def belegInstance = Beleg.get(params.id)
        if (belegInstance) {
            try {
                belegInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
            redirect(action: "list")
        }
    }
}
