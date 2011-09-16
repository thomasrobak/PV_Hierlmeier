package hierlmeier

class Zahlungsteil implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    BigDecimal betrag      // == teilbetrag einer Zahlung
    
    Zahlung zahlung
    Beleg beleg

    static constraints = {
        //betrag(scale:2) /*@todo checken wo ma wie die nachkommastellen checkt 
        //weil hier in den constraints, bedeutet das nur das abgeschnitte wird,
        //und kein fehler geschmissen
    }
    
    def String toString() {
        return "Zahlteil ${betrag} of ${zahlung}"
    }
}
