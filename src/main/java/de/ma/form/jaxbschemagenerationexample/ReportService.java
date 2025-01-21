package de.ma.form.jaxbschemagenerationexample;

import de.bafin.mvp.sp.v1.mvp.EmptyElementType;
import de.ma.form.generated.*;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ReportService {

    public ReportAuslagerungsanzeige generateReport() {
        ReportAuslagerungsanzeige report = new ReportAuslagerungsanzeige();

        // 1. Allgemeine Angaben
        report.setAllgemeineAngaben(createAllgemeineAngaben());

        // 2. Ansprechpartner
        report.setAnsprechpartner(createAnsprechpartner());

        // 3. Angaben zur Auslagerung
        report.setAngabenAuslagerung(createAngabenAuslagerung());

        // 4. Risikobewertung und Risikoanalyse
        report.setRisikobewertungRisikoanalyse(createRisikobewertung());

        // 5. Auslagerungsnehmer
        report.setAuslagerungsnehmer(createAuslagerungsnehmer());

        // 6. Vertragsdaten
        report.setVertragsdaten(createVertragsdaten());

        // 7. Cloud-Auslagerungen
        report.setCloudAuslagerungen(createCloudAuslagerungen());

        // 8. Subunternehmen
        report.setSubunternehmen(createSubunternehmen());

        // 9. Dateiupload (optional)
        report.setDateiupload(createDateiupload());

        return report;
    }

    private AllgemeineAngabenType createAllgemeineAngaben() {
        AllgemeineAngabenType angaben = new AllgemeineAngabenType();
        angaben.setArtUnternehmen(ArtUnternehmenType.KI);
        angaben.setArtMeldung(ArtMeldungType.ABSICHT);

        // Optional fields for specific cases
        if (angaben.getArtMeldung() == ArtMeldungType.UPDATE) {
            angaben.setReferenznummer("REF123456");

            // Set Grund für wesentliche Änderung
            GrundWesentlicheAenderungType grund = new GrundWesentlicheAenderungType();
            GrundWesentlicheAenderungEnumListeType grundListe = new GrundWesentlicheAenderungEnumListeType();
            grundListe.getAuswahl().addAll(List.of(
                    GrundWesentlicheAenderungEnumType.AENDERUNG_NEUE_RISIKOANALYSE,
                    GrundWesentlicheAenderungEnumType.VERTRAGSAENDERUNGEN
            ));
            grund.setAuswahlListe(grundListe);
            grund.setSonstige("Additional changes description");
            angaben.setGrundWesentlicheAenderung(grund);
        }

        // Add FondsListe if KVG
        if (angaben.getArtUnternehmen() == ArtUnternehmenType.KVG) {
            FondsListeType fondsListe = new FondsListeType();
            FondType fond = new FondType();
            fond.setIsin("DE0001234567");
            fond.setLei("529900W3MOO00A18PIR96");
            fondsListe.getFond().add(fond);
            angaben.setFondsListe(fondsListe);
        }

        return angaben;
    }

    private AnsprechpartnerType createAnsprechpartner() {
        AnsprechpartnerType ansprechpartner = new AnsprechpartnerType();
        ansprechpartner.setName("Max Mustermann");
        ansprechpartner.setTelefon("+49 123 456789");
        ansprechpartner.setEmail("max.mustermann@example.com");
        return ansprechpartner;
    }

    private AngabenAuslagerungType createAngabenAuslagerung() {
        AngabenAuslagerungType angaben = new AngabenAuslagerungType();
        angaben.setReferenznummer("AUS123456");
        angaben.setArtAuslagerung(ArtAuslagerungType.TEIL);
        angaben.setUmfangAuslagerung("Complete IT infrastructure management");
        angaben.setGrundAuslagerung("Cost optimization and access to specialized expertise");

        // Set AIFM grounds if applicable
        GrundGemAIFMVOType aifmGrund = new GrundGemAIFMVOType();
        GrundGemAIFMVOEnumListeType aifmListe = new GrundGemAIFMVOEnumListeType();
        aifmListe.getAuswahl().add(GrundGemAIFMVOEnumType.KOSTENEINSPARUNGEN);
        aifmGrund.setAuswahlListe(aifmListe);
        angaben.setGrundGemAIFMVO(aifmGrund);

        // Set wesentliche Auslagerung
        WesentlicheAuslagerungType wesentlich = new WesentlicheAuslagerungType();
        EmptyElementType ja = new EmptyElementType();
        wesentlich.setJa(ja);
        wesentlich.setBegruendung("Critical business process");
        wesentlich.setDatumWesentlichkeit(toXMLGregorianCalendar(LocalDate.now()));
        angaben.setWesentlicheAuslagerung(wesentlich);

        // Set weitere Angaben
        angaben.setUnterstuetztZeitkritischeGeschaeftsvorgaenge(true);
        angaben.setUebertragungPersonenbezogeneDaten(true);
        angaben.setVerarbeitungPersonenbezogeneDaten(true);
        angaben.setAuslagerungsvereinbarungGeltendesRecht("German Law");

        return angaben;
    }

    private RisikobewertungRisikoanalyseType createRisikobewertung() {
        RisikobewertungRisikoanalyseType risiko = new RisikobewertungRisikoanalyseType();
        risiko.setDatum(toXMLGregorianCalendar(LocalDate.now()));
        risiko.setErgebnis("Risk assessment shows moderate risk level with adequate controls in place");
        return risiko;
    }

    private AuslagerungsnehmerType createAuslagerungsnehmer() {
        AuslagerungsnehmerType nehmer = new AuslagerungsnehmerType();
        nehmer.setName("IT Services GmbH");
        nehmer.setHandelsregisternummer("HRB 12345");
        nehmer.setLei("529900W3MOO00A18PIR96");

        // Set address
        AdresseType adresse = new AdresseType();
        adresse.setStrasse("Musterstraße 123");
        adresse.setOrt("Berlin");
        adresse.setPlz("10115");
        StaatType staat = new StaatType();
        EmptyElementType deutschland = new EmptyElementType();
        staat.setDeutschland(deutschland);
        adresse.setStaat(staat);
        nehmer.setAdresse(adresse);

        // Set additional details
        nehmer.setZustaendigeAufsichtsbehoerde("BaFin");
        nehmer.setTeilDerGruppe(false);
        nehmer.setImEigentum(false);
        nehmer.setNutzenAuslagerung("Cost efficiency and specialized expertise");

        return nehmer;
    }

    private VertragsdatenType createVertragsdaten() {
        VertragsdatenType vertrag = new VertragsdatenType();
        vertrag.setVertragsbeginn(toXMLGregorianCalendar(LocalDate.now()));
        vertrag.setVertragsende(toXMLGregorianCalendar(LocalDate.now().plusYears(5)));
        vertrag.setNaechsteVerlaengerung(toXMLGregorianCalendar(LocalDate.now().plusYears(1)));
        vertrag.setJaehrlicheKosten(new BigDecimal("500000.00"));
        vertrag.setKuendigungsfristenUnternehmen(90); // 90 days
        vertrag.setKuendigungsfristenAuslagerungsnehmer(90); // 90 days
        return vertrag;
    }

    private CloudAuslagerungenType createCloudAuslagerungen() {
        CloudAuslagerungenType cloud = new CloudAuslagerungenType();
        EmptyElementType ja = new EmptyElementType();
        cloud.setJa(ja);
        cloud.setCloudDienstmodell(CloudDienstmodellType.IAAS);
        cloud.setCloudBereitstellungsmodell(CloudBereitstellungsmodellType.PRIVATE_CLOUD);
        return cloud;
    }

    private SubunternehmenType createSubunternehmen() {
        SubunternehmenType sub = new SubunternehmenType();
        sub.setWeiterverlagert(true);
        sub.setVerarbeitetPersonenbezogeneDaten(true);

        SubunternehmenDetailsListeType liste = new SubunternehmenDetailsListeType();
        SubunternehmenDetailsType details = new SubunternehmenDetailsType();
        details.setName("Cloud Provider GmbH");
        details.setId("SUB001");
        details.setStaatRegistratur("DE");
        details.setStaatDienstleistungserbringung("DE");
        details.setStaatDatenspeicherung("DE");
        liste.getSubunternehmenDetails().add(details);

        sub.setSubunternehmenDetailsListe(liste);
        return sub;
    }

    private DateiuploadType createDateiupload() {
        DateiuploadType upload = new DateiuploadType();
        upload.setDateiname("documentation.pdf");
        // Example base64 content - in practice, this would be real file content
        upload.setInhalt("SGVsbG8gV29ybGQ=".getBytes());
        return upload;
    }

    private XMLGregorianCalendar toXMLGregorianCalendar(LocalDate date) {
        try {
            GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        } catch (Exception e) {
            throw new RuntimeException("Error converting date", e);
        }
    }
}
