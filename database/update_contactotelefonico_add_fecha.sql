-- Agrega la columna fecha en contactotelefonico si no existe.
ALTER TABLE contactotelefonico
ADD COLUMN IF NOT EXISTS fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
