BEGIN;

-- 1) Modelo: actividad depende de accion
ALTER TABLE actividad
    ADD COLUMN IF NOT EXISTS idaccion INT;

-- 2) Datos maestros de accion
INSERT INTO accion (id, nombre) VALUES
(1, 'Seguimiento por psicología'),
(2, 'Seguimiento a activación de ruta interna acordada'),
(3, 'Seguimiento a remisión interna acordada'),
(4, 'Seguimiento a activación de ruta externa acordada'),
(5, 'Seguimiento a remision externa acordada')
ON CONFLICT (id) DO UPDATE SET nombre = EXCLUDED.nombre;

-- 3) Datos maestros de actividad, ahora relacionados por accion
INSERT INTO actividad (id, idaccion, nombre) VALUES
(1, 1, 'Seguimiento psicológico'),
(2, 2, 'Unidad de Asuntos Disciplinarios - UAD'),
(3, 2, 'Unidad de Resolución de Conflictos - URC'),
(4, 2, 'Seguridad a Personas y Bienes'),
(5, 2, 'Protocolo de Amenaza'),
(6, 2, 'Medidas de Protección Académicas'),
(7, 2, 'Medidas de Protección Laborales'),
(8, 3, 'Asesoría psicojurídica y representación - Convenio de Dirección de Bienestar Universitario'),
(9, 3, 'Línea Violeta te Orienta'),
(10, 3, 'Psiquiatría'),
(11, 3, 'Psicoterapia'),
(12, 3, 'Toxicología'),
(13, 3, 'Nutrición'),
(14, 3, 'Ginecología'),
(15, 3, 'Exámenes ITS'),
(16, 4, 'Ruta de salud'),
(17, 4, 'Fiscalía General de la Nación - FGN'),
(18, 4, 'Comisaría de Familia - CDF'),
(19, 4, 'Inspección de Policía'),
(20, 5, 'Línea 123 Agencia Mujer Medellín - Secretaría de las Mujeres del Distrito de Medellín'),
(21, 5, 'Línea 123 Mujer Metropolitana - Secretaría de las Mujeres del Departamento de Antioquia'),
(22, 5, 'Atención Psicojurídica en Territorio - Secretaría de las Mujeres del Distrito de Medellín'),
(23, 5, 'Defensa Técnica - Secretaría de las Mujeres del Distrito de Medellín'),
(24, 5, 'Defensoría del Pueblo'),
(25, 5, 'Módulo Diverso Línea 123 Social - Secretaría de Inclusión Social, Familia y Derechos Humanos del Distrito de Medellín'),
(26, 5, 'Gerencia de Diversidades Sexuales e Identidades de Género - Distrito de Medellín')
ON CONFLICT (id) DO UPDATE
SET idaccion = EXCLUDED.idaccion,
    nombre = EXCLUDED.nombre;

-- 4) Completar posibles filas antiguas sin accion para poder forzar integridad
UPDATE actividad
SET idaccion = 1
WHERE idaccion IS NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'actividad_idaccion_fkey'
    ) THEN
        ALTER TABLE actividad
            ADD CONSTRAINT actividad_idaccion_fkey
            FOREIGN KEY (idaccion) REFERENCES accion(id) ON DELETE NO ACTION;
    END IF;
END $$;

ALTER TABLE actividad
    ALTER COLUMN idaccion SET NOT NULL;

COMMIT;
