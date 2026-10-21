CREATE TABLE einstellung (
    schluessel TEXT PRIMARY KEY,
    wert INTEGER NOT NULL
);
INSERT INTO einstellung (schluessel, wert)
VALUES ('MAX_QUELLEN', 5);