package de.umfeldradar.bewertung;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.Column;

@Embeddable
public class RundenThemaId implements Serializable {
    @Column(name = "runde_id")
    private UUID rundeId;
    @Column(name = "thema_id")
    private UUID themaId;
    protected RundenThemaId() {
    }
    public RundenThemaId(UUID rundeId, UUID themaId) {
        this.rundeId = rundeId;
        this.themaId = themaId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RundenThemaId that)) return false;
        return Objects.equals(rundeId, that.rundeId)
                && Objects.equals(themaId, that.themaId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(rundeId, themaId);
    }
}