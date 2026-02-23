-- Script para agregar auto-incremento a las columnas ID de las tablas de maestros
-- Esto permite que los IDs se generen automáticamente al insertar nuevos registros

-- Crear secuencias para las tablas
CREATE SEQUENCE IF NOT EXISTS tiposolicitud_id_seq;
CREATE SEQUENCE IF NOT EXISTS campus_id_seq;
CREATE SEQUENCE IF NOT EXISTS dependencia_id_seq;
CREATE SEQUENCE IF NOT EXISTS facultadescuelainstituto_id_seq;
CREATE SEQUENCE IF NOT EXISTS tipoidentificacion_id_seq;

-- Actualizar las secuencias al valor actual máximo + 1
SELECT setval('tiposolicitud_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM tiposolicitud), false);
SELECT setval('campus_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM campus), false);
SELECT setval('dependencia_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM dependencia), false);
SELECT setval('facultadescuelainstituto_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM facultadescuelainstituto), false);
SELECT setval('tipoidentificacion_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM tipoidentificacion), false);

-- Alterar las columnas para usar las secuencias
ALTER TABLE tiposolicitud ALTER COLUMN id SET DEFAULT nextval('tiposolicitud_id_seq');
ALTER TABLE campus ALTER COLUMN id SET DEFAULT nextval('campus_id_seq');
ALTER TABLE dependencia ALTER COLUMN id SET DEFAULT nextval('dependencia_id_seq');
ALTER TABLE facultadescuelainstituto ALTER COLUMN id SET DEFAULT nextval('facultadescuelainstituto_id_seq');
ALTER TABLE tipoidentificacion ALTER COLUMN id SET DEFAULT nextval('tipoidentificacion_id_seq');

-- Asignar las secuencias a las columnas (para que se eliminen automáticamente si se elimina la tabla)
ALTER SEQUENCE tiposolicitud_id_seq OWNED BY tiposolicitud.id;
ALTER SEQUENCE campus_id_seq OWNED BY campus.id;
ALTER SEQUENCE dependencia_id_seq OWNED BY dependencia.id;
ALTER SEQUENCE facultadescuelainstituto_id_seq OWNED BY facultadescuelainstituto.id;
ALTER SEQUENCE tipoidentificacion_id_seq OWNED BY tipoidentificacion.id;
