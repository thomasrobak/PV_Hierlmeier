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
    }

    
    def String toString () {
        return "${nachname} ${vorname}"
    }
}
