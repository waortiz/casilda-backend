CREATE TABLE IF NOT EXISTS instanciaremision (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    idtiporemision integer NOT NULL,
    CONSTRAINT instanciaremision_pkey PRIMARY KEY (id),
    CONSTRAINT fk_instanciaremision_tiporemision FOREIGN KEY (idtiporemision) REFERENCES tiporemision(id) ON DELETE NO ACTION
);

INSERT INTO instanciaremision (id, nombre, idtiporemision) VALUES
(1, 'Seguridad a personas y bienes', 1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO instanciaremision (id, nombre, idtiporemision) VALUES
(2, 'Policía', 2),
(3, 'Fiscalía General de la Nación', 2),
(4, 'IPS-Salud', 2),
(5, 'EPS-Salud', 2)
ON CONFLICT (id) DO NOTHING;
