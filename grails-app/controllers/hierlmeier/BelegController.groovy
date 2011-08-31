package hierlmeier

import grails.converters.JSON
import grails.converters.XML

import hierlmeier.PrintService

import java.math.RoundingMode

class BelegController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "index"
    
    PrintService printService

    def index = {
        redirect(action: "list", params: params)
    }
    
    def dataTableJSON = {
        println("****** $controllerName.$actionName START")
        println("params: " + params)
        
        def belege = Beleg.list(params)
        def foundRecords = Beleg.count()
        
        println("foundRecords: " + foundRecords)
        
        def formattedResults = belege.collect {
            [
                belegnummer: it.belegnummer,
                kunde: it.kunde.toString(),
                datum: new java.text.SimpleDateFormat(message(code:"default.date.format")).format(it.datum),
                dataUrl: g.createLink(action:'show', id:it.id)
            ]
        }
        
        def data = [
            totalRecords: foundRecords,
            results: formattedResults
        ]
        
        println("db query results: " + belege)
        println("JSON: " + data)
        println("****** $controllerName.$actionName END")
        
        render data as JSON
    }
    
    
    def dataTableJSONByBelege = {
        println("****** $controllerName.$actionName START")
        println("params: " + params)
        
        def belegIds
        params.belege.each {
            belegIds.add(it.id)
        }
        def belege = Beleg.getAll(belegIds)
        println("belege: " + belege)
        
        def foundRecords = belegIds.size()
        println("foundRecords: " + foundRecords)
        
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
        
        println("no db query done, list supplied: " + belege)
        println("JSON: " + data)
        println("****** $controllerName.$actionName END")
        
        render data as JSON
    }
    
    def dataTableJSONByKunde = {
        println("****** $controllerName.$actionName START")
        println("params: " + params)
        
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
        
        println("foundRecords: " + foundRecords)
        
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
        
        println("db query results: " + belege)
        println("JSON: " + data)
        println("****** $controllerName.$actionName END")
        
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
            on("submit").to "saveCreatedBeleg"
            on("error").to "determinePositionen"
            on("return").to "determineKunde"
        }
        saveCreatedBeleg {
            action {
                def k =  flow.chosenKunde
                def p = flow.kundePositionenList
                def bnr = params.belegnummer
                def d = params.datum
                def netto = new BigDecimal("0.00")
                p.each {
                    netto = netto.add(it.betrag)   
                }
                def brutto = new BigDecimal(netto.multiply(new BigDecimal(g.message(code:'default.tax.rate'))))
                // brutto needs rounding because the scale maybe to long after multiplication
                brutto = brutto.setScale(g.message(code:'default.scale').toInteger(), RoundingMode.valueOf(g.message(code:'default.rounding.mode')))
                def betrag = k.mwst ? new BigDecimal(brutto) : new BigDecimal(netto)
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
            on("success").to "displayCreatedBeleg"
            on("error").to "determinePositionen"
        }
        displayCreatedBeleg {
            redirect(action:"show", belegInstance:flow.createdBeleg)
        }
    }

    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
	[belegInstanceList: Beleg.list(params), belegInstanceTotal: Beleg.count()]
    }

    def create = {
        redirect(action: "belegCreation")
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
