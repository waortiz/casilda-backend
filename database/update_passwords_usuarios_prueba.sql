-- ============================================================================
-- ACTUALIZACIÓN DE CONTRASEÑAS DE USUARIOS DE PRUEBA (BCrypt válido)
-- Sistema CASILDA - FNSP
-- ============================================================================
-- Contraseña en texto plano: Casilda2024!
-- Hash BCrypt válido: $2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru
-- ============================================================================

BEGIN;

UPDATE usuario
SET password = '$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru',
    fechaactualizacion = CURRENT_TIMESTAMP
WHERE email IN (
    'admin@udea.edu.co',
    'admin2@udea.edu.co',
    'coordinador.atencion@udea.edu.co',
    'coordinador.prevencion@udea.edu.co',
    'andrea.salazar@udea.edu.co',
    'carmen.sanchez@udea.edu.co',
    'diana.sanchez@udea.edu.co',
    'manuela.morales@udea.edu.co',
    'laura.valencia@udea.edu.co',
    'lina.rodas@udea.edu.co',
    'revisor1@udea.edu.co',
    'revisor2@udea.edu.co',
    'usuario.estudiante@udea.edu.co',
    'usuario.docente@udea.edu.co',
    'usuario.admin@udea.edu.co'
);

COMMIT;

-- Validación rápida sugerida:
-- SELECT email, LEFT(password, 4) AS prefijo_hash
-- FROM usuario
-- WHERE email IN (
--     'admin@udea.edu.co',
--     'admin2@udea.edu.co',
--     'coordinador.atencion@udea.edu.co',
--     'coordinador.prevencion@udea.edu.co',
--     'andrea.salazar@udea.edu.co',
--     'carmen.sanchez@udea.edu.co',
--     'diana.sanchez@udea.edu.co',
--     'manuela.morales@udea.edu.co',
--     'laura.valencia@udea.edu.co',
--     'lina.rodas@udea.edu.co',
--     'revisor1@udea.edu.co',
--     'revisor2@udea.edu.co',
--     'usuario.estudiante@udea.edu.co',
--     'usuario.docente@udea.edu.co',
--     'usuario.admin@udea.edu.co'
-- );
