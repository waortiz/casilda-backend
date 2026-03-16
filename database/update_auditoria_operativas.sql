-- Agrega columnas de auditoria a contactotelefonico, asignacion y atencion
ALTER TABLE contactotelefonico
    ADD COLUMN IF NOT EXISTS fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuariocreacion BIGINT,
    ADD COLUMN IF NOT EXISTS fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuarioactualizacion BIGINT;

ALTER TABLE asignacion
    ADD COLUMN IF NOT EXISTS fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuariocreacion BIGINT,
    ADD COLUMN IF NOT EXISTS fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuarioactualizacion BIGINT;

ALTER TABLE asignacion
    DROP COLUMN IF EXISTS fecha;

ALTER TABLE contactotelefonico
    DROP COLUMN IF EXISTS fecha;

ALTER TABLE atencion
    ADD COLUMN IF NOT EXISTS fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuariocreacion BIGINT,
    ADD COLUMN IF NOT EXISTS fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuarioactualizacion BIGINT;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'contactotelefonico_idusuariocreacion_fkey'
          AND table_name = 'contactotelefonico'
    ) THEN
        ALTER TABLE contactotelefonico
            ADD CONSTRAINT contactotelefonico_idusuariocreacion_fkey
            FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'contactotelefonico_idusuarioactualizacion_fkey'
          AND table_name = 'contactotelefonico'
    ) THEN
        ALTER TABLE contactotelefonico
            ADD CONSTRAINT contactotelefonico_idusuarioactualizacion_fkey
            FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'asignacion_idusuariocreacion_fkey'
          AND table_name = 'asignacion'
    ) THEN
        ALTER TABLE asignacion
            ADD CONSTRAINT asignacion_idusuariocreacion_fkey
            FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'asignacion_idusuarioactualizacion_fkey'
          AND table_name = 'asignacion'
    ) THEN
        ALTER TABLE asignacion
            ADD CONSTRAINT asignacion_idusuarioactualizacion_fkey
            FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'atencion_idusuariocreacion_fkey'
          AND table_name = 'atencion'
    ) THEN
        ALTER TABLE atencion
            ADD CONSTRAINT atencion_idusuariocreacion_fkey
            FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'atencion_idusuarioactualizacion_fkey'
          AND table_name = 'atencion'
    ) THEN
        ALTER TABLE atencion
            ADD CONSTRAINT atencion_idusuarioactualizacion_fkey
            FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;
END
$$;