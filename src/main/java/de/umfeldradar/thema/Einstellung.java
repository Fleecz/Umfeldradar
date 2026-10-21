package de.umfeldradar.thema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "einstellung")
public class Einstellung {
    @Id
    private String schluessel;
    private int wert;
    protected Einstellung() {}
    String getSchluessel() {
        return schluessel;
    }
    int getWert() {
        return wert;
    }
    public void wertAendern(int neuerWert) {
        if (neuerWert < 1) {
            throw new IllegalArgumentException("Wert muss mindestens 1 sein");
        }
        this.wert = neuerWert;
    }
}