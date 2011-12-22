package hierlmeier

class Rechnung implements Serializable {
    
    String rechnungnummer
    Date datum  = new Date()
    Date dateCreated  //automatically maintained by GORM
    Date lastUpdated  //automatically maintained by GORM
    Kunde kunde
    BigDecimal betrag
    
    static hasMany = [belege:Beleg, zahlungsteile:Zahlungsteil]

    static constraints = {
        rechnungnummer(nullable:false, blank:false)
    }
    
    static mapping = {
        datum type:"date"
    }
    
    static namedQueries = {
        zuletztErstellteRechnungPerKunde {
            projections {
                max "datum"
                groupProperty "kunde"
            }
        }
    }
}
