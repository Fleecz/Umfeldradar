package de.umfeldradar.thema;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "thema")
public class Thema {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String kennung;
    private String titel;
    private String cluster;
    private String beschreibung;
    private String chancen;
    private String risiken;
    @Version
    private Integer version;
    @Enumerated(EnumType.STRING)
    private Herkunft herkunft;
    @Enumerated(EnumType.STRING)
    private Pestel pestel;
    @Enumerated(EnumType.STRING)
    private ThemaStatus status;

    private static void pruefePflichteingabe(String wert, String feldname) {
        if (wert == null || wert.isBlank()) {
            throw new IllegalArgumentException(feldname + " darf nicht leer sein");
        }
    }
    private void setzeInhalte(
            String titel, String cluster, String beschreibung,
            String chancen, String risiken, Pestel pestel) {
        pruefePflichteingabe(titel, "Titel");
        pruefePflichteingabe(beschreibung, "Beschreibung");
        pruefePflichteingabe(chancen, "Chancen");
        pruefePflichteingabe(risiken, "Risiken");
        if (pestel == null) {
            throw new IllegalArgumentException("Pestel darf nicht null sein.");
        }
        this.titel = titel;
        this.cluster = cluster;
        this.beschreibung = beschreibung;
        this.chancen = chancen;
        this.risiken = risiken;
        this.pestel = pestel;
    }
    protected Thema() {
    }
    public Thema(String kennung, String titel, String cluster, String beschreibung, String chancen, String risiken, Pestel pestel, Herkunft herkunft, ThemaStatus status) {
        setzeInhalte(titel, cluster, beschreibung, chancen, risiken, pestel);
        if (herkunft == null) {
            throw new IllegalArgumentException("Herkunft darf nicht null sein.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status darf nicht null sein.");
        }
        this.kennung = kennung;
        this.herkunft = herkunft;
        this.status = status;
    }
    public String getKennung() {
        return kennung;
    }
    public String getTitel() {
        return titel;
    }
    public UUID getId() {
        return id;
    }
    public ThemaStatus getStatus() {
        return status;
    }
    public String getBeschreibung() {
        return beschreibung;
    }
    public Pestel getPestel() {
        return pestel;
    }
    public String getChancen() {
        return chancen;
    }
    public String getRisiken() {
        return risiken;
    }
    public String getCluster() {
        return cluster;
    }
    public void aktualisieren(String titel, String cluster, String beschreibung, String chancen, String risiken, Pestel pestel){
        setzeInhalte(titel, cluster, beschreibung, chancen, risiken, pestel);
    }
    public void archivieren() {
        this.status = ThemaStatus.ARCHIVIERT;
    }
}