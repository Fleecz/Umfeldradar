package de.umfeldradar.bewertung;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class Bewertungsrunde {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private BewertungsrundeStatus status;
    public UUID getId() {
        return id;
    }
    protected Bewertungsrunde() {
    }
    public Bewertungsrunde(String name, BewertungsrundeStatus status) {
        this.name = name;
        this.status = status;
    }
    public String getName() {
        return name;
    }
    public BewertungsrundeStatus getStatus() {
        return status;
    }
    public void open() {
        if(status != BewertungsrundeStatus.ENTWURF){
            throw new IllegalStateException("Nur Entwürfe können geöffnet werden");
        }
        this.status = BewertungsrundeStatus.OFFEN;
    }
    public void close() {
        if(status != BewertungsrundeStatus.OFFEN){
            throw new IllegalStateException("Nur offene Bewertungsrunden können geschlossen werden");
        }
        this.status = BewertungsrundeStatus.GESCHLOSSEN;
    }
}