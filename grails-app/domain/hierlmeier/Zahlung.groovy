package hierlmeier

class Zahlung implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    Kunde kunde
    BigDecimal betrag
    Date datum = new Date()
    Date dateCreated  //automatically maintained by GORM
    Date lastUpdated  //automatically maintained by GORM
    
    static hasMany = [zahlungsteile:Zahlungsteil]
    

    static constraints = {
        zahlungsteile(nullable:true) //@todo eigentlich false, zahlungscontroller checken die validates müssen anders behandelt werden!
        betrag(shared: "currencynumber")
    }
    
    static mapping = {
        datum type:"date"
    }
    
    def String toString() {
        return "Zahlung by ${kunde} amount ${betrag}"
    }
}
