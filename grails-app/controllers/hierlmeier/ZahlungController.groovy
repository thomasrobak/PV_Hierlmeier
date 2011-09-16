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
    
    def create = {
        redirect(action:"createZahlung")
    }
    
    def createZahlungFlow = { //flow names must be unique for whole application
        chooseKunde {
            on("submit") {
                println("****** $controllerName.$actionName chooseKunde.onSubmit")
                println("*** params: " + params)
                flow.chosenKunde = Kunde.get(params.id)
                def zahlungInstance = new Zahlung()
                zahlungInstance.properties = params
                [zahlungInstance: zahlungInstance]
            }.to "chooseZahlungDetails"
            on("return").to "chooseKunde"
        }
        chooseZahlungDetails {
            on("submit") {
                def k =  flow.chosenKunde
                def b = params.betrag
                def d = params.datum
                
                def selectedIds = [] 
                params.selected.each {
                    selectedIds.add(it.toInteger())
                }
                println("*** selectedIds: " + selectedIds)
                def results = Beleg.getAll(selectedIds)
                
                flow.chosenBelege = results.sort { x, y -> x.compareTo(y) }
                
                def z = new Zahlung(kunde:k, betrag:b, datum:d)
                                
                if(!z.validate()) {
                    z.errors.each {
                        println it
                    }
                    flash.message = "Error: check input"
                    return error()
                }     
                flow.zahlungInstance = z
            }.to "validateDistribution"
            on("error").to "chooseZahlungDetails"
            on("return").to "chooseKunde"
        }
        validateDistribution {
            on("submit") {
                z.save(flush: true)
            }.to "displayCreatedZahlung"
            on("error").to "chooseZahlungDetails"
            on("return").to "chooseZahlungDetails"
        }
        displayCreatedZahlung {
            redirect(action:"show", id:flow.zahlungInstance.id)
        }
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
