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

        def data = [aoData: results]
        
        println("** data before JSON rendering: " + data)
        
        println("**** $controllerName.$actionName END")
        render data as JSON
    }
    
    def dataTableJSONByBelege = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def belegIds
        params.belege.each {
            belegIds.add(it.id)
        }
        def belege = Beleg.getAll(belegIds)
        println("** belege: " + belege)
        
        def foundRecords = belegIds.size()
        println("** foundRecords: " + foundRecords)
        
        def formattedResults = belege.collect {
            [
                datum: new java.text.SimpleDateFormat(message(code:"default.date.format")).format(it.datum),
                belegnummer: it.belegnummer
            ]
        }
        
        def data = [
            totalRecords: foundRecords,
            results: formattedResults
        ]
        
        println("** no db query done, list supplied: " + belege)
        println("** JSON: " + data)
        println("**** $controllerName.$actionName END")
        
        render data as JSON
    }
    
    def dataTableJSONByKunde = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def kunde = Kunde.get(params.kundeId)
        
        /*
        def criteria = Positionen.createCriteria()
        def poslist = criteria.listDistinct {
            isNotEmpty("positionen")
            positionen {
                isNull("beleg")
            }
        }
        */
        
        def belege = Beleg.findAllByKunde(kunde, params)
        def foundRecords = Beleg.countByKunde(kunde)
        
        println("** foundRecords: " + foundRecords)
        
        def formattedResults = belege.collect {
            [
                datum: new java.text.SimpleDateFormat(message(code:"default.date.format")).format(it.datum),
                belegnummer: it.belegnummer
            ]
        }
        
        def data = [
            totalRecords: foundRecords,
            results: formattedResults
        ]
        
        println("** db query results: " + belege)
        println("** JSON: " + data)
        println("**** $controllerName.$actionName END")
        
        render data as JSON
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
                def belegInstance = new Beleg()
                belegInstance.properties = params
                [belegInstance: belegInstance]
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
                flow.kundePositionenList = results
            }.to "saveCreatedBeleg"
            on("error") {
                println("*!*!* Error during $controllerName.$actionName choosePositionen.onSubmit")
                flash.message = "irgendein fehler während createBelegFlow.choosePositionen"
            }.to "choosePositionen"
            on("return").to "chooseKunde"
        }
        saveCreatedBeleg {
            action {
                println("****** $controllerName.$actionName saveCreatedBeleg.action")
                println("*** params: " + params)
                def k =  flow.chosenKunde
                def p = flow.kundePositionenList
                def bnr = params.belegnummer
                def d = params.datum
                //@todo berechnung checken da passt was net mit den nachkommastellen, siehe Beleg.print() console output
                def netto = new BigDecimal("0.00")
                p.each {
                    netto = netto.add(it.betrag)   
                }
                def brutto = new BigDecimal(netto.multiply(new BigDecimal(g.message(code:'default.tax.rate'))))
                // brutto needs rounding because the scale maybe to long after multiplication
                brutto = brutto.setScale(g.message(code:'default.scale').toInteger(), RoundingMode.valueOf(g.message(code:'default.rounding.mode')))
                def betrag = k.mwst ? new BigDecimal(brutto) : new BigDecimal(netto) //@todo da passiert ein nachkomma blödsinn, beleg mit allen positionen für HuberFranz erstellen zum anschauen
                def sbz = new BigDecimal("0.00")
                
                def b = new Beleg(kunde:k, belegnummer:bnr, datum:d, netto:netto, brutto:brutto, betrag:betrag, summeBezahlt:sbz)
                
                p.each {
                    it.beleg = b    
                }
                
                /*
                if(!b.validate()) {
                    b.errors.each {
                        println it
                        
                    }
                    //@todo flash.message = b.errors.fieldError
                    return error()
                }
                */
                if (b.save()) {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'beleg.label', default: 'Beleg'), b.id])
                }
                else {
                    return error()
                }
                flow.createdBeleg = b //@todo check if needed weil siehe drunter
                [belegInstance:b]
            }
            on("success") {
                println("****** $controllerName.$actionName saveCreatedBeleg.action.onSuccess")
                String tmp = flow.createdBeleg.toStringDetailed()
                println("*** created beleg: " + tmp)
            }.to "displayCreatedBeleg"
            on("error") {
                println("*!*!* Error during $controllerName.$actionName saveCreatedBeleg.action")
                flash.message = "irgendein fehler während createBelegFlow.saveCreatedBeleg"
            }.to "choosePositionen"
        }
        displayCreatedBeleg {
            redirect(action:"show", belegInstance:flow.createdBeleg)
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
