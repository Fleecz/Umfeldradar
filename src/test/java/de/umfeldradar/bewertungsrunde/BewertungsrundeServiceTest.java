package de.umfeldradar.bewertungsrunde;

import de.umfeldradar.AbstractPostgresIntegrationTest;
import de.umfeldradar.bewertung.Bewertungsrunde;
import de.umfeldradar.bewertung.BewertungsrundeRepository;
import de.umfeldradar.bewertung.BewertungsrundeService;
import de.umfeldradar.bewertung.BewertungsrundeStatus;
import de.umfeldradar.thema.Herkunft;
import de.umfeldradar.thema.Pestel;
import de.umfeldradar.thema.Thema;
import de.umfeldradar.thema.ThemaRepository;
import de.umfeldradar.thema.ThemaStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Transactional
public class BewertungsrundeServiceTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private BewertungsrundeService service;
    @Autowired
    private BewertungsrundeRepository repository;
    @Autowired
    private ThemaRepository themaRepository;
    @Autowired
    private EntityManager entityManager;
    @Test
    public void openDraft() {
        Thema thema = erstelleTestThema();
        Bewertungsrunde runde = service.createRound("Testrunde");
        var rundeId = runde.getId();
        service.openRound(rundeId, List.of(thema.getId()));
        entityManager.flush();
        entityManager.clear();
        Bewertungsrunde gespeichert = repository.findById(rundeId)
                .orElseThrow();
        assertEquals(BewertungsrundeStatus.OFFEN, gespeichert.getStatus());
    }

    @Test
    public void rejectOpeningAlreadyOpenRound() {
        Thema thema = erstelleTestThema();
        Bewertungsrunde runde = service.createRound("Testrunde");
        List<java.util.UUID> themaIds = List.of(thema.getId());
        service.openRound(runde.getId(), themaIds);
        assertThrows(IllegalStateException.class, () -> service.openRound(runde.getId(), themaIds));
    }
    @Test
    public void closeOpenRound() {
        Thema thema = erstelleTestThema();
        Bewertungsrunde runde = service.createRound("Testrunde");
        var rundeId = runde.getId();
        service.openRound(rundeId, List.of(thema.getId()));
        service.closeRound(rundeId);
        entityManager.flush();
        entityManager.clear();
        Bewertungsrunde gespeichert = repository.findById(rundeId)
                .orElseThrow();
        assertEquals(BewertungsrundeStatus.GESCHLOSSEN, gespeichert.getStatus());
    }
    @Test
    public void rejectClosingDraft() {
        Bewertungsrunde runde = service.createRound("Testrunde");
        assertThrows(IllegalStateException.class, () -> service.closeRound(runde.getId()));
    }
    @Test
    public void rejectClosingAlreadyClosedRound() {
        Thema thema = erstelleTestThema();
        Bewertungsrunde runde = service.createRound("Testrunde");
        service.openRound(runde.getId(), List.of(thema.getId()));
        service.closeRound(runde.getId());
        assertThrows(IllegalStateException.class, () -> service.closeRound(runde.getId()));
    }
    private Thema erstelleTestThema() {
        Thema thema = new Thema("T-9001", "Quantencomputing", "Digitalisierung", "Neue Verfahren zur Verarbeitung von Informationen.", "Schnellere Berechnung bestimmter Probleme.", "Risiken für bestehende Verschlüsselungsverfahren.", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        return themaRepository.save(thema);
    }
}