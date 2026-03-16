-- Agrega columnas de auditoria a remision, solicitudatencion y cita
ALTER TABLE remision
    ADD COLUMN IF NOT EXISTS fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuariocreacion BIGINT,
    ADD COLUMN IF NOT EXISTS fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuarioactualizacion BIGINT;

ALTER TABLE remision
    DROP COLUMN IF EXISTS fecha;

ALTER TABLE solicitudatencion
    ADD COLUMN IF NOT EXISTS fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuariocreacion BIGINT,
    ADD COLUMN IF NOT EXISTS fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuarioactualizacion BIGINT;

ALTER TABLE solicitudatencion
    DROP COLUMN IF EXISTS fecha;

ALTER TABLE cita
    ADD COLUMN IF NOT EXISTS fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuariocreacion BIGINT,
    ADD COLUMN IF NOT EXISTS fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS idusuarioactualizacion BIGINT;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'remision_idusuariocreacion_fkey'
          AND table_name = 'remision'
    ) THEN
        ALTER TABLE remision
            ADD CONSTRAINT remision_idusuariocreacion_fkey
            FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'remision_idusuarioactualizacion_fkey'
          AND table_name = 'remision'
    ) THEN
        ALTER TABLE remision
            ADD CONSTRAINT remision_idusuarioactualizacion_fkey
            FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'solicitudatencion_idusuariocreacion_fkey'
          AND table_name = 'solicitudatencion'
    ) THEN
        ALTER TABLE solicitudatencion
            ADD CONSTRAINT solicitudatencion_idusuariocreacion_fkey
            FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'solicitudatencion_idusuarioactualizacion_fkey'
          AND table_name = 'solicitudatencion'
    ) THEN
        ALTER TABLE solicitudatencion
            ADD CONSTRAINT solicitudatencion_idusuarioactualizacion_fkey
            FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'cita_idusuariocreacion_fkey'
          AND table_name = 'cita'
    ) THEN
        ALTER TABLE cita
            ADD CONSTRAINT cita_idusuariocreacion_fkey
            FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'cita_idusuarioactualizacion_fkey'
          AND table_name = 'cita'
    ) THEN
        ALTER TABLE cita
            ADD CONSTRAINT cita_idusuarioactualizacion_fkey
            FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;
    END IF;
END
$$;