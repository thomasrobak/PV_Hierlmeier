package hierlmeier

class Zahlungsteil {
    
    Integer betrag      // == teilbetrag einer Zahlung
    
    Zahlung zahlung
    Beleg beleg

    static constraints = {
    }
    
    def String toString() {
        return "zahlteil:$betrag von zahlung:$zahlung.betrag"
    }
}
