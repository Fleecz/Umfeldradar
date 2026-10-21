package de.umfeldradar.thema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuelleRepository extends JpaRepository<Quelle, UUID> {
    List<Quelle> findByThema(Thema thema);
    long countByThema(Thema thema);
}
