package hierlmeier //Maybe NetBeans shows an java.lang.Enum related error here (IDE Bug)

import grails.converters.JSON
import grails.converters.XML

import hierlmeier.PdfService

import java.math.RoundingMode

class RechnungController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"
    
    PdfService pdfService
    
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
        
        
    }
    
    private String generatePrintXML(String id) {
        def rechnungInstance = Rechnung.get(id)
        
        if (!rechnungInstance) {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'rechnung.label', default: 'Rechnung'), params.id])
            redirect(action: "list")
        } else {
            return generatePrintXML(rechnungInstance)
        }
    }
    
    private String generatePrintXML(Rechnung rechnungInstance) {
        println("**** generatePrintXML for Rechnung - START")
        println("** rechnung: " + rechnungInstance)
                
        def blg = []
        def rechnungsum = new BigDecimal("0.00").setScale(g.message(code:'default.scale').toInteger())
        def rechnungmwst = new BigDecimal("0.00").setScale(g.message(code:'default.scale').toInteger())
        def rechnungnetto = new BigDecimal("0.00").setScale(g.message(code:'default.scale').toInteger())
        
        def kundeInstance = rechnungInstance.kunde
        def zt = rechnungInstance.zahlungsteile
        
        rechnungInstance.belege.each { it ->
            def offen = new BigDecimal(it.betrag.toString())
            def belegtext
            def tmpzt = zt.findAll{ x -> x.beleg == it}
            println("** zt: " + tmpzt)
            if(zt != [] && zt != null) {
                zt.each { z ->
                    offen = offen.subtract(z.betrag)
                }
                belegtext = new String("Restbetrag Beleg " + it.belegnummer.toString())
            } else {
                belegtext = new String("Betrag Beleg " + it.belegnummer.toString())
            }
            rechnungsum = rechnungsum.add(offen)
            blg.add([id: it.id, belegtext: belegtext, datum: it.datum, offen: offen])
        }
        
        if(kundeInstance.mwst) {
            rechnungnetto = rechnungsum.divide(new BigDecimal(g.message(code:'default.tax.rate')), g.message(code:'default.scale').toInteger(), RoundingMode.valueOf(g.message(code:'default.rounding.mode')))
            rechnungmwst = rechnungsum.subtract(rechnungnetto)
        }
        
        def writer = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(writer)
        
        xml.rechnung(id: rechnungInstance.id) {
            rechnungnummer rechnungInstance.rechnungnummer
            datum rechnungInstance.datum
            summe rechnungsum
            mwst rechnungmwst
            netto rechnungnetto
            rechnungbelege() {
                blg.each { tmp ->
                    beleg(id: tmp.id) {
                        belegtext tmp.belegtext
                        datum tmp.datum
                        offen tmp.offen
                    }
                }
            }
            kunde(id: kundeInstance.id) {
                nachname kundeInstance.nachname
                vorname kundeInstance.vorname
                adresse kundeInstance.adresse
                wohnort kundeInstance.wohnort
                telefonnummer kundeInstance.telefonnummer
                beruf kundeInstance.beruf
                mwst kundeInstance.mwst
                bemerkung kundeInstance.bemerkung
            }
        }
        
        println("**** generatePrintXML for Rechnung - END - Result: " + writer.toString())
        
        return writer.toString()
    }
    
    
    def print = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def printList = []
        
        if(params.id != null) {
            printList.add(params.id)
        } 
        if(params.printList != null) {
            printList.addAll(params.printList)
        }
        
        if (printList == []) {
            flash.message = "*!*!* List for Printing is empty! Redirecting to action: list"
            println("*!*!* List for printing is empty! Redirecting to action: list")
            redirect(action: "list")
        }
        
        def xslfile = servletContext.getAttribute("RechnungStyleSheet")
        String pdfOutputDir
        
        def pdfOutputDirAbsolute = g.message(code: 'application.pdf.output.dir.absolute', default: "null")
        if(pdfOutputDirAbsolute != null && pdfOutputDirAbsolute != "null") {
            pdfOutputDir = new String(pdfOutputDirAbsolute)
        } else {
            pdfOutputDir = new String(servletContext.getRealPath("/") + g.message(code: 'application.pdf.output.dir', default: "pdf/"))
        }
        new File(pdfOutputDir).mkdirs()
        println("** PDF Output Directory set to: " + pdfOutputDir)
        
        printList.each { rechnungId ->
            def rechnungInstance = Rechnung.get(rechnungId)
            if (!rechnungInstance) {
                flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'rechnung.label', default: 'Rechnung'), params.id])
                redirect(action: "list") 
            }
            def xmlstring = new String(generatePrintXML(rechnungInstance))
            String pdfOutputFilePath = new String(pdfOutputDir + "Rechnung_" + rechnungInstance.rechnungnummer + ".pdf")
               
            File pdf = pdfService.generatePDF(xmlstring, xslfile, pdfOutputFilePath)
            String printCmd = g.message(code: 'application.print.command')
            println("** Executing: " + String.format(printCmd, pdf.getAbsolutePath()))
            Runtime.getRuntime().exec(String.format(printCmd, pdf.getAbsolutePath()))
        }
        println("**** $controllerName.$actionName END")
        redirect(action: "list")
    }
    
    def create = {
        redirect(action:"createRechnung")
    }
    
    def createRechnungFlow = {  //flow names must be unique for whole application
        chooseKunde {
            on("batch") {
                println("****** $controllerName.$actionName chooseKunde.onBatch")
                println("*** params: " + params)
                def minimumZahllast
                if(params.minzahllast == null || params.minzahllast == "") {
                    minimumZahllast = new BigDecimal("0")
                } else {
                    try {
                        minimumZahllast = new BigDecimal(params.minzahllast.toString())
                    } catch (NumberFormatException nfe) {
                        return error()
                    }
                }
                
                def results = Kunde.withUnpaidBelege.listDistinct()
                def kl = []
                results.each {
                    if(it.zahllast >= minimumZahllast) {
                        kl.add(it)
                    }
                }
                flow.kundeList = kl
            }.to "persistRechnung"  
            on("single") {
                println("****** $controllerName.$actionName chooseKunde.onSingle")
                println("*** params: " + params)
                def kl = []
                kl.add(Kunde.get(params.id))
                flow.kundeList = kl
            }.to "persistRechnung"
            on("error") {
                println("*!*!* Error during $controllerName.$actionName chooseKunde")
            }.to "chooseKunde"
        }
        persistRechnung {
            action {
                println("****** $controllerName.$actionName persistRechnung.action")
                println("*** params: " + params)
                
                Integer rn = new Integer("10001")   //@todo change this (rechnungsnummer)
                
                def rl = []
                
                flow.kundeList.each {
                    def b = Beleg.unbeglichene.findAllByKunde(it, [sort:"datum", order:"asc"])
                    def zt = Zahlungsteil.findAllByBelegInList(b)
                    def rechnung = new Rechnung(kunde: it, rechnungnummer: rn.toString(), belege: b, zahlungsteile: zt)
                    
                    if(!rechnung.validate()) {
                        rechnung.errors.each {
                            println it
                        }
                        return error()
                    } else {
                        rl.add(rechnung)
                        rn += 1
                    }
                }
                def idList = []
                rl.each {
                    if (it.save(flush: true)) {
                        flash.message = message(code: 'default.created.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), it.id])
                    }
                    else {
                        return error()
                    }
                    idList.add(it.id)
                }
                
                flow.createdRechnungIdList = idList
            }
            on("success") {
                println("****** $controllerName.$actionName persistRechnung.action.success")
            }.to "printPersistedRechnungen"
            on("error") {
                println("*!*!* Error during $controllerName.$actionName persistRechnung.action")
            }.to "chooseKunde"
        }
        printPersistedRechnungen {
            redirect(action: "print", params: [printList: flow.createdRechnungIdList])
        }
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [rechnungInstanceList: Rechnung.list(params), rechnungInstanceTotal: Rechnung.count()]
    }

    def save = {
        def rechnungInstance = new Rechnung(params)
        if (rechnungInstance.save(flush: true)) {
            flash.message = message(code: 'default.created.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), rechnungInstance.id])
            redirect(action: "show", id: rechnungInstance.id)
        }
        else {
            render(view: "create", model: [rechnungInstance: rechnungInstance])
        }
    }

    def show = {
        def rechnungInstance = Rechnung.get(params.id)
        if (!rechnungInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), params.id])
            redirect(action: "list")
        }
        else {
            [rechnungInstance: rechnungInstance]
        }
    }

    def edit = {
        def rechnungInstance = Rechnung.get(params.id)
        if (!rechnungInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), params.id])
            redirect(action: "list")
        }
        else {
            return [rechnungInstance: rechnungInstance]
        }
    }

    def update = {
        def rechnungInstance = Rechnung.get(params.id)
        if (rechnungInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (rechnungInstance.version > version) {
                    
                    rechnungInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'rechnung.label', default: 'Rechnung')] as Object[], "Another user has updated this Rechnung while you were editing")
                    render(view: "edit", model: [rechnungInstance: rechnungInstance])
                    return
                }
            }
            rechnungInstance.properties = params
            if (!rechnungInstance.hasErrors() && rechnungInstance.save(flush: true)) {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), rechnungInstance.id])
                redirect(action: "show", id: rechnungInstance.id)
            }
            else {
                render(view: "edit", model: [rechnungInstance: rechnungInstance])
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), params.id])
            redirect(action: "list")
        }
    }

    def delete = {
        def rechnungInstance = Rechnung.get(params.id)
        if (rechnungInstance) {
            try {
                rechnungInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), params.id])
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), params.id])
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rechnung.label', default: 'Rechnung'), params.id])
            redirect(action: "list")
        }
    }
}