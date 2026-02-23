-- Script para crear la tabla solicitud_acompanamiento
-- Ejecutar en la base de datos de Casilda

CREATE TABLE IF NOT EXISTS solicitud_acompanamiento (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    tipo_solicitud VARCHAR(100) NOT NULL,
    tipo_reporte VARCHAR(20) NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Datos del solicitante
    sol_primer_nombre VARCHAR(100) NOT NULL,
    sol_segundo_nombre VARCHAR(100),
    sol_primer_apellido VARCHAR(100) NOT NULL,
    sol_segundo_apellido VARCHAR(100),
    sol_campus VARCHAR(150) NOT NULL,
    sol_dependencia VARCHAR(150) NOT NULL,
    sol_facultad VARCHAR(150) NOT NULL,
    sol_tipo_documento VARCHAR(50) NOT NULL,
    sol_numero_documento VARCHAR(20) NOT NULL,
    sol_identidad_genero VARCHAR(50) NOT NULL,
    sol_edad INTEGER NOT NULL,
    sol_celular VARCHAR(10) NOT NULL,
    sol_celular_alterno VARCHAR(10) NOT NULL,
    sol_correo_institucional VARCHAR(150) NOT NULL,
    sol_correo_personal VARCHAR(150) NOT NULL,
    
    -- Datos del remitente (opcionales para solicitudes indirectas)
    rem_primer_nombre VARCHAR(100),
    rem_segundo_nombre VARCHAR(100),
    rem_primer_apellido VARCHAR(100),
    rem_segundo_apellido VARCHAR(100),
    rem_cargo VARCHAR(150)
);

-- Crear índices para mejorar las consultas
CREATE INDEX IF NOT EXISTS idx_solicitud_codigo ON solicitud_acompanamiento(codigo);
CREATE INDEX IF NOT EXISTS idx_solicitud_fecha ON solicitud_acompanamiento(fecha_creacion);
CREATE INDEX IF NOT EXISTS idx_solicitud_estado ON solicitud_acompanamiento(estado);
CREATE INDEX IF NOT EXISTS idx_solicitud_documento ON solicitud_acompanamiento(sol_numero_documento);

-- Comentarios para documentación
COMMENT ON TABLE solicitud_acompanamiento IS 'Almacena las solicitudes de acompañamiento del sistema Casilda';
COMMENT ON COLUMN solicitud_acompanamiento.codigo IS 'Código único de la solicitud en formato ACO-YYYY-NNNN';
COMMENT ON COLUMN solicitud_acompanamiento.tipo_reporte IS 'Tipo de solicitud: directa o indirecta';
