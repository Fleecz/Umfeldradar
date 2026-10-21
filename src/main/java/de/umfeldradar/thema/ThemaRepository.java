package de.umfeldradar.thema;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ThemaRepository extends JpaRepository<Thema, UUID>, JpaSpecificationExecutor<Thema> {
    Optional<Thema> findByKennung(String kennung);
    @Query(value = "SELECT nextval('thema_kennung_seq')", nativeQuery = true)
    long nextKennungSequence();
    List<Thema> findByPestel(Pestel pestel);
}