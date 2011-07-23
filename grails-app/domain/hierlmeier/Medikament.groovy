package hierlmeier

class Medikament extends Positionstyp {
    

    static constraints = {
    }
    
    def String toString() {
        return "Medikament: $bezeichnung"
    }
}
