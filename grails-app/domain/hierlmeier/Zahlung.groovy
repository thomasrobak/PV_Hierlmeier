package hierlmeier

class Zahlung implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    Kunde kunde
    BigDecimal betrag
    Date datum = new Date()
    
    static hasMany = [zahlungsteile:Zahlungsteil]
    

    static constraints = {
        zahlungsteile(nullable:true)
        betrag(shared: "currencynumber")
    }
    
    def String toString() {
        return "Zahlung of ${kunde} amount ${betrag}"
    }
}
