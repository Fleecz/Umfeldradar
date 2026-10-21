CREATE TABLE thema(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    kennung TEXT NOT NULL UNIQUE,
    titel TEXT NOT NULL,
    pestel TEXT NOT NULL,
    cluster TEXT,
    beschreibung TEXT NOT NULL,
    chancen TEXT NOT NULL,
    risiken TEXT NOT NULL,
    herkunft TEXT NOT NULL,
    status TEXT NOT NULL,
    version int NOT NULL
);
ALTER TABLE thema
    ADD CONSTRAINT pestel_value_check
        CHECK(pestel IN('POLITISCH', 'OEKONOMISCH','SOZIAL', 'TECHNOLOGISCH', 'OEKOLOGISCH', 'RECHTLICH')),
    ADD CONSTRAINT herkunft_value_check
        CHECK(herkunft IN('MANUELL', 'KI')),
    ADD CONSTRAINT status_value_check
        CHECK (status IN('AKTIV', 'ARCHIVIERT'));
CREATE TABLE quelle (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    thema_id UUID NOT NULL,
    url TEXT NOT NULL,
    titel TEXT NOT NULL,
    herausgeber TEXT NOT NULL,
    abrufdatum DATE NOT NULL,
    FOREIGN KEY (thema_id) REFERENCES thema(id)
);