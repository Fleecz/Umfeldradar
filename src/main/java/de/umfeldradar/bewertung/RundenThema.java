package de.umfeldradar.bewertung;

import de.umfeldradar.thema.Pestel;
import de.umfeldradar.thema.Thema;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "runde_thema")
public class RundenThema {
    @EmbeddedId
    private RundenThemaId id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("rundeId")
    @JoinColumn(name = "runde_id")
    private Bewertungsrunde runde;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("themaId")
    @JoinColumn(name = "thema_id")
    private Thema thema;
    @Column(name = "titel_snapshot", columnDefinition = "text", updatable = false)
    private String titelSnapshot;
    @Enumerated(EnumType.STRING)
    @Column(name = "pestel_snapshot", columnDefinition = "text", updatable = false)
    private Pestel pestelSnapshot;
    @Column(name = "beschreibung_snapshot", columnDefinition = "text", updatable = false)
    private String beschreibungSnapshot;
    @Column(name = "chancen_snapshot", columnDefinition = "text", updatable = false)
    private String chancenSnapshot;
    @Column(name = "risiken_snapshot", columnDefinition = "text", updatable = false)
    private String risikenSnapshot;
    protected RundenThema() {}
    public RundenThema(Bewertungsrunde runde, Thema thema) {
        this.runde = Objects.requireNonNull(runde, "Round is required");
        this.thema = Objects.requireNonNull(thema, "Topic is required");
        this.id = new RundenThemaId(
                Objects.requireNonNull(runde.getId(), "Round must be persisted"),
                Objects.requireNonNull(thema.getId(), "Topic must be persisted")
        );
        this.titelSnapshot = thema.getTitel();
        this.pestelSnapshot = thema.getPestel();
        this.beschreibungSnapshot = thema.getBeschreibung();
        this.chancenSnapshot = thema.getChancen();
        this.risikenSnapshot = thema.getRisiken();
    }
    public RundenThemaId getId() {
        return id;
    }
    public Bewertungsrunde getRunde() {
        return runde;
    }
    public Thema getThema() {
        return thema;
    }
    public String getTitelSnapshot() {
        return titelSnapshot;
    }
    public Pestel getPestelSnapshot() {
        return pestelSnapshot;
    }
    public String getBeschreibungSnapshot() {
        return beschreibungSnapshot;
    }
    public String getChancenSnapshot() {
        return chancenSnapshot;
    }
    public String getRisikenSnapshot() {
        return risikenSnapshot;
    }
}