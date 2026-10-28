package de.umfeldradar.bewertung;

import de.umfeldradar.thema.Thema;
import de.umfeldradar.thema.ThemaRepository;
import de.umfeldradar.thema.ThemaStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BewertungsrundeService {
    private final BewertungsrundeRepository repository;
    private final ThemaRepository themaRepository;
    private final RundenThemaRepository rundenThemaRepository;
    public BewertungsrundeService(BewertungsrundeRepository repository, ThemaRepository themaRepository, RundenThemaRepository rundenThemaRepository) {
        this.repository = repository;
        this.themaRepository = themaRepository;
        this.rundenThemaRepository = rundenThemaRepository;
    }
    public Bewertungsrunde createRound(String name) {
        Bewertungsrunde runde = new Bewertungsrunde(name, BewertungsrundeStatus.ENTWURF);
        return repository.save(runde);
    }
    @Transactional
    public void openRound(UUID id, List<UUID> themaIds) {
        if (themaIds == null || themaIds.isEmpty()) {
            throw new IllegalArgumentException("At least one topic must be selected");
        }
        if (themaIds.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Topic IDs must not be null");
        }
        var eindeutigeIds = new HashSet<>(themaIds);
        if (eindeutigeIds.size() != themaIds.size()) {
            throw new IllegalArgumentException("Topic IDs must be unique");
        }
        Bewertungsrunde runde = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Round not found"));
        runde.open();
        List<Thema> themen = themaRepository.findAllById(eindeutigeIds);
        if (themen.size() != eindeutigeIds.size()) {
            throw new IllegalArgumentException("One or more topics were not found");
        }
        List<RundenThema> snapshots = new ArrayList<>();
        for (Thema thema : themen) {
            if (thema.getStatus() == ThemaStatus.ARCHIVIERT) {
                throw new IllegalArgumentException("Archived topics cannot be selected");
            }
            snapshots.add(new RundenThema(runde, thema));
        }
        rundenThemaRepository.saveAll(snapshots);
    }
    @Transactional
    public void closeRound(UUID id) {
        Bewertungsrunde runde = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Round not found"));
        runde.close();
    }
}