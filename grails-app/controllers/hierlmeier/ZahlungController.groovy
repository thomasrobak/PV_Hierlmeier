package hierlmeier //Maybe NetBeans shows an java.lang.Enum related error here (IDE Bug)

import grails.converters.JSON

import org.codehaus.groovy.grails.orm.hibernate.exceptions.GrailsQueryException

import java.math.RoundingMode

class ZahlungController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"
    
    enum Filter {  // possible filters for the dataTableJSON method, filter is set in the view and submitted by the ajax call
        NOFILTER("filter.NOFILTER")    //value is a message.properties code (no filter at all)

        private final String value 
        Filter(String value) { this.value = value }
        String toString() { value }
        String value() { value }
        String getKey() { name() }
    }

    def index = {
        redirect(action: "list", params: params)
    }
    
    def dataTableJSON = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def results
        def foundRecords
        
        if(params.filter == g.message(code: Filter.NOFILTER.value())) {
            if(params.kundeId) {
                def kunde = Kunde.get(params.kundeId)
                results = Zahlung.findAllByKunde(kunde) //@todo try with id only (not fetching the kunde object first)
            }
            else {
                results = Zahlung.list()
            }
        }
        else {
            println("** params.filter not set or invalid value, showing all for $controllerName")
            flash.message = "Filter not found. Showing all records (same as 'Filter.NOFILTER')."  //@todo message code dafür fehlt
            results = Zahlung.list()
        }
        
        foundRecords = results.size();
        println("** results Class: " + results.getClass().toString())
        println("** foundRecords: " + foundRecords)
        println("** db query results: " + results)
        
        BigDecimal paidsum = new BigDecimal("0.00")
        
        results.each {
            paidsum = paidsum.add(it.betrag)
        }
        if(paidsum.scale() < 2)
            paidsum = paidsum.setScale(g.message(code:'default.scale').toInteger())

        def data = [paid: paidsum.toString(), aoData: results]
        
        println("** data before JSON rendering: " + data)
        
        println("**** $controllerName.$actionName END")
        render JSON.use("deep"){data as JSON}  //@todo performacetechnisch net optimal evtl, besser eager fetching in der domain class von position?
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
                flow.zahlungInstance = new Zahlung(kunde:flow.chosenKunde)
            }.to "chooseZahlungDetails"
            on("return").to "chooseKunde"
        }
        chooseZahlungDetails {
            on("submit") {
                def z = flow.zahlungInstance
                z.properties = params
                
                if(!z.validate()) {
                    z.errors.each {
                        println it
                    }
                    return error()
                }
                
                def results = Beleg.unbeglichene.findAllByKunde(flow.chosenKunde, [sort:"datum", order:"asc"])
                println("*** query result class: " + results.class.toString())
                println("*** query result (Beleg.unbeglichene.findAllByKunde): " + results)

                BigDecimal splitfromthis = new BigDecimal(z.betrag.toString())
                def listzt = []
                
                for(int i=0; splitfromthis > 0; i++) {
                    def blg = results.get(i)
                    BigDecimal offen = blg.betrag.subtract(blg.bezahlt)
                    if(offen < splitfromthis) {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(offen.toString()))
                        listzt.add(zt)
                        blg.bezahlt = blg.bezahlt.add(offen)
                        splitfromthis = splitfromthis.subtract(offen)
                    }
                    else {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(splitfromthis.toString()))
                        listzt.add(zt)
                        blg.bezahlt = blg.bezahlt.add(splitfromthis)//.setScale(g.message(code:'default.scale').toInteger())
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
                def z = flow.zahlungInstance
                
                def results = Beleg.unbeglichene.findAllByKunde(flow.chosenKunde, [sort:"datum", order:"asc"])
                println("*** query result class: " + results.class.toString())
                println("*** query result (Beleg.unbeglichene.findAllByKunde): " + results)
                
                def b = new BigDecimal("0.00")

                results.each {
                    b = b.add(it.betrag.subtract(it.bezahlt))
                }
                params.betrag = b
                
                z.properties = params
                
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
                    BigDecimal offen = blg.betrag.subtract(blg.bezahlt)
                    if(offen < splitfromthis) {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(offen.toString()))
                        listzt.add(zt)
                        blg.bezahlt = blg.bezahlt.add(offen)
                        splitfromthis = splitfromthis.subtract(offen)
                    }
                    else {
                        def zt = new Zahlungsteil(zahlung:z, beleg:blg, betrag:new BigDecimal(splitfromthis.toString()))
                        listzt.add(zt)
                        blg.bezahlt = blg.bezahlt.add(splitfromthis)//.setScale(g.message(code:'default.scale').toInteger())
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
