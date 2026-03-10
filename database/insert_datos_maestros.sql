-- ============================================================================
-- SCRIPT DE INSERCIÓN DE DATOS MAESTROS (CATÁLOGOS)
-- Sistema CASILDA - FNSP
-- Generado a partir de EV-I-02-25 D1.xlsm
-- ============================================================================

-- ============================================================================
-- 1. TIPOS DE CORREO
-- ============================================================================
INSERT INTO tipocorreo (id, nombre) VALUES
(1, 'Correo Institucional'),
(2, 'Correo Personal');

-- ============================================================================
-- 2. SEXO (Sexo biológico)
-- ============================================================================
INSERT INTO sexo (id, codigo, nombre) VALUES
(1, 'M', 'Mujer'),
(2, 'H', 'Hombre'),
(3, 'NB', 'No binario'),
(4, 'PR', 'Prefiere no responder');

-- ============================================================================
-- 3. TIPOS DE IDENTIFICACIÓN
-- ============================================================================
INSERT INTO tipoidentificacion (id, codigo, nombre) VALUES
(1, 'TI', 'Tarjeta de Identidad'),
(2, 'CC', 'Cédula de Ciudadanía'),
(3, 'CE', 'Cédula de Extranjería'),
(4, 'PA', 'Pasaporte'),
(5, 'PPT', 'PPT'),
(6, 'NUIP', 'NUIP'),
(7, 'OT', 'Otro');

-- ============================================================================
-- 4. ETNIAS
-- ============================================================================
INSERT INTO etnia (id, nombre) VALUES
(1, 'Afrodescendiente'),
(2, 'Indígena'),
(3, 'Palenquero-a'),
(4, 'Gitano-a (Rrom)'),
(5, 'Raizal'),
(6, 'Ningún grupo étnico');

-- ============================================================================
-- 5. IDENTIDADES DE GÉNERO
-- ============================================================================
INSERT INTO identidadgenero (id, nombre) VALUES
(1, 'Mujer cisgénero'),
(2, 'Hombre cisgénero'),
(3, 'Mujer trans'),
(4, 'Hombre trans'),
(5, 'No binaria'),
(6, 'Prefiere no responder'),
(7, 'Otra'),
(8, 'SD');

-- ============================================================================
-- 6. ORIENTACIÓN SEXUAL
-- ============================================================================
INSERT INTO orientacionsexual (id, nombre) VALUES
(1, 'Asexual'),
(2, 'Bisexual'),
(3, 'Heterosexual'),
(4, 'Homosexual'),
(5, 'Lesbiana'),
(6, 'Prefiere no responder'),
(7, 'Otra');

-- ============================================================================
-- 7. DEPARTAMENTOS (Principales de Colombia)
-- ============================================================================
INSERT INTO departamento (id, codigo, nombre) VALUES
(1, '05', 'Antioquia'),
(2, '08', 'Atlántico'),
(3, '11', 'Bogotá D.C.'),
(4, '13', 'Bolívar'),
(5, '15', 'Boyacá'),
(6, '17', 'Caldas'),
(7, '18', 'Caquetá'),
(8, '19', 'Cauca'),
(9, '20', 'Cesar'),
(10, '23', 'Córdoba'),
(11, '25', 'Cundinamarca'),
(12, '27', 'Chocó'),
(13, '41', 'Huila'),
(14, '44', 'La Guajira'),
(15, '47', 'Magdalena'),
(16, '50', 'Meta'),
(17, '52', 'Nariño'),
(18, '54', 'Norte de Santander'),
(19, '63', 'Quindío'),
(20, '66', 'Risaralda'),
(21, '68', 'Santander'),
(22, '70', 'Sucre'),
(23, '73', 'Tolima'),
(24, '76', 'Valle del Cauca'),
(25, '81', 'Arauca'),
(26, '85', 'Casanare'),
(27, '86', 'Putumayo'),
(28, '88', 'San Andrés'),
(29, '91', 'Amazonas'),
(30, '94', 'Guainía'),
(31, '95', 'Guaviare'),
(32, '97', 'Vaupés'),
(33, '99', 'Vichada');

