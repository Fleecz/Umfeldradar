package de.umfeldradar.thema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThemaTest {
    @Test
    void lehntLeerenTitelAb(){
        assertThrows(IllegalArgumentException.class, () -> {
            Thema thema = new Thema("T-TEST-001", "", "Digitalisierung", "Beschreibung des Themas", "Chancen des Themas", "Risiken des Themas", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        });
    }
    @Test
    void lehntFehlendesPestelAb(){
        assertThrows(IllegalArgumentException.class, () -> {
            Thema thema = new Thema("T-TEST-001", "", "Digitalisierung", "Beschreibung des Themas", "Chancen des Themas", "Risiken des Themas", null,Herkunft.MANUELL, ThemaStatus.AKTIV);
        });
    }
}