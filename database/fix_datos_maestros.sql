-- ============================================================================
-- SCRIPT PARA AGREGAR DATOS MAESTROS FALTANTES
-- Sistema CASILDA - Solicitudes de Acompañamiento
-- ============================================================================

-- Agregar tipo de solicitud "Acompañamiento" si no existe
INSERT INTO tiposolicitud (id, nombre) 
SELECT 7, 'Acompañamiento'
WHERE NOT EXISTS (SELECT 1 FROM tiposolicitud WHERE nombre = 'Acompañamiento');

-- Agregar estado "PENDIENTE" si no existe
INSERT INTO estadosolicitud (id, nombre)
SELECT 3, 'PENDIENTE'
WHERE NOT EXISTS (SELECT 1 FROM estadosolicitud WHERE nombre = 'PENDIENTE');

-- Verificar datos insertados
SELECT 'TipoSolicitud' as tabla, id, nombre FROM tiposolicitud WHERE nombre = 'Acompañamiento'
UNION ALL
SELECT 'EstadoSolicitud' as tabla, id, nombre FROM estadosolicitud WHERE nombre = 'PENDIENTE';
