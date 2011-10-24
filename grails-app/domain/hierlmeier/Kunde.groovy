package hierlmeier

class Kunde implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    String nachname
    String vorname
    String adresse
    String wohnort
    String telefonnummer    //optional
    String beruf            //optional
    Boolean mwst
    String bemerkung        //optional
    Date dateCreated  //automatically maintained by GORM
    Date lastUpdated  //automatically maintained by GORM
    
    static hasMany = [positionen:Position, belege:Beleg, zahlungen:Zahlung]
    
   
    static constraints = {
        nachname(blank:false)
        vorname(blank:false)
        adresse(blank:false)
        wohnort(blank:false)
        telefonnummer(blank:true)
        beruf(blank:true)
        bemerkung(blank:true)
    }
    
    static namedQueries = { 
        withUnprocessedPositionen {
            isNotEmpty("positionen")
            positionen {
                isNull("beleg")
            }
        }
        withUnpaidBelege {
            isNotEmpty("belege")
            belege {
                gtProperty 'betrag', 'bezahlt'
            }
        }
    }

    
    def String toString () {
        return "${nachname} ${vorname}"
    }
}
