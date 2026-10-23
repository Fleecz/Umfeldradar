CREATE TABLE bewertungsrunde(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status TEXT NOT NULL,
    name TEXT NOT NULL,
    CONSTRAINT bewertungsrunde_status_check
    CHECK (status IN ('ENTWURF', 'OFFEN', 'GESCHLOSSEN'))
);
CREATE TABLE runde_thema (
    runde_id UUID NOT NULL,
    thema_id UUID NOT NULL,
    PRIMARY KEY (runde_id, thema_id),
    FOREIGN KEY (runde_id) REFERENCES bewertungsrunde(id),
    FOREIGN KEY (thema_id) REFERENCES thema(id)
);