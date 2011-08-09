package hierlmeier

class Beleg implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    String belegnummer
    //Boolean bezahlt //@todo das flag doch behalten?
    //Integer summebezahlt //@todo doch behalten oder jedesmal rechnen?
    
    Date datum
    Kunde kunde
    
    static hasMany = [positionen:Position, zahlungsteile:Zahlungsteil]

    static constraints = {
        belegnummer(blank:false, unique:true, nullable:false)
        zahlungsteile(nullable:true)
        positionen(nullable:true) //@todo nicht ganz richtig eigentlich
    }
    
    def String toString () {
        return "Belegnr.: ${belegnummer}"
    }
}
