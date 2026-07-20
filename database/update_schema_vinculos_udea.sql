-- Update vinculoudea catalog entries
INSERT INTO vinculoudea (id, nombre) VALUES
(1, 'Estudiante de Pregrado'),
(2, 'Estudiante de Posgrado'),
(3, 'Egresado Pregrado'),
(4, 'Egresado Posgrado'),
(5, 'Personal Administrativo'),
(6, 'Docente Vinculado'),
(7, 'Docente Ocasional'),
(8, 'Docente de Cátedra'),
(9, 'Contratista'),
(10, 'Otro tipo de vínculo'),
(11, 'Docente Cátedra 50'),
(12, 'Jubilado / Pensionado'),
(13, 'Prestador de Servicios'),
(14, 'Externo')
ON CONFLICT (id) DO UPDATE SET nombre = EXCLUDED.nombre;
