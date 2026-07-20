-- Script de migración para actualizar las tablas caso y atencion de acuerdo al nuevo esquema

-- 1. Crear tabla estadocaso si no existe
CREATE TABLE IF NOT EXISTS public.estadocaso
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT estadocaso_pkey PRIMARY KEY (id)
);

-- Inserción de valores maestros para estadocaso
INSERT INTO public.estadocaso (id, nombre) VALUES
(1, 'Abierto activo'),
(2, 'Abierto en transición'),
(3, 'Cerrado')
ON CONFLICT (id) DO NOTHING;

-- 2. Asegurar que las columnas existan en la tabla caso y agregar idcita
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idregimen integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS ideps integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idetnia integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idciudadresidencia integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS direccionresidencia character varying;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idprograma integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idunidadacademica integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idunidadadministrativa integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idcampus integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idvinculoudea integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS otrovinculo character varying;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idciudadhechos integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idestadocaso integer;
ALTER TABLE public.caso ADD COLUMN IF NOT EXISTS idcita bigint;

-- Eliminar columnas antiguas de caso
ALTER TABLE public.caso DROP COLUMN IF EXISTS idsolicitud;
ALTER TABLE public.caso DROP COLUMN IF EXISTS idsolicitudatencion;

-- 3. Crear llaves foráneas en caso si no existen
ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idregimen_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idregimen_fkey FOREIGN KEY (idregimen) REFERENCES public.regimen(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_ideps_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_ideps_fkey FOREIGN KEY (ideps) REFERENCES public.eps(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idetnia_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idetnia_fkey FOREIGN KEY (idetnia) REFERENCES public.etnia(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idciudadresidencia_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idciudadresidencia_fkey FOREIGN KEY (idciudadresidencia) REFERENCES public.municipio(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idprograma_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idprograma_fkey FOREIGN KEY (idprograma) REFERENCES public.programa(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idunidadacademica_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idunidadacademica_fkey FOREIGN KEY (idunidadacademica) REFERENCES public.unidadacademica(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idunidadadministrativa_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idunidadadministrativa_fkey FOREIGN KEY (idunidadadministrativa) REFERENCES public.unidadadministrativa(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idcampus_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idcampus_fkey FOREIGN KEY (idcampus) REFERENCES public.campus(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idvinculoudea_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idvinculoudea_fkey FOREIGN KEY (idvinculoudea) REFERENCES public.vinculoudea(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idciudadhechos_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idciudadhechos_fkey FOREIGN KEY (idciudadhechos) REFERENCES public.municipio(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idestadocaso_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idestadocaso_fkey FOREIGN KEY (idestadocaso) REFERENCES public.estadocaso(id) ON DELETE NO ACTION;

ALTER TABLE public.caso DROP CONSTRAINT IF EXISTS caso_idcita_fkey;
ALTER TABLE public.caso ADD CONSTRAINT caso_idcita_fkey FOREIGN KEY (idcita) REFERENCES public.cita(id) ON DELETE NO ACTION;

-- 4. Eliminar restricciones de llaves foráneas antiguas de la tabla atencion
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idregimen_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_ideps_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idetnia_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idciudadresidencia_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idprograma_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idfacultad_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_iddependencia_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idcampus_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idvinculoudea_fkey;
ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS fk4ohct7musqgi8oxj7yer5ylt0;

-- 5. Eliminar columnas antiguas de la tabla atencion
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idregimen;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS ideps;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idetnia;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idciudadresidencia;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS direccionresidencia;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idprograma;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idunidadacademica;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idunidadadministrativa;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idcampus;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idvinculoudea;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS otrovinculo;
ALTER TABLE public.atencion DROP COLUMN IF EXISTS idcita;

-- 6. Agregar columna idcaso a la tabla atencion
ALTER TABLE public.atencion ADD COLUMN IF NOT EXISTS idcaso bigint;

ALTER TABLE public.atencion DROP CONSTRAINT IF EXISTS atencion_idcaso_fkey;
ALTER TABLE public.atencion ADD CONSTRAINT atencion_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES public.caso(id) ON DELETE NO ACTION;

-- 7. Actualizar valor por defecto de idestadoatencion en atencion
ALTER TABLE public.atencion ALTER COLUMN idestadoatencion SET DEFAULT 1;
