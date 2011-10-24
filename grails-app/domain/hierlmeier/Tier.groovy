package hierlmeier

class Tier implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    String bezeichnung //tierart
    Date dateCreated  //automatically maintained by GORM
    Date lastUpdated  //automatically maintained by GORM

    static constraints = {
    }
    
    def String toString () {
        return "${bezeichnung}"
    }
}
