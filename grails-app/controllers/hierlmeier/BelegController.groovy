package hierlmeier //Maybe NetBeans shows an java.lang.Enum related error here (IDE Bug)

import grails.converters.JSON
import grails.converters.XML

import hierlmeier.PrintService

import java.math.RoundingMode

class BelegController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"
    
    PrintService printService
    
    enum Filter {  // possible filters for the dataTableJSON method, filter is set in the view and submitted by the ajax call
        NOFILTER("filter.NOFILTER"),    //value is a message.properties code (no filter at all)
        NPB("filter.NPB")               //value is a message.properties code (has not paid belege)

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
                results = Beleg.findAllByKunde(kunde) //@todo try with id only (not fetching the kunde object first)
            }
            else {
                results = Beleg.list()
            }
        }
        else if(params.filter == g.message(code: Filter.NPB.value())) {
            if(params.kundeId) {
                def kunde = Kunde.get(params.kundeId)
                results = Beleg.unbeglichene.findAllByKunde(kunde)
            }
            else {
                results = Beleg.unbeglichene.list();
            }
        }
        else {
            println("** params.filter not set or invalid value, showing all for $controllerName")
            flash.message = "Filter not found. Showing all records (same as 'Filter.NOFILTER')."  //@todo message code dafür fehlt
            results = Beleg.list()
        }
        
        foundRecords = results.size();
        println("** results Class: " + results.getClass().toString())
        println("** foundRecords: " + foundRecords)
        println("** db query results: " + results)
        
        BigDecimal opensum = new BigDecimal("0.00") 
        BigDecimal paidsum = new BigDecimal("0.00")
        
        results.each {
            opensum = opensum.add(it.betrag)
            paidsum = paidsum.add(it.bezahlt)
        }

        def data = [remaining: opensum.subtract(paidsum).toString(), aoData: results]
        
        println("** data before JSON rendering: " + data)
        
        println("**** $controllerName.$actionName END")
        render JSON.use("deep"){data as JSON}  //@todo performacetechnisch net optimal evtl, besser eager fetching in der domain class von position?
    }
    
    def print = {
        def belegInstance = Beleg.get(params.id)
        
        if (!belegInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'beleg.label', default: 'Beleg'), params.id])
            redirect(action: "show", id: belegInstance.id)
        }
        
        def ByteArrayOutputStream out
        
        def xslfile = servletContext.getAttribute("BelegStyleSheet")
                
        try {
            out = printService.generatePDF(belegInstance, xslfile)
            
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=Beleg_${belegInstance.belegnummer}")
            response.setContentLength(out.size());
            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
        } catch (Exception e) {
            println("Exception: " + e.toString())
        }
        finally {
            out.close();
        }
    }
    
    def create = {
        redirect(action:"createBeleg")
    }
    
    def createBelegFlow = {  //flow names must be unique for whole application
        chooseKunde {
            on("submit") {
                println("****** $controllerName.$actionName chooseKunde.onSubmit")
                println("*** params: " + params)
                flow.chosenKunde = Kunde.get(params.id)
                flow.belegInstance = new Beleg(datum: new Date(), kunde: flow.chosenKunde)
            }.to "choosePositionen"           
        }
        choosePositionen {
            on("submit") {
                println("****** $controllerName.$actionName choosePositionen.onSubmit")
                println("*** params: " + params)
                def selectedIds = []
                params.selected.each {
                    selectedIds.add(it.toInteger())
                }
                println("*** selectedIds: " + selectedIds)
                def results = Position.getAll(selectedIds)
                flow.chosenPositionList = results
            }.to "saveCreatedBeleg"
            on("error") {
                println("*!*!* Error during $controllerName.$actionName choosePositionen.onSubmit")
            }.to "choosePositionen"
            on("return").to "chooseKunde"
        }
        saveCreatedBeleg {
            action {
                println("****** $controllerName.$actionName saveCreatedBeleg.action")
                println("*** params: " + params)
                
                def p = flow.chosenPositionList
                def bnr = params.belegnummer
                def d = params.datum
                flow.belegInstance.properties = this.params
                flow.belegInstance.positionen = p

                def netto = new BigDecimal("0.00").setScale(g.message(code:'default.scale').toInteger())
                p.each {
                    netto = netto.add(it.betrag)   
                }
                def brutto = new BigDecimal(netto.multiply(new BigDecimal(g.message(code:'default.tax.rate'))))
                // brutto needs rounding because the scale maybe to long after multiplication
                brutto = brutto.setScale(g.message(code:'default.scale').toInteger(), RoundingMode.valueOf(g.message(code:'default.rounding.mode')))
                def betrag = flow.chosenKunde.mwst ? new BigDecimal(brutto.toString()) : new BigDecimal(netto.toString())
                def bez = new BigDecimal("0.00")
                
                flow.belegInstance.netto = netto
                flow.belegInstance.brutto = brutto
                flow.belegInstance.betrag = betrag
                flow.belegInstance.bezahlt = bez
                
                if(!flow.belegInstance.validate()) {
                    flow.belegInstance.errors.each {
                        println it
                    }
                    return error()
                }
                
                p.each {
                    it.beleg = flow.belegInstance
                }
                                
                if (flow.belegInstance.save()) {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'beleg.label', default: 'Beleg'), flow.belegInstance.id])
                }
                else {
                    return error()
                }
            }
            on("success") {
                println("****** $controllerName.$actionName saveCreatedBeleg.action.onSuccess")
                String tmp = flow.belegInstance.toStringDetailed()
                println("*** created beleg: " + tmp)
            }.to "displayCreatedBeleg"
            on("error") {
                println("*!*!* Error during $controllerName.$actionName saveCreatedBeleg.action")
            }.to "choosePositionen"
        }
        displayCreatedBeleg {
            redirect(action:"show", id: flow.belegInstance.id)
        }
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[belegInstanceList: Beleg.list(params), belegInstanceTotal: Beleg.count()]
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