-- ============================================================================
-- 8. MUNICIPIOS DE ANTIOQUIA (Principales)
-- ============================================================================
INSERT INTO municipio (id, codigo, nombre, iddepartamento) VALUES
(1, '05001', 'Medellín', 1),
(2, '05002', 'Abejorral', 1),
(3, '05004', 'Abriaquí', 1),
(4, '05021', 'Alejandría', 1),
(5, '05030', 'Amagá', 1),
(6, '05031', 'Amalfi', 1),
(7, '05034', 'Andes', 1),
(8, '05036', 'Angelópolis', 1),
(9, '05038', 'Angostura', 1),
(10, '05040', 'Anorí', 1),
(11, '05044', 'Santafé de Antioquia', 1),
(12, '05045', 'Anza', 1),
(13, '05051', 'Apartadó', 1),
(14, '05055', 'Arboletes', 1),
(15, '05059', 'Argelia', 1),
(16, '05079', 'Barbosa', 1),
(17, '05086', 'Belmira', 1),
(18, '05088', 'Bello', 1),
(19, '05091', 'Betania', 1),
(20, '05093', 'Betulia', 1),
(21, '05101', 'Ciudad Bolívar', 1),
(22, '05107', 'Briceño', 1),
(23, '05113', 'Buriticá', 1),
(24, '05120', 'Cáceres', 1),
(25, '05125', 'Caicedo', 1),
(26, '05129', 'Caldas', 1),
(27, '05134', 'Campamento', 1),
(28, '05138', 'Cañasgordas', 1),
(29, '05142', 'Caracolí', 1),
(30, '05145', 'Caramanta', 1),
(31, '05147', 'Carepa', 1),
(32, '05148', 'El Carmen de Viboral', 1),
(33, '05150', 'Carolina', 1),
(34, '05154', 'Caucasia', 1),
(35, '05172', 'Chigorodó', 1),
(36, '05190', 'Cisneros', 1),
(37, '05197', 'Cocorná', 1),
(38, '05206', 'Concepción', 1),
(39, '05209', 'Concordia', 1),
(40, '05212', 'Copacabana', 1),
(41, '05234', 'Dabeiba', 1),
(42, '05237', 'Donmatías', 1),
(43, '05240', 'Ebéjico', 1),
(44, '05250', 'El Bagre', 1),
(45, '05264', 'Entrerríos', 1),
(46, '05266', 'Envigado', 1),
(47, '05282', 'Fredonia', 1),
(48, '05284', 'Frontino', 1),
(49, '05306', 'Giraldo', 1),
(50, '05308', 'Girardota', 1),
(51, '05310', 'Gómez Plata', 1),
(52, '05315', 'Granada', 1),
(53, '05318', 'Guadalupe', 1),
(54, '05321', 'Guarne', 1),
(55, '05347', 'Guatapé', 1),
(56, '05353', 'Heliconia', 1),
(57, '05360', 'Hispania', 1),
(58, '05361', 'Itagüí', 1),
(59, '05364', 'Ituango', 1),
(60, '05368', 'Jardín', 1),
(61, '05376', 'Jericó', 1),
(62, '05380', 'La Ceja', 1),
(63, '05390', 'La Estrella', 1),
(64, '05400', 'La Pintada', 1),
(65, '05411', 'La Unión', 1),
(66, '05425', 'Liborina', 1),
(67, '05440', 'Maceo', 1),
(68, '05467', 'Marinilla', 1),
(69, '05475', 'Montebello', 1),
(70, '05480', 'Murindó', 1),
(71, '05483', 'Mutatá', 1),
(72, '05490', 'Nariño', 1),
(73, '05495', 'Necoclí', 1),
(74, '05501', 'Nechí', 1),
(75, '05541', 'Olaya', 1),
(76, '05543', 'Peñol', 1),
(77, '05576', 'Peque', 1),
(78, '05579', 'Pueblorrico', 1),
(79, '05585', 'Puerto Berrío', 1),
(80, '05591', 'Puerto Nare', 1),
(81, '05604', 'Puerto Triunfo', 1),
(82, '05607', 'Remedios', 1),
(83, '05615', 'Retiro', 1),
(84, '05628', 'Rionegro', 1),
(85, '05631', 'Sabanalarga', 1),
(86, '05642', 'Sabaneta', 1),
(87, '05647', 'Salgar', 1),
(88, '05649', 'San Andrés de Cuerquia', 1),
(89, '05652', 'San Carlos', 1),
(90, '05656', 'San Francisco', 1),
(91, '05658', 'San Jerónimo', 1),
(92, '05659', 'San José de la Montaña', 1),
(93, '05660', 'San Juan de Urabá', 1),
(94, '05664', 'San Luis', 1),
(95, '05665', 'San Pedro', 1),
(96, '05667', 'San Pedro de Uraba', 1),
(97, '05670', 'San Rafael', 1),
(98, '05674', 'San Roque', 1),
(99, '05679', 'San Vicente', 1),
(100, '05686', 'Santa Bárbara', 1),
(101, '05690', 'Santa Rosa de Osos', 1),
(102, '05697', 'Santo Domingo', 1),
(103, '05736', 'El Santuario', 1),
(104, '05756', 'Segovia', 1),
(105, '05761', 'Sonsón', 1),
(106, '05789', 'Sopetrán', 1),
(107, '05790', 'Támesis', 1),
(108, '05792', 'Tarazá', 1),
(109, '05809', 'Tarso', 1),
(110, '05819', 'Titiribí', 1),
(111, '05837', 'Toledo', 1),
(112, '05842', 'Turbo', 1),
(113, '05847', 'Uramita', 1),
(114, '05854', 'Urrao', 1),
(115, '05856', 'Valdivia', 1),
(116, '05858', 'Valparaíso', 1),
(117, '05861', 'Vegachí', 1),
(118, '05873', 'Venecia', 1),
(119, '05885', 'Vigía del Fuerte', 1),
(120, '05887', 'Yalí', 1),
(121, '05890', 'Yarumal', 1),
(122, '05893', 'Yolombó', 1),
(123, '05895', 'Yondó', 1),
(124, '05898', 'Zaragoza', 1);

