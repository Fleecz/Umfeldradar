package de.umfeldradar.thema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;

@SpringBootTest
class ThemaOptimisticLockingTest {
    @Autowired
    private ThemaRepository themaRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate tx;
    @BeforeEach
    void setUp() {
        tx = new TransactionTemplate(transactionManager);
    }
    @Test
    void erkenntGleichzeitigeAenderung() {
        Thema gespeichert = tx.execute(status ->
                themaRepository.save(new Thema("T-LOCK-001", "Original", null, "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH, Herkunft.MANUELL, ThemaStatus.AKTIV)));
        UUID id = gespeichert.getId();
        Thema adminA = tx.execute(status ->
                themaRepository.findById(id).orElseThrow());
        Thema adminB = tx.execute(status ->
                themaRepository.findById(id).orElseThrow());
        adminA.aktualisieren("Änderung A", null, "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH);
        adminB.aktualisieren("Änderung B", null, "Beschreibung", "Chancen", "Risiken", Pestel.TECHNOLOGISCH);
        tx.executeWithoutResult(status ->
                themaRepository.saveAndFlush(adminA));
        assertThrows(
                ObjectOptimisticLockingFailureException.class,
                () -> tx.executeWithoutResult(status ->
                        themaRepository.saveAndFlush(adminB)));
    }
    @AfterEach
    void aufraeumen() {
        tx.executeWithoutResult(status ->
                themaRepository.findByKennung("T-LOCK-001")
                        .ifPresent(themaRepository::delete));
    }
}