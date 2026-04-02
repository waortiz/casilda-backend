-- Elimina la columna hora de contactotelefonico si existe.
ALTER TABLE contactotelefonico
DROP COLUMN IF EXISTS hora;
