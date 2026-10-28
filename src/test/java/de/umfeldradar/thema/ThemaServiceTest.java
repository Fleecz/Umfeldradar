package de.umfeldradar.thema;

import de.umfeldradar.AbstractPostgresIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class ThemaServiceTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private ThemaService themaService;
    @Autowired
    private ThemaRepository themaRepository;
    @Test
    void legtThemaMitAutomatischerKennungAn() {
        Thema thema = themaService.anlegen("Titel", "Cluster", "Beschreibung", "chance", "risiko", Pestel.OEKOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        assertTrue(thema.getKennung().startsWith("T-"));
        assertTrue(themaRepository.findByKennung(thema.getKennung()).isPresent());
    }
    @Test
    void aktualisiertBestehendesThema() {
        Thema thema = themaService.anlegen("Alter Titel", "Digitalisierung", "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaService.aktualisieren(thema.getId(), "Neuer Titel", "Digitalisierung", "Neue Beschreibung", "Neue Chancen", "Neue Risiken", Pestel.OEKONOMISCH);
        Thema geladen = themaRepository.findById(thema.getId()).orElseThrow();
        assertEquals("Neuer Titel", geladen.getTitel());
        assertEquals("Neue Beschreibung", geladen.getBeschreibung());
        assertEquals(Pestel.OEKONOMISCH, geladen.getPestel());
    }
    @Test
    void archiviertThema() {
        Thema thema = themaService.anlegen("Testthema", null, "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaService.archivieren(thema.getId());
        Thema geladen = themaRepository.findById(thema.getId()).orElseThrow();
        assertEquals(ThemaStatus.ARCHIVIERT, geladen.getStatus());
    }
    @Test
    void filtertThema() {
        Thema thema1 = themaService.anlegen("KI im Kundenservice", null, "a", "b", "c", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        Thema thema2 = themaService.anlegen("Robotik", null, "a", "b", "c", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.ARCHIVIERT);
        Thema thema3 = themaService.anlegen("KI-Regulierung", null, "a", "b", "c", Pestel.RECHTLICH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        List<Thema> ergebnis = themaService.filtern(Pestel.TECHNOLOGISCH, null, ThemaStatus.AKTIV, "KI");
        assertEquals(1, ergebnis.size());
        assertEquals("KI im Kundenservice", ergebnis.get(0).getTitel());
    }
}