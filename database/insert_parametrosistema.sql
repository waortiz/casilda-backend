-- Script: Creación e inicialización de la tabla parametrosistema
-- Parámetros configurables del sistema CASILDA

-- Crear tabla si no existe
CREATE TABLE IF NOT EXISTS parametrosistema (
    id serial NOT NULL,
    clave character varying NOT NULL,
    valor character varying NOT NULL,
    CONSTRAINT parametrosistema_pkey PRIMARY KEY (id),
    CONSTRAINT parametrosistema_clave_unique UNIQUE (clave)
);

-- Insertar parámetro de máximo de llamadas de contacto telefónico
-- Valor por defecto: 2 (segunda llamada activa asignación unilateral)
INSERT INTO parametrosistema (clave, valor)
VALUES ('MAX_LLAMADAS_CONTACTO', '2')
ON CONFLICT (clave) DO NOTHING;
