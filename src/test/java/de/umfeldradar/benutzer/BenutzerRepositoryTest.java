package de.umfeldradar.benutzer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BenutzerRepositoryTest {
    @Autowired
    private BenutzerRepository benutzerRepository;
    @Test
    void saveAndLoadUser(){
        Benutzer benutzer = new Benutzer(true, "test@example.de", "hash", Role.EXPERTE, "Testnutzer");
        benutzerRepository.save(benutzer);
        benutzer = benutzerRepository.findById(benutzer.getId()).orElse(null);
        Assertions.assertNotNull(benutzer);
    }
  @Test
  void findByEmail(){
      Benutzer benutzer = new Benutzer(true, "test@example.de", "hash", Role.EXPERTE, "Testnutzer");
      benutzerRepository.save(benutzer);
      Optional<Benutzer> gefunden = benutzerRepository.findByEmail("test@example.de");
      gefunden.isPresent();
  }
}