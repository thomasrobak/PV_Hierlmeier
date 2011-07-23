package hierlmeier

class Zahlung {
    
    Kunde kunde
    Integer betrag
    
    static hasMany = [zahlungsteile:Zahlungsteil]
    

    static constraints = {
    }
    
    def String toString() {
        return "Zahlung von: $kunde : $betrag"
    }
}
