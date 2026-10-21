package de.umfeldradar.thema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuelleRepositoryTest {
    @Autowired
    private QuelleRepository quelleRepository;
    @Autowired
    private ThemaRepository themaRepository;
    @Test
    public void saveQuelle() {
        Thema thema = new Thema("T-0002", "KI in der Produktion", "Digitalisierung", "Beschreibung des Themas", "Chancen des Themas", "Risiken des Themas", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV);
        themaRepository.save(thema);
        Quelle quelle = new Quelle(thema, "https://example.com", "Beispielquelle", "Beispiel-Verlag", LocalDate.now());
        quelleRepository.save(quelle);
        List<Quelle> gefunden = quelleRepository.findByThema(thema);
        Assertions.assertEquals(1, gefunden.size());
    }
}
