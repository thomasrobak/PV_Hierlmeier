package hierlmeier

class Beleg implements Serializable { //muss Seriazable implementieren für Flows in Grails
    
    String belegnummer
    //Boolean bezahlt //@todo das flag doch behalten?
    //Integer summebezahlt //@todo doch behalten oder jedesmal rechnen?
    
    Kunde kunde
    
    static hasMany = [positionen:Position, zahlungsteile:Zahlungsteil]

    static constraints = {
        belegnummer(blank:false, unique:true, nullable:false)
    }
    
    def String toString () {
        return "Belegnr.: ${belegnummer}"
    }
}
