package de.umfeldradar.thema;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class QuelleServiceTest {
    @Autowired
    private QuelleService quelleService;
    @Autowired
    private ThemaRepository themaRepository;
    @Autowired
    private EinstellungRepository einstellungRepository;
    @Autowired
    private QuelleRepository quelleRepository;

    @Test
    public void legtQuelleMitRichtigerURLAn(){
        Thema thema = new Thema("T-TEST-001", "KI in der Produktion", "Digitalisierung", "Beschreibung des Themas", "Chancen des Themas", "Risiken des Themas", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaRepository.save(thema);
        Quelle quelle = quelleService.anlegen(thema, "https://example.com", "Beispielquelle","Beispiel-Vortrag", LocalDate.now());
        assertNotNull(quelle);
        Quelle gespeichert = quelleRepository.findById(quelle.getId()).orElseThrow();
        assertEquals("https://example.com", gespeichert.getUrl());
    }

    @Test
    public void lehntHttpUrlAb(){
        Thema thema = new Thema("T-TEST-001", "KI in der Produktion", "Digitalisierung", "Beschreibung des Themas", "Chancen des Themas", "Risiken des Themas", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaRepository.save(thema);
        assertThrows(IllegalArgumentException.class, ()->{
            quelleService.anlegen(thema, "http://example.com", "Beispielquelle", "Beispiel-Verlag", LocalDate.now());
        });
    }

    @Test
    public void maximal5Quellen(){
        Thema thema = new Thema("T-TEST-001", "KI in der Produktion", "Digitalisierung", "Beschreibung des Themas", "Chancen des Themas", "Risiken des Themas", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaRepository.save(thema);
        for(int i=0;i<5;i++){
            quelleService.anlegen(thema, "https://example.com/"+i,"Quelle"+i,"Verlag",LocalDate.now());
        }
        assertThrows(IllegalArgumentException.class, ()->{
            quelleService.anlegen(thema, "https://exampl.com/6", "Quelle 6", "Verlag", LocalDate.now());
        });
    }

    @Test
    public void geaendertesQuellenlimitWirdBeachtet() {
        Einstellung einstellung = einstellungRepository
                .findById("MAX_QUELLEN")
                .orElseThrow();
        einstellung.wertAendern(2);
        Thema thema = new Thema("T-TEST-002", "KI in der Logistik", "Digitalisierung", "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaRepository.save(thema);
        quelleService.anlegen(thema, "https://example.com/1", "Quelle 1", "Verlag", LocalDate.now());
        quelleService.anlegen(thema, "https://example.com/2", "Quelle 2", "Verlag", LocalDate.now());
        assertThrows(IllegalArgumentException.class, () ->
                quelleService.anlegen(thema, "https://example.com/3", "Quelle 3", "Verlag", LocalDate.now()));
    }

    @Test
    void lehntHttpsOhneHostAb() {
        Thema thema = new Thema("T-TEST-003", "Testthema", null, "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaRepository.save(thema);
        assertThrows(IllegalArgumentException.class, () ->
                quelleService.anlegen(thema, "https://", "Quelle", "Verlag", LocalDate.now()));
    }
}