package hierlmeier

class Zahlung {
    
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
