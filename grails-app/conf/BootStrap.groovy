import grails.util.Environment
import java.util.Calendar
import hierlmeier.*

class BootStrap {

    def init = { servletContext ->
        if (Environment.getCurrent() == Environment.DEVELOPMENT) {
            
            Medikament wurmkur
            Leistung krallen
            Tier kuh, katze, hund, meersau, karnickel, pferd
            Kunde huber
            Kunde holzmann
            Position pos1, pos2, pos3, pos4, pos5, pos6
            Beleg beleg1
            
            if (!Medikament.count()) {
                wurmkur = new Medikament(bezeichnung: "Wurmkur", preis: "12").save(failOnError: true)
                new Medikament(bezeichnung: "Penicillin", preis: "5").save(failOnError: true)
                new Medikament(bezeichnung: "Abführmittel", preis: "1").save(failOnError: true)
            }
            if (!Leistung.count()) {
                krallen = new Leistung(bezeichnung: "Krallen Stutzen", preis: "12").save(failOnError: true)
                new Leistung(bezeichnung: "Schnipp Schnapp, Scrotum ab", preis: "200").save(failOnError: true)
                new Leistung(bezeichnung: "Blinddarm raus", preis: "169").save(failOnError: true)
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
                holzmann = new Kunde(nachname: "Holzmann", vorname: "Erwin", adresse: "Kornstrasse 45/4-5",
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
                beleg1 = new Beleg(datum: new Date(Calendar.getInstance().getTimeInMillis()), belegnummer: "1111111", kunde: huber).save(failOnError: true)
                
            }
            if (!Position.count()) {
                pos1 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Mitzi die Kuh", tier: kuh,
                             typ: wurmkur, preis: "34", menge: "5", kunde: huber, beleg: beleg1).save(failOnError: true)
                pos2 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "Oskar der oarge Ochs", tier: kuh,
                             typ: krallen, preis: "34", menge: "2", kunde: huber, beleg: beleg1).save(failOnError: true)
                pos3 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix", tier: kuh,
                             typ: wurmkur, preis: "4", menge: "2", kunde: holzmann).save(failOnError: true)
                pos4 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix2", tier: karnickel,
                             typ: wurmkur, preis: "5", menge: "2", kunde: holzmann).save(failOnError: true)
                pos5 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix3", tier: katze,
                             typ: krallen, preis: "6", menge: "1", kunde: holzmann).save(failOnError: true)
                pos6 = new Position(datum: new Date(Calendar.getInstance().getTimeInMillis()), anmerkung: "nix4", tier: hund,
                             typ: wurmkur, preis: "7", menge: "2", kunde: holzmann).save(failOnError: true)
                
            }
            
            
            
            /*** @todo weitere testdaten reinhauen für dev env falls nötig
             *
            */
        }
            
    }
    def destroy = {
    }
}
