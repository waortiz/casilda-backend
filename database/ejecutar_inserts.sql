-- ============================================================================
-- SCRIPT MAESTRO DE INSERCIÓN DE DATOS
-- Sistema CASILDA - FNSP
-- ============================================================================
-- Este script ejecuta todos los scripts de inserción en el orden correcto
-- Ejecutar después de crear la estructura de la base de datos (er_model_schema.sql)
-- ============================================================================

\echo '============================================================================'
\echo 'Iniciando inserción de datos maestros...'
\echo '============================================================================'

-- Desactivar temporalmente las restricciones de clave foránea para facilitar la inserción
SET session_replication_role = 'replica';

\echo 'Insertando datos maestros básicos...'
\i insert_datos_maestros.sql

\echo 'Insertando programas académicos y países...'
\i insert_programas_paises.sql

\echo 'Insertando usuarios de prueba...'
\i insert_usuarios_prueba.sql

-- Reactivar las restricciones de clave foránea
SET session_replication_role = 'origin';

\echo '============================================================================'
\echo 'Inserción completada exitosamente!'
\echo '============================================================================'
\echo 'Resumen de datos insertados:'
\echo '- Tipos de correo: 2'
\echo '- Sexos: 4'
\echo '- Tipos de identificación: 7'
\echo '- Etnias: 6'
\echo '- Identidades de género: 8'
\echo '- Orientaciones sexuales: 7'
\echo '- Departamentos: 33'
\echo '- Municipios de Antioquia: 124'
\echo '- Tipos de discapacidad: 14'
\echo '- Tipos de teléfono: 4'
\echo '- Campus: 14'
\echo '- Dependencias: 22'
\echo '- Facultades/Escuelas/Institutos: 22'
\echo '- Vínculos agresor-víctima: 13'
\echo '- Vínculos con UdeA: 17'
\echo '- Modalidades de violencia: 44'
\echo '- Modalidades de violencia sexual: 19'
\echo '- Tipos de solicitud: 6'
\echo '- Cargos: 9'
\echo '- Jornadas: 3'
\echo '- Resultados contacto telefónico: 6'
\echo '- Régimenes de salud: 4'
\echo '- EPS: 16'
\echo '- Roles de usuario: 5'
\echo '- Programas académicos: 124'
\echo '- Países: 240'
\echo '- Usuarios de prueba: 13'
\echo '============================================================================'
\echo 'NOTA: La contraseña por defecto para todos los usuarios es: Casilda2024!'
\echo '============================================================================'
