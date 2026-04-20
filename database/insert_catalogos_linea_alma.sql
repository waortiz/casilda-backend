-- =============================================================================
-- Script: insert_catalogos_linea_alma.sql
-- Descripción: Inserta los datos de catálogos requeridos por el módulo Línea ALMA
--              (tablas creadas en la sección APH del er_model_schema.sql).
-- Ejecutar DESPUÉS de haber aplicado el DDL de Línea ALMA.
-- =============================================================================

INSERT INTO tiporeportealma (id, nombre) VALUES
    (1, 'Directa'),
    (2, 'Por remisión')
ON CONFLICT (id) DO NOTHING;

INSERT INTO canalcontacto (id, nombre) VALUES
    (1, 'WhatsApp'),
    (2, 'Llamada'),
    (3, 'Remisión')
ON CONFLICT (id) DO NOTHING;

INSERT INTO formaentrevista (id, nombre) VALUES
    (1, 'Presencial'),
    (2, 'Virtual'),
    (3, 'Telefónica')
ON CONFLICT (id) DO NOTHING;

INSERT INTO canalaph (id, nombre) VALUES
    (1, 'Línea 106'),
    (2, 'Consulta externa'),
    (3, 'Urgencias'),
    (4, 'Otro')
ON CONFLICT (id) DO NOTHING;

INSERT INTO convenioaph (id, nombre) VALUES
    (1, 'UdeA'),
    (2, 'Colsanitas'),
    (3, 'Otro')
ON CONFLICT (id) DO NOTHING;

INSERT INTO ambitoaph (id, nombre) VALUES
    (1, 'Universitario'),
    (2, 'Comunitario'),
    (3, 'Hospitalario')
ON CONFLICT (id) DO NOTHING;

INSERT INTO protocoloaph (id, nombre) VALUES
    (1, 'Protocolo estándar'),
    (2, 'Protocolo crisis'),
    (3, 'Protocolo remisión')
ON CONFLICT (id) DO NOTHING;

INSERT INTO resultadotriage (id, nombre) VALUES
    (1, 'Alta'),
    (2, 'Media'),
    (3, 'Baja')
ON CONFLICT (id) DO NOTHING;

-- Tipo de servicio requerido por el flujo de Atención PR (Línea ALMA)
INSERT INTO tiposervicio (id, nombre) VALUES
    (5, 'Atención APH')
ON CONFLICT (id) DO NOTHING;