-- ============================================================================
-- 9. TIPOS DE DISCAPACIDAD
-- ============================================================================
INSERT INTO discapacidad (id, nombre) VALUES
(1, 'Persona ciega'),
(2, 'Persona con baja visión'),
(3, 'Persona sordo-oralizado'),
(4, 'Persona con hipoacusia'),
(5, 'Persona con compromiso en miembros superiores'),
(6, 'Persona con compromiso en miembros inferiores'),
(7, 'Persona con compromiso en miembros superiores e inferiores'),
(8, 'Discapacidad intelectual'),
(9, 'Discapacidad psicosocial'),
(10, 'Discapacidad múltiple'),
(11, 'Talla baja'),
(12, 'Sordoceguera'),
(13, 'Persona sordoseñante'),
(14, 'Ninguna');

-- ============================================================================
-- 10. TIPOS DE TELÉFONO
-- ============================================================================
INSERT INTO tipotelefono (id, nombre) VALUES
(1, 'Celular'),
(2, 'Teléfono Fijo'),
(3, 'WhatsApp'),
(4, 'Oficina');

-- ============================================================================
-- 11. CAMPUS
-- ============================================================================
INSERT INTO campus (id, nombre) VALUES
(1, 'Campus Apartadó'),
(2, 'Campus Carepa'),
(3, 'Campus Turbo'),
(4, 'Campus Caucasia'),
(5, 'Campus Santa Fe de Antioquia'),
(6, 'Campus Yarumal'),
(7, 'Campus Amalfi'),
(8, 'Campus Segovia'),
(9, 'Campus Andes'),
(10, 'Campus La Pintada'),
(11, 'Campus El Carmen de Viboral'),
(12, 'Campus Sonsón'),
(13, 'Campus Puerto Berrío'),
(14, 'Campus Medellín');

