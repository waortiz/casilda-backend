-- ===========================================================================
-- SCRIPT DE MIGRACIÓN — Sistema CASILDA
-- Versión: v2 — Entidades core: caso, persona, solicitudatencion, atencion
-- Fecha: 2026-03-28
--
-- RESUMEN DE CAMBIOS:
--   caso             → Elimina FK idpersona; agrega FK idsolicitud (→ solicitudatencion)
--                      Elimina ididentidadgenero, iddependencia, idfacultad,
--                      idcampus, idvinculoudea, idsubvinculoudea
--   persona          → Elimina idetnia, idciudadresidencia, direccionresidencia
--   solicitudatencion→ Elimina idcaso; agrega ididentidadgenero, idsolicitante
--   atencion         → Elimina fecha, idsolicitudatencion
--                      Agrega idetnia, idciudadresidencia, direccionresidencia,
--                      idprograma, iddependencia, idfacultad, idcampus,
--                      idvinculoudea, idsubvinculoudea
--
-- REQUERIMIENTOS PREVIOS:
--   Ejecutar dentro de una transacción. Si falla algún paso, hacer ROLLBACK.
--   Verificar que la BD tenga los datos actualizados antes de ejecutar.
--
-- ADVERTENCIA:
--   La migración de datos asume que toda solicitudatencion.idcaso apunta
--   a un caso existente. Casos huérfanos (sin solicitudatencion asociada)
--   quedarán con idsolicitud = NULL y FALLARÁN en el paso 3.4.
--   Verificar con: SELECT id FROM caso WHERE id NOT IN (SELECT idcaso FROM solicitudatencion);
-- ===========================================================================

BEGIN;

-- ---------------------------------------------------------------------------
-- PASO 0: Eliminar casos huérfanos (sin solicitudatencion asociada)
--         y todos sus registros dependientes.
-- ---------------------------------------------------------------------------

DELETE FROM public.modalidadviolenciacaso
    WHERE idcaso NOT IN (SELECT idcaso FROM public.solicitudatencion WHERE idcaso IS NOT NULL);

DELETE FROM public.modalidadviolenciasexualcaso
    WHERE idcaso NOT IN (SELECT idcaso FROM public.solicitudatencion WHERE idcaso IS NOT NULL);

DELETE FROM public.programacaso
    WHERE idcaso NOT IN (SELECT idcaso FROM public.solicitudatencion WHERE idcaso IS NOT NULL);

DELETE FROM public.agresorvictima
    WHERE idcaso NOT IN (SELECT idcaso FROM public.solicitudatencion WHERE idcaso IS NOT NULL);

DELETE FROM public.caso
    WHERE id NOT IN (SELECT idcaso FROM public.solicitudatencion WHERE idcaso IS NOT NULL);

-- ---------------------------------------------------------------------------
-- PASO 1: Agregar nuevas columnas (sin restricciones todavía)
-- ---------------------------------------------------------------------------

-- 1.1  caso → idsolicitud (reemplaza la relación inversa en solicitudatencion.idcaso)
ALTER TABLE public.caso
    ADD COLUMN IF NOT EXISTS idsolicitud bigint;

-- 1.2  solicitudatencion → ididentidadgenero e idsolicitante
ALTER TABLE public.solicitudatencion
    ADD COLUMN IF NOT EXISTS ididentidadgenero integer,
    ADD COLUMN IF NOT EXISTS idsolicitante bigint;

-- 1.3  atencion → campos trasladados desde caso y persona
ALTER TABLE public.atencion
    ADD COLUMN IF NOT EXISTS idetnia              integer,
    ADD COLUMN IF NOT EXISTS idciudadresidencia   integer,
    ADD COLUMN IF NOT EXISTS direccionresidencia  character varying(500),
    ADD COLUMN IF NOT EXISTS idprograma           integer,
    ADD COLUMN IF NOT EXISTS iddependencia        integer,
    ADD COLUMN IF NOT EXISTS idfacultad           integer,
    ADD COLUMN IF NOT EXISTS idcampus             integer,
    ADD COLUMN IF NOT EXISTS idvinculoudea        integer,
    ADD COLUMN IF NOT EXISTS idsubvinculoudea     integer;


-- ---------------------------------------------------------------------------
-- PASO 2: Migración de datos
-- ---------------------------------------------------------------------------

-- 2.1  Poblar caso.idsolicitud desde solicitudatencion.idcaso
--      Cada solicitudatencion apuntaba a un caso exactamente.
UPDATE public.caso c
SET    idsolicitud = sa.id
FROM   public.solicitudatencion sa
WHERE  sa.idcaso = c.id;

