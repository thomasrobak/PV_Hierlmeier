package hierlmeier

class Tier implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    String bezeichnung //tierart

    static constraints = {
    }
    
    def String toString () {
        return "${bezeichnung}"
    }
}
