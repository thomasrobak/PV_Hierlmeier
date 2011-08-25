package hierlmeier

import grails.converters.JSON

import org.codehaus.groovy.grails.orm.hibernate.exceptions.GrailsQueryException

import java.math.RoundingMode

class ZahlungController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[zahlungInstanceList: Zahlung.list(params), zahlungInstanceTotal: Zahlung.count()]
    }
    
    def zahlungCreationFlow = {
        getListApplicableKunden {
            action {
                def offeneBelegeList = Beleg.unbeglichene.list()
                println(offeneBelegeList.class.toString() + " offeneBelegeList: " + offeneBelegeList)

                def kundenList = []
                offeneBelegeList.each {
                    if(!kundenList.contains(it.kunde)) {
                        kundenList.add(it.kunde)
                    }
                }
                println(kundenList.class.toString() + " kundenList: " + kundenList)

                flow.applicableKundenList = kundenList
                flow.applicableKundenListTotal = kundenList.size()
                flow.offeneBelegeList = offeneBelegeList
            }
            on("success").to "determineKunde"
            //@todo on(Exception).to "handleError"   
        }
        determineKunde {
            on("submit") {
                flow.chosenKunde = Kunde.get(params.id)
                
            }.to "getListKundeOffeneBelege"
            on("return").to "determineKunde"
        }
        getListKundeOffeneBelege {
            action {
                def results = Beleg.unbeglichene.findAllByKunde(flow.chosenKunde)
                flow.kundeOffeneBelegeList = results
                flow.kundeOffeneBelegeTotal = results.size()
            }
            on("success").to "inputZahlungData"
            //@todo on(Exception).to "handleError"   
        }
        inputZahlungData {
            on("submit") {
                def k =  flow.chosenKunde
                def b = params.betrag
                def d = params.datum
                
                def z = new Zahlung(kunde:k, betrag:b, datum:d)
                                
                if(!z.validate()) {
                    z.errors.each {
                        println it
                    }
                    //flash.message = b.errors.fieldError
                    return error()
                }     
                z.save(flush: true)
                flow.createdZahlung = z
                [zahlungsInstance:z]
            }.to "displayCreatedZahlung"
            on("error").to "inputZahlungData"
            on("return").to "determineKunde"
        }
        displayCreatedZahlung {
            redirect(action:"show", id:flow.createdZahlung.id)
        }
    }

    def create = {
        redirect(action:"zahlungCreation")
    }

    def save = {
        def zahlungInstance = new Zahlung(params)
        if (zahlungInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), zahlungInstance.id])
            redirect(action: "show", id: zahlungInstance.id)
        }
        else {
            render(view: "create", model: [zahlungInstance: zahlungInstance])
        }
    }

    def show = {
        def zahlungInstance = Zahlung.get(params.id)
        if (!zahlungInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), params.id])
            redirect(action: "list")
        }
        else {
            [zahlungInstance: zahlungInstance]
        }
    }

    def edit = {
        def zahlungInstance = Zahlung.get(params.id)
        if (!zahlungInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), params.id])
            redirect(action: "list")
        }
        else {
            return [zahlungInstance: zahlungInstance]
        }
    }

    def update = {
        def zahlungInstance = Zahlung.get(params.id)
        if (zahlungInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (zahlungInstance.version > version) {
                    
                    zahlungInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'zahlung.label', default: 'Zahlung')] as Object[], "Another user has updated this Zahlung while you were editing")
                    render(view: "edit", model: [zahlungInstance: zahlungInstance])
                    return
                }
            }
            zahlungInstance.properties = params
            if (!zahlungInstance.hasErrors() && zahlungInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), zahlungInstance.id])
                redirect(action: "show", id: zahlungInstance.id)
            }
            else {
                render(view: "edit", model: [zahlungInstance: zahlungInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def zahlungInstance = Zahlung.get(params.id)
        if (zahlungInstance) {
            try {
                zahlungInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), params.id])
            redirect(action: "list")
        }
    }
}
