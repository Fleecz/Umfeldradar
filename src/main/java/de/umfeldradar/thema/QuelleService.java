package de.umfeldradar.thema;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Service
public class QuelleService {
    private final ThemaService themaService;
    private final QuelleRepository quelleRepository;
    private final EinstellungRepository einstellungRepository;
    public QuelleService(QuelleRepository quelleRepository, EinstellungRepository einstellungRepository, ThemaService themaService) {
        this.quelleRepository = quelleRepository;
        this.einstellungRepository = einstellungRepository;
        this.themaService = themaService;
    }
    public Quelle anlegen(Thema thema, String url, String titel, String herausgeber, LocalDate abrufdatum) {
        Einstellung einstellung = einstellungRepository
                .findById("MAX_QUELLEN")
                .orElseThrow();
        long quellenAnzahl = quelleRepository.countByThema(thema);
        if (quellenAnzahl >= einstellung.getWert()) {
            throw new IllegalArgumentException("Maximale Anzahl an Quellen erreicht");
        }
        if (url == null) {
            throw new IllegalArgumentException("URL darf nicht null sein");
        }
        URI uri = URI.create(url);
        if (!"https".equalsIgnoreCase(uri.getScheme()) || uri.getHost() == null) {
            throw new IllegalArgumentException("Quelle benötigt eine gültige HTTPS-URL");
        }
        Quelle quelle = new Quelle(thema, url, titel, herausgeber, abrufdatum);
        return quelleRepository.save(quelle);
    }
    public List<Quelle> findByTopic(Thema thema){
        return quelleRepository.findByThema(thema);
    }
}