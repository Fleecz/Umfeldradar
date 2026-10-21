package de.umfeldradar.thema;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

@Service
public class ThemaService {
    private final ThemaRepository themaRepository;
    public ThemaService(ThemaRepository themaRepository) {
        this.themaRepository = themaRepository;
    }
    public Thema anlegen(String titel, String cluster, String beschreibung, String chancen, String risiken, Pestel pestel, Herkunft herkunft, ThemaStatus status) {
        long nummer = themaRepository.nextKennungSequence();
        String kennung = String.format("T-%04d", nummer);
        Thema thema = new Thema(kennung, titel, cluster, beschreibung, chancen, risiken, pestel, herkunft, status);
        return themaRepository.save(thema);
    }
    @Transactional
    public void aktualisieren(UUID id, String titel, String cluster, String beschreibung, String chancen, String risiken, Pestel pestel) {
        Thema thema = themaRepository.findById(id).orElseThrow();
        thema.aktualisieren(titel, cluster, beschreibung, chancen, risiken, pestel);
    }
    @Transactional
    public void archivieren(UUID id){
        Thema thema = themaRepository.findById(id).orElseThrow();
        thema.archivieren();
    }
    public List<Thema> filtern(Pestel pestel, Herkunft herkunft, ThemaStatus status, String suchtext) {
        Specification<Thema> spec = Specification.unrestricted();
        if (pestel != null) {
            spec = spec.and(ThemaSpecifications.hatPestel(pestel));
        }
        if (herkunft != null) {
            spec = spec.and(ThemaSpecifications.hatHerkunft(herkunft));
        }
        if (status != null) {
            spec = spec.and(ThemaSpecifications.hatStatus(status));
        } else{
            spec = spec.and(ThemaSpecifications.hatStatus(ThemaStatus.AKTIV));
        }
        if (suchtext != null && !suchtext.isEmpty()) {
            spec = spec.and(ThemaSpecifications.titelEnthaelt(suchtext));
        }
        return themaRepository.findAll(spec);
    }
}