package hierlmeier

import grails.converters.JSON
import grails.converters.XML

import hierlmeier.PrintBelegService

class BelegController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"
    
    PrintBelegService printBelegService

    def index = {
        redirect(action: "list", params: params)
    }
    
    def print = {
        def belegInstance = Beleg.get(params.id)
        
        if (!belegInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
            redirect(action: "show", id: belegInstance.id)
        }
        
        def ByteArrayOutputStream out
        
        try {
            out = printBelegService.generatePDF(belegInstance)
            
            response.setContentType("application/pdf");
            response.setContentLength(out.size());
    
            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
        } finally {
            out.close();
            println("error pdf")
        }
    }
    
    def belegCreationFlow = {
        getListApplicableKunden {
            action {
                def criteria = Kunde.createCriteria()
                def results = criteria.listDistinct {
                    isNotEmpty("positionen")
                    positionen {
                        isNull("beleg")
                    }
                    //@todo order("nachname", "asc")
                }
                flow.applicableKundeList = results
                flow.applicableKundeListTotal = results.count()
            }
            on("success").to "determineKunde"
            //@todo on(Exception).to "handleError"   
        }
        determineKunde {
            on("submit") {
                flow.chosenKunde = Kunde.get(params.id)
            }.to "getListKundePositionen"
            
            on("return").to "determineKunde"
            
        }
        getListKundePositionen {
            action {
                def results = Position.findAllByKundeAndBelegIsNull(flow.chosenKunde)
                flow.kundePositionenList = results
                flow.positionenTotal = results.count()
            }
            on("success").to "determinePositionen"
            //@todo on(Exception).to "handleError"   
        }
        determinePositionen {
            on("submit") {
                def k =  flow.chosenKunde
                def p = flow.kundePositionenList
                def bnr = params.belegnummer
                
                def b = new Beleg(kunde:k, positionen:p, belegnummer:bnr) 
                
                if(!b.validate()) {
                    b.errors.each {
                        println it
                        
                    }
                    //flash.message = b.errors.fieldError
                    return error()
                }     
                b.save() 
                [belegInstance:b]
            }.to "displayCreatedBeleg"
            on("error").to "determinePositionen"
            on("return").to "determineKunde"
        }
        displayCreatedBeleg()
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
