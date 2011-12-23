import grails.util.Environment
import java.util.Calendar
import hierlmeier.*
import grails.converters.JSON
import grails.converters.XML

class BootStrap {

    def init = { servletContext ->
        
        println("************* PVHM started at " + new Date().toString())
        println("********** Bootstrap Strizzi Actions Start")
                
        // read the servletContext path and add the /xml directory to it
        // this is needed because dev and prod environments have different paths
        def xslstylesheetdir = new String(servletContext.getRealPath(File.separator) + "xml" + File.separator) 
        println("*** xsl-stylesheets directory: " + xslstylesheetdir)
        
        // add the xsl stylesheet names to the path we got above
        // xsl stylesheets are needed for pdf transformation in hierlmeier.PdfService
        String belegstylesheetname = "belegstylesheet.xsl" //@todo vllt noch in ein config file verlagern
        println("*** name of the xsl-stylesheet for Beleg: " + belegstylesheetname)
        String rechnungstylesheetname = "rechnungstylesheet.xsl" //@todo vllt noch in ein config file verlagern
        println("*** name of the xsl-stylesheet for Rechnung: " + belegstylesheetname)
        
        // load files for xsl-stylesheets
        def belegstylesheet = new File(new String(xslstylesheetdir + belegstylesheetname))
        println("*** loaded file: " + belegstylesheet)
        def rechnungstylesheet = new File(new String(xslstylesheetdir + rechnungstylesheetname))
        println("*** loaded file: " + rechnungstylesheet)
        
        // add xsl-stylesheet files to servletContext so they can be accessed from within the app
        servletContext.setAttribute("BelegStyleSheet", belegstylesheet)
        println("*** added attribute BelegStyleSheet to serlvetContext")
        servletContext.setAttribute("RechnungStyleSheet", rechnungstylesheet)
        println("*** added attribute RechnungStyleSheet to serlvetContext")
        
        // test added attributes
        println("****** Test Added ServletContext Attributes:")
        def testattribute = servletContext.getAttribute("BelegStyleSheet")
        println("*** attribute BelegStyleSheet: " + testattribute)
        testattribute = servletContext.getAttribute("RechnungStyleSheet")
        println("*** attribute RechnungStyleSheet: " + testattribute)
        
        println("****** Register Custom Marshaller for JSON rendering of Object Kunde")
        JSON.registerObjectMarshaller(Kunde) {
            def returnArray = [:]
            returnArray['id'] = it.id
            returnArray['nachname'] = it.nachname
            returnArray['vorname'] = it.vorname
            returnArray['name'] = it.name
            returnArray['adresse'] = it.adresse
            returnArray['wohnort'] = it.wohnort
            returnArray['telefonnummer'] = it.telefonnummer
            returnArray['beruf'] = it.beruf
            returnArray['mwst'] = it.mwst
            returnArray['bemerkung'] = it.bemerkung
            returnArray['zahllast'] = it.zahllast
            returnArray['letztesrechnungsdatum'] = it.letztesrechnungsdatum
            return returnArray
        }
        JSON.use("deep"){
            JSON.registerObjectMarshaller(Kunde) {
                def returnArray = [:]
                returnArray['id'] = it.id
                returnArray['nachname'] = it.nachname
                returnArray['vorname'] = it.vorname
                returnArray['name'] = it.name
                returnArray['adresse'] = it.adresse
                returnArray['wohnort'] = it.wohnort
                returnArray['telefonnummer'] = it.telefonnummer
                returnArray['beruf'] = it.beruf
                returnArray['mwst'] = it.mwst
                returnArray['bemerkung'] = it.bemerkung
                returnArray['zahllast'] = it.zahllast
                returnArray['letztesrechnungsdatum'] = it.letztesrechnungsdatum
                return returnArray
            }
        }
        println("****** Register Custom Marshaller for XML rendering of Object Kunde")
        XML.use("deep"){
            XML.registerObjectMarshaller(Kunde) { kunde, converter ->
                converter.attribute("id", kunde.id.toString())
                converter.build {
                    nachname(kunde.nachname)
                    vorname(kunde.vorname)
                    name(kunde.name)
                    adresse(kunde.adresse)
                    wohnort(kunde.wohnort)
                    telefonnummer(kunde.telefonnummer)
                    beruf(kunde.beruf)
                    mwst(kunde.mwst)
                    bemerkung(kunde.bemerkung)
                    zahllast(kunde.zahllast)
                }
            }
        }
        XML.registerObjectMarshaller(Kunde) { kunde, converter ->
            converter.attribute("id", kunde.id.toString())
            converter.build {
                nachname(kunde.nachname)
                vorname(kunde.vorname)
                name(kunde.name)
                adresse(kunde.adresse)
                wohnort(kunde.wohnort)
                telefonnummer(kunde.telefonnummer)
                beruf(kunde.beruf)
                mwst(kunde.mwst)
                bemerkung(kunde.bemerkung)
                zahllast(kunde.zahllast)
            }
        }

        
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
            Beleg beleg1, beleg2
            
            if (!Medikament.count()) {
                wurmkur = new Medikament(bezeichnung: "Wurmkur", preis: new BigDecimal("12.70")).save(failOnError: true)
                penic = new Medikament(bezeichnung: "Penicillin", preis: new BigDecimal("5.50")).save(failOnError: true)
                gacken = new Medikament(bezeichnung: "Abführmittel", preis: new BigDecimal("1.99")).save(failOnError: true)
            }
            if (!Leistung.count()) {
                krallen = new Leistung(bezeichnung: "Krallen Stutzen", preis: new BigDecimal("12.00")).save(failOnError: true)
                scrotum = new Leistung(bezeichnung: "Schnipp Schnapp, Scrotum ab", preis: new BigDecimal("200.00")).save(failOnError: true)
                appendix = new Leistung(bezeichnung: "Blinddarm raus", preis: new BigDecimal("169.00")).save(failOnError: true)
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
                    netto: "184.34", brutto: "221.21", betrag: "221.21", bezahlt: "0.00")
                beleg2 = new Beleg(datum: new Date(Calendar.getInstance().getTimeInMillis()), belegnummer: "1112222", kunde: vielpos,
                    netto: "35.95", brutto: "43.14", betrag: "35.95", bezahlt: "0.00")
                
                
            }
            if (!Position.count()) {
                // Positionen in Beleg für Kunde Huber
                pos1 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Mitzi die Kuh", tier: kuh,
                    typ: wurmkur, preis: "34.23", menge: "5", kunde: huber, beleg: beleg1).save(failOnError: true)
                pos2 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Oskar der oarge Ochs", tier: kuh,
                    typ: krallen, preis: "13.19", menge: "1", kunde: huber, beleg: beleg1).save(failOnError: true)
                
                beleg1.positionen = [pos1, pos2]
                beleg1.save(failOnError: true)
                
                // Positionen ohne Beleg für Kunde Huber
                new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Mitzi die Kuh", tier: katze,
                    typ: appendix, preis: "9.99", menge: "1", kunde: huber).save(failOnError: true)
                new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Oskar der oarge Ochs", tier: hund,
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
                
                // Positionen in Beleg für Kunde VielePositionen
                pos15 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "xinwerwer", tier: katze,
                    typ: penic, preis: "7.19", menge: "2", kunde: vielpos).save(failOnError: true)
                pos16 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix123123", tier: hund,
                    typ: penic, preis: "7.19", menge: "3", kunde: vielpos).save(failOnError: true)
                
                beleg2.positionen = [pos15, pos16]
                beleg2.save(failOnError: true)
                
            }
            
            
            
            /*** @todo weitere testdaten reinhauen für dev env falls nötig
             *
             */
        }
            
    }
    def destroy = {
    }
}