-- 2.2  Trasladar ididentidadgenero de caso → solicitudatencion
UPDATE public.solicitudatencion sa
SET    ididentidadgenero = c.ididentidadgenero
FROM   public.caso c
WHERE  c.id = sa.idcaso
  AND  c.ididentidadgenero IS NOT NULL;

-- 2.2.1  Trasladar solicitante desde caso.idpersona → solicitudatencion.idsolicitante
UPDATE public.solicitudatencion sa
SET    idsolicitante = c.idpersona
FROM   public.caso c
WHERE  c.id = sa.idcaso
    AND  c.idpersona IS NOT NULL;

-- 2.3  Trasladar campos organizacionales de caso → atencion
--      Ruta: atencion.idsolicitudatencion → solicitudatencion.idcaso → caso
UPDATE public.atencion a
SET    iddependencia    = c.iddependencia,
       idfacultad       = c.idfacultad,
       idcampus         = c.idcampus,
       idvinculoudea    = c.idvinculoudea,
       idsubvinculoudea = c.idsubvinculoudea
FROM   public.solicitudatencion sa
JOIN   public.caso c ON c.id = sa.idcaso
WHERE  a.idsolicitudatencion = sa.id;

-- 2.4  Trasladar campos demográficos de persona → atencion
--      Ruta: atencion → solicitudatencion → caso → persona
UPDATE public.atencion a
SET    idetnia             = p.idetnia,
       idciudadresidencia  = p.idciudadresidencia,
       direccionresidencia = p.direccionresidencia
FROM   public.solicitudatencion sa
JOIN   public.caso c ON c.id = sa.idcaso
JOIN   public.persona p ON p.id = c.idpersona
WHERE  a.idsolicitudatencion = sa.id
  AND  (p.idetnia IS NOT NULL
     OR p.idciudadresidencia IS NOT NULL
     OR p.direccionresidencia IS NOT NULL);


-- ---------------------------------------------------------------------------
-- PASO 3: Aplicar NOT NULL y agregar nuevas FK constraints
-- ---------------------------------------------------------------------------

-- 3.1  caso.idsolicitud → NOT NULL (todas las filas deben tener valor tras migración)
--      Si falla, revisar la advertencia al inicio del script.
ALTER TABLE public.caso
    ALTER COLUMN idsolicitud SET NOT NULL;

