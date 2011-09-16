package hierlmeier

import grails.converters.JSON

class StatistikController {

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
}
