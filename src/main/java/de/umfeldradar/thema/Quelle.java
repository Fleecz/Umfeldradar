package de.umfeldradar.thema;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "quelle")
public class Quelle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "thema_id", nullable = false)
    private Thema thema;
    private String url;
    private String titel;
    private String herausgeber;
    private LocalDate abrufdatum;
    protected Quelle() {}
    public Quelle(Thema thema, String url, String titel, String herausgeber,  LocalDate abrufdatum) {
        this.thema = thema;
        this.url = url;
        this.titel = titel;
        this.herausgeber = herausgeber;
        this.abrufdatum = abrufdatum;
    }
    public String getTitel() {
        return titel;
    }
    public String getHerausgeber() {
        return herausgeber;
    }
    public String getUrl() {
        return url;
    }
    public UUID getId() {
        return id;
    }
}