-- 3.2  FK caso.idsolicitud → solicitudatencion
ALTER TABLE public.caso
    ADD CONSTRAINT caso_idsolicitud_fkey
        FOREIGN KEY (idsolicitud)
        REFERENCES public.solicitudatencion (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION;

-- 3.3  solicitudatencion.idsolicitante → NOT NULL
ALTER TABLE public.solicitudatencion
    ALTER COLUMN idsolicitante SET NOT NULL;

-- 3.4  FK solicitudatencion.ididentidadgenero → identidadgenero
ALTER TABLE public.solicitudatencion
    ADD CONSTRAINT solicitudatencion_ididentidadgenero_fkey
        FOREIGN KEY (ididentidadgenero)
        REFERENCES public.identidadgenero (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION;

-- 3.5  FK solicitudatencion.idsolicitante → persona
ALTER TABLE public.solicitudatencion
    ADD CONSTRAINT solicitudatencion_idsolicitante_fkey
        FOREIGN KEY (idsolicitante)
        REFERENCES public.persona (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION;

-- 3.6  FKs para los nuevos campos de atencion
ALTER TABLE public.atencion
    ADD CONSTRAINT atencion_idetnia_fkey
        FOREIGN KEY (idetnia)
        REFERENCES public.etnia (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    ADD CONSTRAINT atencion_idciudadresidencia_fkey
        FOREIGN KEY (idciudadresidencia)
        REFERENCES public.municipio (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    ADD CONSTRAINT atencion_idprograma_fkey
        FOREIGN KEY (idprograma)
        REFERENCES public.programa (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    ADD CONSTRAINT atencion_iddependencia_fkey
        FOREIGN KEY (iddependencia)
        REFERENCES public.dependencia (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    ADD CONSTRAINT atencion_idfacultad_fkey
        FOREIGN KEY (idfacultad)
        REFERENCES public.facultadescuelainstituto (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    ADD CONSTRAINT atencion_idcampus_fkey
        FOREIGN KEY (idcampus)
        REFERENCES public.campus (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    ADD CONSTRAINT atencion_idvinculoudea_fkey
        FOREIGN KEY (idvinculoudea)
        REFERENCES public.vinculoudea (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    ADD CONSTRAINT atencion_idsubvinculoudea_fkey
        FOREIGN KEY (idsubvinculoudea)
        REFERENCES public.subvinculoudea (id)
        ON UPDATE NO ACTION ON DELETE NO ACTION;


-- ---------------------------------------------------------------------------
-- PASO 4: Eliminar FK constraints obsoletos
-- ---------------------------------------------------------------------------

-- 4.1  caso: FKs de columnas que se eliminan
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS fkpd2kjxmqx2jw3m6t95mb6fdg2;       -- idpersona → persona
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_ididentidadgenero_fkey;          -- ididentidadgenero → identidadgenero
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_iddependencia_fkey;              -- iddependencia → dependencia
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idfacultad_fkey;                 -- idfacultad → facultadescuelainstituto
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idcampus_fkey;                   -- idcampus → campus
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idvinculoudea_fkey;              -- idvinculoudea → vinculoudea
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idsubvinculoudea_fkey;           -- idsubvinculoudea → subvinculoudea

-- 4.2  solicitudatencion: FK de idcaso
ALTER TABLE public.solicitudatencion DROP CONSTRAINT IF EXISTS solicitudatencion_idcaso_fkey;

-- 4.3  persona: FKs de columnas que se eliminan
ALTER TABLE public.persona DROP CONSTRAINT IF EXISTS persona_idetnia_fkey;
ALTER TABLE public.persona DROP CONSTRAINT IF EXISTS persona_idciudadresidencia_fkey;

-- 4.4  atencion: FK incorrecta (apuntaba a solicitudatencion en lugar de cita)
--       y FK de idsolicitudatencion
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idcita_fkey;              -- FK incorrecta (apuntaba a solicitudatencion)
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS fk5xkqo32kqxwppjjb50dcn6cul;      -- idsolicitudatencion → solicitudatencion


-- ---------------------------------------------------------------------------
-- PASO 5: Eliminar columnas obsoletas
-- ---------------------------------------------------------------------------

-- 5.1  caso: columnas trasladadas a atencion y solicitudatencion
ALTER TABLE public.caso
    DROP COLUMN IF EXISTS idpersona,
    DROP COLUMN IF EXISTS ididentidadgenero,
    DROP COLUMN IF EXISTS iddependencia,
    DROP COLUMN IF EXISTS idfacultad,
    DROP COLUMN IF EXISTS idcampus,
    DROP COLUMN IF EXISTS idvinculoudea,
    DROP COLUMN IF EXISTS idsubvinculoudea;

-- 5.2  solicitudatencion: idcaso (relación ahora invertida en caso.idsolicitud)
ALTER TABLE public.solicitudatencion
    DROP COLUMN IF EXISTS idcaso;

-- 5.3  persona: campos trasladados a atencion
ALTER TABLE public.persona
    DROP COLUMN IF EXISTS idetnia,
    DROP COLUMN IF EXISTS idciudadresidencia,
    DROP COLUMN IF EXISTS direccionresidencia;

-- 5.4  atencion: fecha (eliminada del modelo) e idsolicitudatencion (relación ahora a través de Cita)
ALTER TABLE public.atencion
    DROP COLUMN IF EXISTS fecha,
    DROP COLUMN IF EXISTS idsolicitudatencion;


COMMIT;

-- ===========================================================================
-- VERIFICACIÓN POST-MIGRACIÓN (ejecutar manualmente para confirmar)
-- ===========================================================================
--
-- Estructura final esperada:
--
-- caso:
--   SELECT column_name FROM information_schema.columns
--   WHERE table_name = 'caso' ORDER BY ordinal_position;
--   → debe incluir idsolicitud; NO debe incluir idpersona, ididentidadgenero,
--     iddependencia, idfacultad, idcampus, idvinculoudea, idsubvinculoudea
--
-- persona:
--   → NO debe incluir idetnia, idciudadresidencia, direccionresidencia
--
-- solicitudatencion:
--   → debe incluir ididentidadgenero, idsolicitante; NO debe incluir idcaso
--
-- atencion:
--   → debe incluir idetnia, idciudadresidencia, direccionresidencia, idprograma,
--     iddependencia, idfacultad, idcampus, idvinculoudea, idsubvinculoudea
--   → NO debe incluir fecha, idsolicitudatencion
--
-- Integridad referencial:
--   SELECT COUNT(*) FROM caso WHERE idsolicitud IS NULL;  -- debe ser 0
--   SELECT COUNT(*) FROM solicitudatencion WHERE idsolicitante IS NULL;  -- debe ser 0
-- ===========================================================================
