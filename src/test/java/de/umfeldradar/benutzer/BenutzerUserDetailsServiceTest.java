package de.umfeldradar.benutzer;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Transactional
@SpringBootTest
public class BenutzerUserDetailsServiceTest {
    @Autowired
    BenutzerRepository repository;
    @Autowired
    BenutzerUserDetailsService detailService;
    @Test
    public void agentUnableToVerifyViaLogin(){
        Benutzer benutzerAgent = new Benutzer(true, "test@example.de", "hash", Role.AGENT, "Testnutzer");
        repository.save(benutzerAgent);
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> detailService.loadUserByUsername("test@example.de")
        );
    }
}
