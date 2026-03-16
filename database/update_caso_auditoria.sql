-- Agrega columnas de auditoria a la tabla caso
ALTER TABLE caso
    ADD COLUMN IF NOT EXISTS fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuariocreacion BIGINT,
    ADD COLUMN IF NOT EXISTS fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuarioactualizacion BIGINT;

-- Crea llaves foraneas de auditoria si no existen
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'caso_idusuariocreacion_fkey'
          AND table_name = 'caso'
    ) THEN
        ALTER TABLE caso
            ADD CONSTRAINT caso_idusuariocreacion_fkey
            FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'caso_idusuarioactualizacion_fkey'
          AND table_name = 'caso'
    ) THEN
        ALTER TABLE caso
            ADD CONSTRAINT caso_idusuarioactualizacion_fkey
            FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;
END
$$;