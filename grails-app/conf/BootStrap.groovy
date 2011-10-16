import grails.util.Environment
import java.util.Calendar
import hierlmeier.*

class BootStrap {

    def init = { servletContext ->
        
        println()
        println("********** Bootstrap Strizzi Actions Start")
                
        // read the servletContext path and add the /xml directory to it
        // this is needed because dev and prod environments have different paths
        def xslstylesheetdir = new String(servletContext.getRealPath("/") + "xml/") 
        println("*** xsl-stylesheets directory: " + xslstylesheetdir)
        
        // add the xsl stylesheet names to the path we got above
        // xsl stylesheets are needed for pdf transformation in hierlmeier.PrintService
        String belegstylesheetname = "belegstylesheet.xsl" //@todo vllt noch in ein config file verlagern
        println("*** name of the xsl-stylesheet for Beleg: " + belegstylesheetname)
        
        // load files for xsl-stylesheets
        def belegstylesheet = new File(new String(xslstylesheetdir + belegstylesheetname))
        println("*** loaded file: " + belegstylesheet)
        
        // add xsl-stylesheet files to servletContext so they can be accessed from within the app
        servletContext.setAttribute("BelegStyleSheet", belegstylesheet)
        println("*** added attribute BelegStyleSheet to serlvetContext")
        
        // test added attributes
        println("********** Test Added ServletContext Attributes:")
        def testattribute = servletContext.getAttribute("BelegStyleSheet")
        println("*** attribute BelegStyleSheet: " + testattribute)
       
        //insert further init äktschns here

        // end of Strizzi Bootstrap Äktschns
        println("********** Bootstrap Strizzi Actions Ende")
        println()
        
        if (Environment.getCurrent() == Environment.DEVELOPMENT) {
            
            Medikament wurmkur, penic, gacken
            Leistung krallen, scrotum, appendix
            Tier kuh, katze, hund, meersau, karnickel, pferd
            Kunde huber
            Kunde vielpos
            Position pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13, pos14, pos15, pos16
            Beleg beleg1
            
            if (!Medikament.count()) {
                wurmkur = new Medikament(bezeichnung: "Wurmkur", preis: "12.70").save(failOnError: true)
                penic = new Medikament(bezeichnung: "Penicillin", preis: new BigDecimal("5.50")).save(failOnError: true)
                gacken = new Medikament(bezeichnung: "Abführmittel", preis: "1.99").save(failOnError: true)
            }
            if (!Leistung.count()) {
                krallen = new Leistung(bezeichnung: "Krallen Stutzen", preis: "12.00").save(failOnError: true)
                scrotum = new Leistung(bezeichnung: "Schnipp Schnapp, Scrotum ab", preis: "200.00").save(failOnError: true)
                appendix = new Leistung(bezeichnung: "Blinddarm raus", preis: "169.00").save(failOnError: true)
            }
            if (!Tier.count()) {
                kuh = new Tier(bezeichnung: "Kuh").save(failOnError: true)
                meersau = new Tier(bezeichnung: "Meersau").save(failOnError: true)
                karnickel = new Tier(bezeichnung: "Karnickel").save(failOnError: true)
                katze = new Tier(bezeichnung: "Katze").save(failOnError: true)
                hund = new Tier(bezeichnung: "Hund").save(failOnError: true)
                pferd = new Tier(bezeichnung: "Pferd").save(failOnError: true)
            }
            if (!Kunde.count()) {
                huber = new Kunde(nachname: "Huber", vorname: "Franz", adresse: "Landweg 12",
                          wohnort: "Mistelbach", telefonnummer: "0-555-123456",
                          beruf: "", bemerkung: "da Franz is a guada Spezl", mwst: "true").save(failOnError: true)
                vielpos = new Kunde(nachname: "VielePositionen", vorname: "Erwin", adresse: "Kornstrasse 45/4-5",
                          wohnort: "45678 Fürstenfeld", telefonnummer: "", beruf: "Zahnarzt",
                          bemerkung: "", mwst: "false").save(failOnError: true)
                new Kunde(nachname: "Nachname555", vorname: "Vorname555", adresse: "Adressenweg 555",
                          wohnort: "Wohnort555", telefonnummer: "0-555-555",
                          beruf: "Beruf555", bemerkung: "Bemerkung555", mwst: "true").save(failOnError: true)
                new Kunde(nachname: "Nachname666", vorname: "Vorname666", adresse: "Adresseweg 666",
                          wohnort: "666 Wohnort", telefonnummer: "keine", beruf: "keiner",
                          bemerkung: "keine", mwst: "false").save(failOnError: true)
                
            }
            if (!Beleg.count()) {
                beleg1 = new Beleg(datum: new Date(Calendar.getInstance().getTimeInMillis()), belegnummer: "1111111", kunde: huber,
                    netto: "184.34", brutto: "221.21", betrag: "221.21", summeBezahlt: "0.00").save(failOnError: true)
                
                
            }
            if (!Position.count()) {
                // 2 Positionen in Beleg für Kunde Huber
                pos1 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Mitzi die Kuh", tier: kuh,
                    typ: wurmkur, preis: "34.23", menge: "5", kunde: huber, beleg: beleg1).save(failOnError: true)
                pos2 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Oskar der oarge Ochs", tier: kuh,
                    typ: krallen, preis: "13.19", menge: "1", kunde: huber, beleg: beleg1).save(failOnError: true)
                
                // 2 Positionen ohne Beleg für Kunde Huber
                pos7 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Mitzi die Kuh", tier: katze,
                    typ: appendix, preis: "9.99", menge: "1", kunde: huber).save(failOnError: true)
                pos8 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Oskar der oarge Ochs", tier: hund,
                    typ: scrotum, preis: "8.79", menge: "1", kunde: huber).save(failOnError: true)
                
                // Positionen ohne Beleg für Kunde VielePositionen
                pos3 = new Position(datum: new Date(123141411325), anmerkung: "nix", tier: kuh,
                    typ: wurmkur, preis: "4.12", menge: "2", kunde: vielpos).save(failOnError: true)
                pos4 = new Position(datum: new Date(111231231234), anmerkung: "nix2", tier: karnickel,
                    typ: gacken, preis: "5.32", menge: "2", kunde: vielpos).save(failOnError: true)
                pos5 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix3", tier: katze,
                    typ: appendix, preis: "6.12", menge: "1", kunde: vielpos).save(failOnError: true)
                pos6 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos7 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos8 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos9 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos10 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos11 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos12 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos13 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos14 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos15 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos16 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                
            }
            
            
            
            /*** @todo weitere testdaten reinhauen für dev env falls nötig
             *
            */
        }
            
    }
    def destroy = {
    }
}