-- ============================================================================
-- 12. DEPENDENCIAS
-- ============================================================================
INSERT INTO dependencia (id, nombre) VALUES
(1, 'Dirección de Bienestar Universitario'),
(2, 'Dirección de Comunicaciones'),
(3, 'Dirección de Planeación y Desarrollo Institucional'),
(4, 'Dirección de Posgrado'),
(5, 'Dirección de Regionalización'),
(6, 'Dirección de Relaciones Internacionales'),
(7, 'Dirección Jurídica'),
(8, 'Rectoría'),
(9, 'Secretaría General'),
(10, 'Vicerrectoría Administrativa'),
(11, 'Vicerrectoría de Docencia'),
(12, 'Vicerrectoría de Extensión'),
(13, 'Vicerrectoría de Investigación'),
(14, 'Vicerrectoría General'),
(15, 'Publicaciones'),
(16, 'División de Gestión Documental'),
(17, 'Admisiones y Registro'),
(18, 'Sistema de Bibliotecas'),
(19, 'Escuela de Gobierno'),
(20, 'Oficina de Auditoría Institucional'),
(21, 'Talento Humano'),
(22, 'Seguridad a Personas y Bienes');

-- ============================================================================
-- 13. FACULTADES, ESCUELAS E INSTITUTOS
-- ============================================================================
INSERT INTO facultadescuelainstituto (id, nombre) VALUES
(1, 'Escuela de Microbiología'),
(2, 'Escuela de Nutrición y Dietética'),
(3, 'Escuela de Idiomas'),
(4, 'Escuela Interamericana de Bibliotecología'),
(5, 'Facultad de Artes'),
(6, 'Facultad de Ciencias Agrarias'),
(7, 'Facultad de Ciencias Económicas'),
(8, 'Facultad de Ciencias Exactas y Naturales'),
(9, 'Facultad de Ciencias Farmacéuticas y Alimentarias'),
(10, 'Facultad de Ciencias Sociales y Humanas'),
(11, 'Facultad de Comunicaciones'),
(12, 'Facultad de Derecho y Ciencias Políticas'),
(13, 'Facultad de Educación'),
(14, 'Facultad de Enfermería'),
(15, 'Facultad de Ingeniería'),
(16, 'Facultad de Medicina'),
(17, 'Facultad de Odontología'),
(18, 'Facultad Nacional de Salud Pública'),
(19, 'Instituto de Estudios Políticos'),
(20, 'Instituto de Estudios Regionales'),
(21, 'Instituto de Filosofía'),
(22, 'Instituto Universitario de Educación Física y Deportes');

-- ============================================================================
-- 14. VÍNCULO AGRESOR CON VÍCTIMA
-- ============================================================================
INSERT INTO vinculoagresorvictima (id, nombre) VALUES
(1, 'Pareja/expareja'),
(2, 'Familiar'),
(3, 'Compañeros de estudio'),
(4, 'Compañeros de trabajo'),
(5, 'Docente'),
(6, 'Estudiante'),
(7, 'Jefe'),
(8, 'Comparten lugar de trabajo'),
(9, 'Comparten lugar de estudio'),
(10, 'Amigo/amiga'),
(11, 'Conocido'),
(12, 'Desconocido'),
(13, 'Otro');

-- ============================================================================
-- 15. VÍNCULO CON LA UDEA
-- ============================================================================
INSERT INTO vinculoudea (id, nombre) VALUES
(1, 'Estudiante de pregrado'),
(2, 'Estudiante de posgrado'),
(3, 'Estudiante de tecnología'),
(4, 'Estudiante de técnica'),
(5, 'Docente vinculado'),
(6, 'Docente ocasional'),
(7, 'Docente cátedra'),
(8, 'Docente cátedra 50'),
(9, 'Personal no docente'),
(10, 'Prestador de servicios'),
(11, 'Otro tipo de vínculo: Egresado'),
(12, 'Otro tipo de vínculo: Jubilado'),
(13, 'Otro tipo de vínculo: Pensionado'),
(14, 'Otro tipo de vínculo: Contratista'),
(15, 'Externo'),
(16, 'Otro'),
(17, 'N/A');

