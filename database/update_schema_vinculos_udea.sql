-- Update vinculoudea catalog entries
INSERT INTO vinculoudea (id, nombre) VALUES
(1, 'Estudiante de Pregrado'),
(2, 'Personal Administrativo'),
(3, 'Docente Vinculado'),
(4, 'Egresado'),
(5, 'Docente Ocasional'),
(6, 'Docente de Cátedra'),
(7, 'Contratista'),
(8, 'Otro tipo de vínculo'),
(9, 'Estudiante de Tecnología'),
(10, 'Estudiante de Posgrado'),
(11, 'Docente Cátedra 50'),
(12, 'Jubilado / Pensionado'),
(13, 'Prestador de Servicios'),
(14, 'Externo')
ON CONFLICT (id) DO UPDATE SET nombre = EXCLUDED.nombre;
