package de.umfeldradar.bewertung;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BewertungsrundeRepository extends JpaRepository<Bewertungsrunde, UUID> {

}
