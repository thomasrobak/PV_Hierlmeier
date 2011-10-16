package hierlmeier

class Zahlungsteil implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    BigDecimal betrag      // == teilbetrag einer Zahlung
    
    Zahlung zahlung
    Beleg beleg

    static constraints = {
        betrag(shared: "currencynumber")
    }
    
    /*
    def afterInsert() { // Executed after an object is persisted to the database
            beleg.betrag = beleg.betrag.add(betrag)
            beleg.save()  
    } 
    */
    
    def String toString() {
        return "Zahlteil ${betrag} of ${zahlung}"
    }
}
