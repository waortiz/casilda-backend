-- Script to create actorremitente table and insert default values
CREATE TABLE actorremitente (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT actorremitente_pkey PRIMARY KEY (id)
);

INSERT INTO actorremitente (id, nombre) VALUES
    (1, 'Masculinidades'),
    (2, 'Bienestar Universitario'),
    (3, 'Otros')
ON CONFLICT (id) DO NOTHING;
