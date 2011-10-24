package hierlmeier

class Positionstyp implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    BigDecimal preis
    String bezeichnung
    Date dateCreated  //automatically maintained by GORM
    Date lastUpdated  //automatically maintained by GORM

    static constraints = {
        bezeichnung(blank:false)
        preis(shared: "currencynumber")
    }
    
}
