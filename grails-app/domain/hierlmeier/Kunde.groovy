package hierlmeier

import org.springframework.context.i18n.LocaleContextHolder

class Kunde implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    String nachname
    String vorname
    String adresse
    String wohnort
    String telefonnummer    //optional
    String beruf            //optional
    Boolean mwst            //@todo VORSICHT! wenn der zustand der mwst im nachhinein geändert wird, ergibt sich für bestehende belege/rechunge ein undefinierter zustand und fehler!
    String bemerkung        //optional
    Date dateCreated  //automatically maintained by GORM
    Date lastUpdated  //automatically maintained by GORM
        
    static hasMany = [positionen:Position, belege:Beleg, zahlungen:Zahlung, rechnungen:Rechnung]
  
    static constraints = {
        nachname(blank:false)
        vorname(blank:false)
        adresse(blank:false)
        wohnort(blank:false)
        telefonnummer(blank:true)
        beruf(blank:true)
        bemerkung(blank:true)
    }   
    
    static transients = ['zahllast', 'letztesrechnungsdatum']
    
    BigDecimal getZahllast() {
        def result = Beleg.aktuelleZahllastSpecificKunde(this).list()
        if(result == null) { // => Kunde has no Transactions whatsoever (yet)
            return new BigDecimal("0.00")
        } else if(result.size() == 1) {
            def offen = new BigDecimal(result[0][0].toString())
            def bezahlt = new BigDecimal(result[0][1].toString())
            return offen.subtract(bezahlt)
        } else if(result.size() > 1) { //sanity check, should be only 1 result
            println("*** Error during Marshalling of Kunde Object (transient value Zahllast) query returned too many results (should have been only 1 result)")
            println("** returning the fallback value of '13.37' for Zahllast of Kunde [" + this.toString() + "]")
            return new BigDecimal("13.37")
        } else { // (size < 1) => Kunde has no Transactions whatsoever (yet)
            return new BigDecimal("0.00")
        }
    }
    
    Date getLetztesrechnungsdatum() {
        def result = Rechnung.zuletztErstellteRechnungPerKunde.findByKunde(this)
        if(result == null) { // => Kunde has no Rechnung whatsoever (yet)
            return null
        } else if(result.size() == 1) {
            def rechnung = result[0]
            def datum = rechnung.datum
            return datum
        } else if(result.size() > 1) { //sanity check, should be only 1 result
            println("*** Error during Marshalling of Kunde Object (transient value letztesRechnungsDatum) query returned too many results (should have been only 1 result)")
            println("** returning the fallback value of NULL for letztesRechnungsDatum of Kunde [" + this.toString() + "]")
            return null
        } else { // (size < 1) => Kunde has no Transactions whatsoever (yet)
            return null
        }
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
