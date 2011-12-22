package hierlmeier  //ignore error message -> Netbeans IDE Bug

import grails.converters.JSON
import org.springframework.context.i18n.LocaleContextHolder; 
import java.text.SimpleDateFormat;

class StatistikController {
    
        def heute
        def jahrAktuell
    
        def monatListe = []
        def jahrListe = []
    
    StatistikController() {
        heute = new Date()
        jahrAktuell = heute[Calendar.YEAR]
    
        new java.text.DateFormatSymbols(LocaleContextHolder.locale).months.each {
            it == "" ?: monatListe.add(it)
        }
        jahrListe.addAll(jahrAktuell..(jahrAktuell-10))
    }
    
    enum Filter {  // possible filters for the JSON methods, filter is set in the view and submitted by the ajax call
        NOFILTER("filter.NOFILTER")

        private final String value //value is a message.properties code
        Filter(String value) { this.value = value }
        String toString() { value }
        String value() { value }
        String getKey() { name() }
    }

    def overviewJSON = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def counters = [:]
        
        def kunden = Kunde.count()
        counters.kundeTotal = kunden
        
        def belege = Beleg.count()
        counters.belegTotal = belege
        
        def belegeUnbeglichen = Beleg.unbeglichene.count()
        counters.belegOpen = belegeUnbeglichen
        
        def positionen = Position.count()
        counters.positionTotal = positionen
        
        def positionenUnzugewiesen = Position.countByBelegIsNull()
        counters.positionOpen = positionenUnzugewiesen
        
        def medikamente = Medikament.count()
        counters.medikamentTotal = medikamente
        
        def leistungen = Leistung.count()
        counters.leistungTotal = leistungen
        
        def tiere = Tier.count()
        counters.tierTotal = tiere
        
        def zahlungen = Zahlung.count()
        counters.zahlungTotal = zahlungen
        
        
        println("**** $controllerName.$actionName END")
        render counters as JSON
    }
    
    def erbracht = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def jahr
        def monat
        if(params.listrange) {
            jahr = params.listrange.jahr as Integer
            try {
                monat = Date.parse("MMM", params.listrange.monat)[Calendar.MONTH]
            } catch (Exception x) {
                monat = null
            }
        }
        
        def dateFrom
        def dateTo
        Calendar cal = new GregorianCalendar()
        
        if(monat == null || monat == "") {
            monat = ""
            if(jahr == null || jahr == "") {
                jahr = jahrAktuell
                cal.set(jahrAktuell, 0, 1)
                dateFrom = cal.getTime()
                cal.set(jahrAktuell, 11, 31)
                dateTo = cal.getTime()
            } else {
                cal.set(jahr, 0, 1)
                dateFrom = cal.getTime()
                cal.set(jahr, 11, 31)
                dateTo = cal.getTime()
            }
        } else {
            if(jahr == null || jahr == "") {
                jahr = jahrAktuell
                cal.set(jahrAktuell, monat, 1)
                dateFrom = cal.getTime()
                cal.set(jahrAktuell, monat, 31)
                dateTo = cal.getTime()
            } else {
                cal.set(jahr, monat, 1)
                dateFrom = cal.getTime()
                cal.set(jahr, monat, 31)
                dateTo = cal.getTime()
            }
        }

        def results = Position.erbrachtByDateRange(dateFrom, dateTo).list()
        def foundRecords = results.size();

        println("** results Class: " + results.getClass().toString())
        println("** foundRecords: " + foundRecords)
        println("** db query results: " + results)
        
        def medikamente = []
        def leistungen = []
        def sumMed = new BigDecimal("0.00")
        def sumLst = new BigDecimal("0.00")
        results.each {
            if(it[2].instanceOf(Medikament)) {
                sumMed = sumMed.add(new BigDecimal(it[1].toString()))
                medikamente.add([it[0].toString(), it[1].toString(), '"' + it[2].bezeichnung.toString() + '"'])
            } else {
                sumLst = sumLst.add(new BigDecimal(it[1].toString()))
                leistungen.add([it[0].toString(), it[1].toString(), '"' + it[2].bezeichnung.toString() + '"'])
            }
        }

        println("**** $controllerName.$actionName END")
        
        [
            listrange: [
                jahr: jahr,
                monat: params.listrange?.monat
            ],
            selectbox: [
                monatliste: monatListe,
                jahrliste: jahrListe
            ],   
            sums: [
                medikamentsumme: sumMed, 
                leistungsumme: sumLst
            ],
            tabledata: [
                medikament: medikamente, 
                leistung: leistungen
            ]
        ]
    }
    
    def tagesbericht = {
        println("**** $controllerName.$actionName START")
        println("** params: " + params)
        
        def searchDate
        if(params.datum == null || params.datum == "") {
            searchDate = new Date()
        } else {
            searchDate = Date.parse(g.message(code: "default.date.format"), params.datum)
        }

        def resultsPos = Position.findAllByDatum(searchDate)
        println("** Positionen results Class: " + resultsPos.getClass().toString())
        println("** Positionen query results: " + resultsPos)
        
        def resultsZal = Zahlung.findAllByDatum(searchDate)
        println("** Zahlungen results Class: " + resultsZal.getClass().toString())
        println("** Zahlungen query results: " + resultsZal)
        
        def sumPos = new BigDecimal("0.00")
        def sumZal = new BigDecimal("0.00")
        
        def arrayPos = []
        def arrayZal = []
        
        resultsPos.each {
            sumPos = sumPos.add(new BigDecimal(it.betrag.toString()))
            arrayPos.add(['"' + it.kunde.name.toString() + '"', 
                         '"' + it.typ.bezeichnung.toString() + '"',
                         it.betrag.toString()])
        }
        
        resultsZal.each {
            sumZal = sumZal.add(new BigDecimal(it.betrag.toString()))
            arrayZal.add(['"' + it.kunde.name.toString() + '"', 
                         it.betrag.toString()])
        }
        
        println("**** $controllerName.$actionName END")
        
        [
            datum: searchDate.format(g.message(code: "default.date.format")),
            sums: [
                positionsumme: sumPos, 
                zahlungsumme: sumZal
            ],
            tabledata: [
                position: arrayPos,
                zahlung: arrayZal
            ]
        ]
    }
}
