package hierlmeier

class Zahlung implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    Kunde kunde
    BigDecimal betrag
    Date datum
    
    static hasMany = [zahlungsteile:Zahlungsteil]
    

    static constraints = {
        zahlungsteile(nullable:true)
    }
    
    def String toString() {
        return "Zahlung of ${kunde} amount ${betrag}"
    }
}
