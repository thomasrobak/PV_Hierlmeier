package hierlmeier

class Position implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    Date datum
    String anmerkung
    
    Tier tier
    Positionstyp typ    // Medikament oder Leistung
    BigDecimal preis    // zB Preis abweichend vom Katalogpreis (= typ.preis)
    Integer menge       // im falle von typ==leistung sollte menge 1 sein oder whatever
    BigDecimal betrag   // derived property aus menge * preis; formel siehe unten
    Kunde kunde
    Beleg beleg
    Boolean userAction  //transient; flag for misc. user interaction in view
        
    static transients = ['userAction']

    static mapping = {
      betrag formula: 'MENGE * PREIS'
    }
    
    static constraints = {
        beleg(nullable:true)
        anmerkung(blank:true)   //@todo check if evtl noch nullable:true
        menge(nullable:false, min:new Integer(1))
    }
    
    static namedQueries = {
        betragSumme { posList ->    //@todo make this work?
            inList 'id', ${posList}
            projections {
                sum 'betrag'
            }
        }
    }
    
    def String toString() {
        return "Position: ${typ} : ${kunde} : ${datum}"
    }
}