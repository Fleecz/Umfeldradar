package de.umfeldradar.thema;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ThemaRepositoryTest {
    @Autowired
    private ThemaRepository themaRepository;

    @Test
    public void saveThema() {
        Thema thema = new Thema("T-0001", "KI in der Produktion", "Digitalisierung", "Beschreibung des Themas", "Chancen des Themas", "Risiken des Themas", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaRepository.save(thema);
        var gefunden = themaRepository.findByKennung("T-0001");
        assertTrue(gefunden.isPresent());
    }
    @Test
    void findetThemenNachPestel(){
        Thema thema_tech = new Thema("T-001", "Titel_Tech", "Cluster", "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV );
        Thema thema_pol = new Thema("T-002", "Titel_Pol", "Cluster", "Beschreibung", "Chancen", "Risiken", Pestel.POLITISCH, Herkunft.MANUELL, ThemaStatus.AKTIV );
        themaRepository.save(thema_tech);
        themaRepository.save(thema_pol);
        List<Thema> ergebnis = themaRepository.findByPestel(Pestel.TECHNOLOGISCH);
        assertEquals(1, ergebnis.size());
        assertEquals("Titel_Tech", ergebnis.get(0).getTitel());
    }
}