-- ============================================================================
-- 16. MODALIDADES DE VIOLENCIA
-- ============================================================================
INSERT INTO modalidadviolencia (id, nombre) VALUES
(1, 'Acción que degrade a la otra persona'),
(2, 'Omisión que degrade a la otra persona'),
(3, 'Acción que busque controlar los comportamientos, creencias o decisiones de otra persona'),
(4, 'Omisión que busque controlar los comportamientos, creencias o decisiones de otra persona'),
(5, 'Intimidación'),
(6, 'Manipulación'),
(7, 'Amenaza directa'),
(8, 'Amenaza indirecta'),
(9, 'Humillación'),
(10, 'Aislamiento'),
(11, 'Constreñimiento ilegal'),
(12, 'Lenguaje misógino, sexista o discursos de odio'),
(13, 'Injuria'),
(14, 'Calumnia'),
(15, 'Injuria por vía de hecho'),
(16, 'Abuso de poder y/o confianza'),
(17, 'Violencia epistémica'),
(18, 'Violencia psicológica facilitada por las nuevas tecnologías: Acceso, uso, control, manipulación, intercambio o publicación no autorizada de información privada y datos personales'),
(19, 'Violencia psicológica facilitada por las nuevas tecnologías: Suplantación y robo de identidad'),
(20, 'Violencia psicológica facilitada por las nuevas tecnologías: Actos que implican la vigilancia y el monitoreo de una persona'),
(21, 'Violencia psicológica facilitada por las nuevas tecnologías: Ciberhostigamiento o ciberacecho'),
(22, 'Violencia psicológica facilitada por las nuevas tecnologías: Ciberacoso'),
(23, 'Violencia psicológica facilitada por las nuevas tecnologías: Ciberbullying'),
(24, 'Violencia psicológica facilitada por las nuevas tecnologías: Slutshaming'),
(25, 'Violencia psicológica facilitada por las nuevas tecnologías: Ataques a grupos, organizaciones o comunidades de mujeres'),
(26, 'Otra conducta que implique un perjuicio a la salud psicológica, autodeterminación o al desarrollo personal'),
(27, 'Agresión física'),
(28, 'Feminicidio tentado o consumado'),
(29, 'Restricción a la libertad física'),
(30, 'Violencia física facilitada por las nuevas tecnologías'),
(31, 'Pérdida, transformación, sustracción, destrucción, retención o distracción: de objetos o bienes de la persona'),
(32, 'Pérdida, transformación, sustracción, destrucción, retención o distracción: de instrumentos de trabajo de la persona'),
(33, 'Pérdida, transformación, sustracción, destrucción, retención o distracción: de documentos personales'),
(34, 'Pérdida, transformación, sustracción, destrucción, retención o distracción: de valores o derechos económicos destinados a satisfacer las necesidades de la persona'),
(35, 'Extorsión'),
(36, 'Estafa'),
(37, 'Inasistencia alimentaria'),
(38, 'Control económico'),
(39, 'Violencia económica facilitada por las nuevas tecnologías'),
(40, 'Discriminación por género u orientación sexual o identidad de género'),
(41, 'Hostigamiento'),
(42, 'Omisión del deber de denuncia'),
(43, 'Omisión al deber de debida diligencia'),
(44, 'Revictimización');

