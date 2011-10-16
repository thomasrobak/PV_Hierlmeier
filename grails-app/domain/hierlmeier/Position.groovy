package hierlmeier

class Position implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    Date datum = new Date()
    String anmerkung
    
    Tier tier
    Positionstyp typ                // Medikament oder Leistung
    BigDecimal preis                // zB Preis abweichend vom Katalogpreis (= typ.preis)
    Integer menge = new Integer(1)  // im falle von typ==leistung sollte menge 1 sein oder whatever
    BigDecimal betrag               // derived property aus menge * preis; formel siehe unten
    Kunde kunde
    Beleg beleg

    static mapping = {
        betrag formula: 'MENGE * PREIS'
    }
    
    static constraints = {
        beleg(nullable:true)
        anmerkung(blank:true)   //@todo check if evtl noch nullable:true
        menge(nullable:false, min:new Integer(1))
        preis(shared: "currencynumber")
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
        return "Pos ${typ} for ${kunde} on ${datum}"
    }
    
    def String toStringDetailed() {
        return "{ID: " + this.id +
               ", Typ: " + this.typ + 
               ", Datum: " + this.datum +
               ", Tier: " + this.tier +
               ", Anmerkung: " + this.anmerkung + 
               ", Beleg: " + this.beleg + 
               ", Kunde: " + this.kunde + 
               ", Menge: " + this.menge +
               ", Preis: " + this.preis +
               ", Betrag: " + this.betrag +
               "}"
    }
}