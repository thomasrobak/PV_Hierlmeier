package hierlmeier

class Position implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    Date datum
    String anmerkung
    
    Tier tier
    Positionstyp typ    // Medikament oder Leistung
    BigDecimal preis    // zB Preis abweichend vom Katalogpreis (= typ.preis)
    Integer menge       // im falle von typ==leistung is menge==null
    Kunde kunde
    Beleg beleg
    Boolean userAction  //transient; flag for misc. user interaction in view
    
    static transients = ['userAction']

    
    static constraints = {
        beleg(nullable:true)
        anmerkung(blank:true)   //@todo check if evtl noch nullable:true
        menge(nullable:true)
    }
    
    def String toString() {
        return "Position: $typ : $kunde : $datum"
    }
}