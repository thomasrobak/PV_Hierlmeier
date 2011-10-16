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
                def b = new BigDecimal(params.betrag)
                def d = params.datum
                
                if(b.scale() < 2)
                    b = b.setScale(g.message(code:'default.scale').toInteger())
                
                def z = new Zahlung(kunde:k, betrag:b, datum:d)
                flow.zahlungInstance = z
                
                if(!z.validate()) {
                    z.errors.each {
                        println it
                    }
                    return error()
                }
                
                def results = Beleg.unbeglichene.findAllByKunde(flow.chosenKunde, [sort:"datum", order:"asc"])
                println("*** query result class: " + results.class.toString())
                println("*** query result (Beleg.unbeglichene.findAllByKunde): " + results)
                
                    
                
                BigDecimal splitfromthis = new BigDecimal(b.toString())
                def listzt = []
                
                for(int i=0; splitfromthis > 0; i++) {
                    def blg = results.get(i)
                    BigDecimal offen = blg.betrag.subtract(blg.summeBezahlt)
                    if(offen < splitfromthis) {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(offen.toString()))
                        listzt.add(zt)
                        blg.summeBezahlt = blg.summeBezahlt.add(offen)
                        splitfromthis = splitfromthis.subtract(offen)
                    }
                    else {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(splitfromthis.toString()))
                        listzt.add(zt)
                        blg.summeBezahlt = blg.summeBezahlt.add(splitfromthis)
                        splitfromthis = new BigDecimal("0.00")
                    }
                }
                
                z.zahlungsteile = listzt
                
                if (z.save()) {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), z.id])
                }
                else {
                    return error()
                }
            }.to "displayCreatedZahlung"
            on("payall") {
                                
                def results = Beleg.unbeglichene.findAllByKunde(flow.chosenKunde, [sort:"datum", order:"asc"])
                println("*** query result class: " + results.class.toString())
                println("*** query result (Beleg.unbeglichene.findAllByKunde): " + results)
                
                def k =  flow.chosenKunde
                def b = new BigDecimal("0.00")
                def d = params.datum
                
                results.each {
                    b = b.add(it.betrag.subtract(it.summeBezahlt))
                }
                
                def z = new Zahlung(kunde:k, betrag:b, datum:d)
                
                
                if(!z.validate()) {
                    z.errors.each {
                        println it
                    }
                    flash.message = "Error: check input"
                    return error()
                }
                
                BigDecimal splitfromthis = new BigDecimal(b.toString())
                def listzt = []
                
                for(int i=0; splitfromthis > 0; i++) {
                    def blg = results.get(i)
                    BigDecimal offen = blg.betrag.subtract(blg.summeBezahlt)
                    if(offen < splitfromthis) {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(offen.toString()))
                        listzt.add(zt)
                        blg.summeBezahlt = blg.summeBezahlt.add(offen)
                        splitfromthis = splitfromthis.subtract(offen)
                    }
                    else {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(splitfromthis.toString()))
                        listzt.add(zt)
                        blg.summeBezahlt = blg.summeBezahlt.add(splitfromthis)
                        splitfromthis = new BigDecimal("0.00")
                    }
                }
                
                z.zahlungsteile = listzt
                
                if (z.save()) {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'zahlung.label', default: 'Zahlung'), z.id])
                }
                else {
                    return error()
                }
                flow.zahlungInstance = z
                
            }.to "displayCreatedZahlung"
            on("error") {
                //flash.message = "ASDADASDASDADSASDASDASD"
            }.to "chooseZahlungDetails"
            on("return").to "chooseKunde"
        }
        displayCreatedZahlung {
            redirect(action:"show", id: flow.zahlungInstance.id)
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