-- ============================================================================
-- 17. MODALIDADES DE VIOLENCIA SEXUAL
-- ============================================================================
INSERT INTO modalidadviolenciasexual (id, nombre) VALUES
(1, 'Acceso carnal violento'),
(2, 'Acto sexual violento'),
(3, 'Explotación sexual: Inducción a la prostitución'),
(4, 'Explotación sexual: Proxenetismo con menor de edad'),
(5, 'Explotación sexual: Constreñimiento a la prostitución'),
(6, 'Explotación sexual: Trata de personas'),
(7, 'Explotación sexual: Estímulo a la prostitución de menores'),
(8, 'Explotación sexual: Demanda de explotación sexual comercial de persona menor de 18 años de edad'),
(9, 'Explotación sexual: Pornografía con personas menores de 18 años'),
(10, 'Acoso sexual'),
(11, 'Stealthing'),
(12, 'Obligar a otra persona a realizar cualquier acto o interacción sexual con tercera persona'),
(13, 'Violencia sexual correctiva'),
(14, 'Violencia sexual facilitada por las nuevas tecnologías: Creación, difusión, distribución o intercambio digital de fotografías, videos o audioclips de naturaleza sexual o íntima sin consentimiento'),
(15, 'Violencia sexual facilitada por las nuevas tecnologías: Amenazas directas de daño o violencia – sextorsion'),
(16, 'Violencia sexual facilitada por las nuevas tecnologías: Grooming'),
(17, 'Violencia sexual facilitada por las nuevas tecnologías: Sexting sin consentimiento'),
(18, 'Violencia sexual facilitada por las nuevas tecnologías: Abuso, explotación y/o trata de mujeres y niñas por medio de las tecnologías'),
(19, 'Otra');

-- ============================================================================
-- 18. TIPOS DE SOLICITUD
-- ============================================================================
INSERT INTO tiposolicitud (id, nombre) VALUES
(1, 'Directa'),
(2, 'Indirecta');

-- ============================================================================
-- 19. CARGOS
-- ============================================================================
INSERT INTO cargo (id, nombre) VALUES
(1, 'Psicóloga'),
(2, 'Abogada'),
(3, 'Trabajadora Social'),
(4, 'Psicoorientadora'),
(5, 'Coordinadora de Prevención'),
(6, 'Coordinadora de Atención'),
(7, 'Docente'),
(8, 'Administrativo'),
(9, 'Otro');

-- ============================================================================
-- 21. RESULTADO CONTACTO TELEFÓNICO
-- ============================================================================
INSERT INTO resultadocontactotelefonico (id, nombre) VALUES
(1, 'Contesta y se concerta cita'),
(2, 'Contesta y no se concerta cita'),
(3, 'Contesta y desiste'),
(4, 'No contesta'),
(5, 'Número errado'),
(6, 'N/A');

-- ============================================================================
-- 22. RÉGIMEN DE SALUD
-- ============================================================================
INSERT INTO regimen (id, nombre) VALUES
(1, 'Contributivo'),
(2, 'Subsidiado'),
(3, 'Especial'),
(4, 'No afiliado');

-- ============================================================================
-- 23. EPS (Entidades Promotoras de Salud)
-- ============================================================================
INSERT INTO eps (id, nombre) VALUES
(1, 'Sura EPS'),
(2, 'Salud Total'),
(3, 'Sanitas'),
(4, 'Compensar'),
(5, 'Nueva EPS'),
(6, 'Coomeva EPS'),
(7, 'Famisanar'),
(8, 'Cafesalud'),
(9, 'SOS EPS'),
(10, 'Medimás'),
(11, 'Capital Salud'),
(12, 'Aliansalud'),
(13, 'EPS SAVIA SALUD'),
(14, 'Mutual SER'),
(15, 'Otra'),
(16, 'No tiene');

-- ============================================================================
-- 24. ROLES DE USUARIO
-- ============================================================================
INSERT INTO rol (id, nombre) VALUES
(1, 'Admin'),
(2, 'Coordinador'),
(3, 'Profesional'),
(4, 'Revisor'),
(5, 'Usuario');


-- ============================================================================
-- 25. ESTADOS DE SOLICITUD
-- ============================================================================
insert into estadosolicitud values(1, 'Sin asignar');
insert into estadosolicitud values(2, 'Asignada');


-- ============================================================================
-- 26. ESTADOS DE CITA
-- ============================================================================
insert into estadocita values(1, 'Creada');
insert into estadocita values(2, 'Cancelada');
insert into estadocita values(3, 'Reprogramada');


INSERT INTO public.motivoestadocita (id, nombre) VALUES 
    (1, 'Inasistencia injustificada'),
    (2,'Cambio de agenda de la dupla o la profesional'),
    (3,'Circunstancias externas'),
    (4,'Solicitud de persona a atender'),
    (5,'NA')