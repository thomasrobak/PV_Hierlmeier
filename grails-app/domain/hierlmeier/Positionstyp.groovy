package hierlmeier

class Positionstyp implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    Integer preis
    String bezeichnung

    static constraints = {
        bezeichnung(blank:false)
    }
    
}
