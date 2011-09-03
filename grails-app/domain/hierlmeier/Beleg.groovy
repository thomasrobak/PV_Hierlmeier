package hierlmeier

class Beleg implements Serializable { //muss Serializable implementieren für Flows in Grails
    
    String belegnummer
    //Boolean bezahlt //@todo das flag doch behalten?
    Date datum
    Kunde kunde
    BigDecimal brutto
    BigDecimal netto
    BigDecimal betrag
    BigDecimal summeBezahlt
        
    static hasMany = [positionen:Position, zahlungsteile:Zahlungsteil]

    static constraints = {
        belegnummer(blank:false, unique:true, nullable:false)
        zahlungsteile(nullable:true)
        positionen(nullable:true) //@todo eigentlich false aber bootstrap spinnt sonst für dev env
    }
    
    static namedQueries = {
        unbeglichene {
            gtProperty 'betrag', 'summeBezahlt'
        }
        
    }
    
    /*
    static def unbeglichene () {
        return Beleg.findAll("from Beleg as b where b.id not in (select beleg from Zahlungsteil group by beleg) or b.id in (select zt.beleg, sum(zt.betrag) as sumpaid from Zahlungsteil as zt, Beleg as blg where blg.id = zt.beleg and blg.betrag > sumpaid)")
    }
    */
    /*
    def getBrutto () {
        def sum = Position.executeQuery("select sum(p.betrag) from Position as p where p.beleg = ?", [this.id])
        def calc = new BigDecimal(sum.toString()).multiply(new BigDecimal(g.message(code:'default.tax.rate')))
        //needs rounding because the scale maybe to long after multiplication
        calc = calc.setScale(g.message(code:'default.scale').toInteger(), RoundingMode.valueOf(g.message(code:'default.rounding.mode')))
        println("getBrutto of " + this.toString() + ": " + calc.toString())
        return calc
    }
    
    def getNetto () {
        def sum = Position.executeQuery("select sum(p.betrag) from Position as p where p.beleg = ?", [this.id])
        def calc = new BigDecimal(sum.toString()) //return type from hibernate for sum() is double so cast it to BigDecimal
        //no need for rounding because there are no more than 2 nachkommastellen
        println("getNetto of " + this.toString() + ": " + calc.toString())
        return calc 
    }
    
    def getBetrag () {
        if(kunde.mwst) {
            println("getBetrag of " + this.toString() + " is inkl mwst")
            return getBrutto()
        }
        else {
            println("getBetrag of " + this.toString() + " is exkl mwst")
            return getNetto()
        }   
    }
    
    def getSummeBezahlt () {
        if(isEmpty("zahlungsteile")) {
            return new BigDecimal("0.00")
        }
        else {
            def sum = Zahlungsteil.executeQuery("select sum(zt.betrag) from Zahlungsteil as zt where zt.beleg = ?", [this.id])
            def calc = new BigDecimal(sum.toString()) //return type from hibernate for sum() is double so cast it to BigDecimal
            return calc
        }
    }
    */
    
    def String toString () {
        return "Beleg ${belegnummer}"
    }
}
