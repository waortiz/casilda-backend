CREATE TABLE pais (
    id integer NOT NULL,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pais_pkey PRIMARY KEY (id)
);

CREATE TABLE tipocorreo (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipocorreo_pkey PRIMARY KEY (id)
);

CREATE TABLE sexo (
    id integer NOT NULL,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT sexo_pkey PRIMARY KEY (id)
);

CREATE TABLE tipoidentificacion (
    id integer NOT NULL,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT tipoidentificacion_pkey PRIMARY KEY (id)
);

CREATE TABLE etnia (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT etnia_pkey PRIMARY KEY (id)
);

CREATE TABLE identidadgenero (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT identidadgenero_pkey PRIMARY KEY (id)
);

CREATE TABLE orientacionsexual (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE  NOT NULL,
    CONSTRAINT orientacionsexual_pkey PRIMARY KEY (id)
);

CREATE TABLE departamento (
    id integer NOT NULL,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT departamento_pkey PRIMARY KEY (id)
);

CREATE TABLE municipio (
    id integer NOT NULL,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    iddepartamento INT REFERENCES departamento(id),
    CONSTRAINT municipio_pkey PRIMARY KEY (id)
);

CREATE TABLE discapacidad (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT discapacidad_pkey PRIMARY KEY (id)
);


CREATE TABLE tipotelefono (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipotelefono_pkey PRIMARY KEY (id)
);

CREATE TABLE persona (
    id bigserial NOT NULL,
    primernombre character varying COLLATE pg_catalog."default" NOT NULL,
    segundonombre character varying COLLATE pg_catalog."default",
    primerapellido character varying COLLATE pg_catalog."default" NOT NULL,
    segundoapellido character varying COLLATE pg_catalog."default",
    numerodocumento character varying COLLATE pg_catalog."default" NOT NULL,
    fechanacimiento timestamp without time zone,
    idtipoidentificacion INT not null,
    idsexo INT,
    idetnia INT,
    idpaisnacimiento INT,
    CONSTRAINT persona_pkey PRIMARY KEY (id),
    constraint persona_idtipoidentificacion_fkey FOREIGN KEY (idtipoidentificacion) REFERENCES tipoidentificacion(id) ON DELETE NO ACTION,
    constraint persona_idsexo_fkey FOREIGN KEY (idsexo) REFERENCES sexo(id) ON DELETE NO ACTION,
    constraint persona_idetnia_fkey FOREIGN KEY (idetnia) REFERENCES etnia(id) ON DELETE NO ACTION,
    constraint persona_idpaisnacimiento_fkey FOREIGN KEY (idpaisnacimiento) REFERENCES pais(id) ON DELETE NO ACTION 
);

CREATE TABLE discapacidadpersona (
    idpersona bigint NOT NULL,
    iddiscapacidad INT not null,
    CONSTRAINT discapacidadpersona_pkey PRIMARY KEY (idpersona, iddiscapacidad),
    CONSTRAINT discapacidadpersona_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES persona (id) ON DELETE NO ACTION,
    CONSTRAINT discapacidadpersona_iddiscapacidad_fkey FOREIGN KEY (iddiscapacidad) REFERENCES discapacidad (id) ON DELETE NO ACTION
);

CREATE TABLE correopersona (
    idpersona INT NOT NULL,
    idtipo integer not null,
    correo character varying COLLATE pg_catalog."default" NOT NULL,
    descripcion character varying COLLATE pg_catalog."default" NOT NULL,
    constraint correopersona_idpersona_tipo_fkey UNIQUE (idpersona, idtipo),
    CONSTRAINT correopersona_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES persona(id) ON DELETE NO ACTION,
    CONSTRAINT correopersona_idtipo_fkey FOREIGN KEY (idtipo) REFERENCES tipocorreo(id) ON DELETE NO ACTION
);

CREATE TABLE telefonopersona (
    idpersona INT NOT NULL,
    idtipo integer not null,
    telefono character varying COLLATE pg_catalog."default" NOT NULL,
    descripcion character varying COLLATE pg_catalog."default" NOT NULL,
    constraint telefonopersona_idpersona_tipo_fkey UNIQUE (idpersona, idtipo),
    CONSTRAINT telefonopersona_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES persona(id) ON DELETE NO ACTION,
    CONSTRAINT telefonopersona_idtipo_fkey FOREIGN KEY (idtipo) REFERENCES tipotelefono(id) ON DELETE NO ACTION
);

CREATE TABLE campus (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT campus_pkey PRIMARY KEY (id)
);

CREATE TABLE dependencia (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT dependencia_pkey PRIMARY KEY (id)
);

CREATE TABLE facultadescuelainstituto (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT facultadescuelainstituto_pkey PRIMARY KEY (id)
);


CREATE TABLE vinculoagresorvictima (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT vinculoagresorvictima_pkey PRIMARY KEY (id)
);

CREATE TABLE vinculoudea (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT vinculoudea_pkey PRIMARY KEY (id)
);

CREATE TABLE caso (
    id bigserial NOT NULL,
    idpersona bigint not null,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    idorientacionsexual INT,
    ididentidadgenero INT,
    iddependencia INT,
    idfacultad INT,
    idcampus INT,
    idvinvuloagresorvictima INT,
    idvinculoudea INT,
    circunstancias character varying COLLATE pg_catalog."default",
    CONSTRAINT caso_pkey PRIMARY KEY (id),
    constraint caso_idorientacionsexual_fkey FOREIGN KEY (idorientacionsexual) REFERENCES orientacionsexual(id) ON DELETE NO ACTION,
    constraint caso_ididentidadgenero_fkey FOREIGN KEY (ididentidadgenero) REFERENCES identidadgenero(id) ON DELETE NO ACTION,
    constraint caso_iddependencia_fkey FOREIGN KEY (iddependencia) REFERENCES dependencia(id) ON DELETE NO ACTION,
    constraint caso_idfacultad_fkey FOREIGN KEY (idfacultad) REFERENCES facultadescuelainstituto(id) ON DELETE NO ACTION,
    constraint caso_idcampus_fkey FOREIGN KEY (idcampus) REFERENCES campus(id) ON DELETE NO ACTION,
    constraint caso_idvinvuloagresorvictima_fkey FOREIGN KEY (idvinvuloagresorvictima) REFERENCES vinculoagresorvictima(id) ON DELETE NO ACTION,
    constraint caso_idvinculoudea_fkey FOREIGN KEY (idvinculoudea) REFERENCES vinculoudea(id) ON DELETE NO ACTION
);

CREATE TABLE modalidadviolencia (
    id integer not null,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT modalidadviolencia_pkey PRIMARY KEY (id)
);

CREATE TABLE modalidadviolenciasexual (
    id integer not null,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    CONSTRAINT modalidadviolenciasexual_pkey PRIMARY KEY (id)
);

CREATE TABLE modalidadviolenciacaso (
    idcaso INT not null,
    idmodalidadviolencia  integer NOT NULL,
    CONSTRAINT modalidadviolenciacaso_pkey PRIMARY KEY (idcaso, idmodalidadviolencia),
    CONSTRAINT modalidadviolenciacaso_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES caso(id) ON DELETE NO ACTION,
    CONSTRAINT modalidadviolenciacaso_idmodalidadviolencia_fkey FOREIGN KEY (idmodalidadviolencia) REFERENCES modalidadviolencia(id) ON DELETE NO ACTION
);

CREATE TABLE modalidadviolenciasexualcaso (
    idcaso INT not null,
    idmodalidadviolencia  integer NOT NULL,
    CONSTRAINT modalidadviolenciasexualcaso_pkey PRIMARY KEY (idcaso, idmodalidadviolencia),
    CONSTRAINT modalidadviolenciasexualcaso_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES caso(id) ON DELETE NO ACTION,
    CONSTRAINT modalidadviolenciasexualcaso_idmodalidadviolencia_fkey FOREIGN KEY (idmodalidadviolencia) REFERENCES modalidadviolenciasexual(id) ON DELETE NO ACTION
);

CREATE TABLE tiposolicitud (
    id  integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipo_solicitud_pkey PRIMARY KEY (id)
);


CREATE TABLE estadosolicitud
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT estadosolicitud_pkey PRIMARY KEY (id)
);

CREATE TABLE estadocita
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT estadocita_pkey PRIMARY KEY (id)
);

CREATE TABLE programa (
    id integer,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    descripcion character varying COLLATE pg_catalog."default" not null,
    CONSTRAINT programa_pkey PRIMARY KEY (id)
);

CREATE TABLE programacaso (
    idcaso int not null,
    idprograma INT not null,
    constraint programacaso_pkey PRIMARY KEY (idcaso, idprograma),
    constraint programacaso_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES caso(id) ON DELETE no action,
    constraint programacaso_idprograma_fkey FOREIGN KEY (idprograma) REFERENCES programa(id) ON DELETE no action  
);

CREATE TABLE rol (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    constraint rol_pkey PRIMARY KEY (id)
);

CREATE TABLE usuario (
    id bigserial not null,
    email character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    idrol INT not null,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint usuario_pkey PRIMARY KEY (id),
    constraint usuario_idrol_fkey FOREIGN KEY (idrol) REFERENCES rol(id) ON DELETE NO ACTION
);

CREATE TABLE cargo (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint cargo_pkey PRIMARY KEY (id)
);

CREATE TABLE remision (
    id bigserial not null,
    idremitente bigserial not null,
    idcargo INT not null,
    iddependencia INT not null,
    idfacultad INT not null,
    idcampus INT not null,
    fecha timestamp without time zone NOT NULL,
    constraint remision_pkey PRIMARY KEY (id),
    constraint remision_idremitente_fkey FOREIGN KEY (idremitente) REFERENCES persona(id) ON DELETE NO ACTION,
    constraint remision_idcargo_fkey FOREIGN KEY (idcargo) REFERENCES cargo(id) ON DELETE NO ACTION,
    constraint remision_iddependencia_fkey FOREIGN KEY (iddependencia) REFERENCES dependencia(id) ON DELETE NO ACTION,
    constraint remision_idfacultad_fkey FOREIGN KEY (idfacultad) REFERENCES facultadescuelainstituto(id) ON DELETE NO ACTION,
    constraint remision_idcampus_fkey FOREIGN KEY (idcampus) REFERENCES campus(id) ON DELETE NO ACTION
);


CREATE TABLE solicitudatencion (
    id bigserial NOT NULL,
    idcaso bigint not null,
    idremision bigint,
    fecha timestamp without time zone NOT NULL,
    idtiposolicitud INT not null,
    idestadosolicitud INT not null,
    constraint solicitudatencion_pkey PRIMARY KEY (id),
    constraint solicitudatencion_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES caso(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idremision_fkey FOREIGN KEY (idremision) REFERENCES remision(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idtiposolicitud_fkey FOREIGN KEY (idtiposolicitud) REFERENCES tiposolicitud(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idestadosolicitud_fkey FOREIGN KEY (idestadosolicitud) REFERENCES estadosolicitud(id) ON DELETE NO ACTION  
);

CREATE TABLE jornada (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint jornada_pkey PRIMARY KEY (id)
);

CREATE TABLE resultadocontactotelefonico (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint resultadocontactotelefonico_pkey PRIMARY KEY (id)
);


CREATE TABLE contactotelefonico (
    id bigserial NOT NULL,
    idsolicitudatencion bigint not null,
    fecha timestamp without time zone NOT NULL,
    idjornada INT not null,
    idresultado INT not null,
    CONSTRAINT contactotelefonico_pkey PRIMARY KEY (id),
    CONSTRAINT contactotelefonico_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES solicitudatencion(id) ON DELETE NO ACTION,
    CONSTRAINT contactotelefonico_idjornada_fkey FOREIGN KEY (idjornada) REFERENCES jornada(id) ON DELETE NO ACTION,
    CONSTRAINT contactotelefonico_idresultado_fkey FOREIGN KEY (idresultado) REFERENCES resultadocontactotelefonico(id) ON DELETE NO ACTION
);

CREATE TABLE asignacion (
    id bigserial NOT NULL,
    fecha timestamp without time zone NOT NULL,
    idsolicitudatencion bigint not null,
    constraint asignacion_pkey PRIMARY KEY (id),
    constraint asignacion_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES solicitudatencion(id) ON DELETE NO ACTION
);

CREATE TABLE grupoprofesional (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint grupoprofesional_pkey PRIMARY KEY (id)
);

CREATE TABLE profesionalgrupoprofesional (
    idgrupoprofesional int not null,
    idprofesional bigint not null,
    constraint profesionalgrupoprofesional_pkey PRIMARY KEY (idgrupoprofesional, idprofesional),
    constraint profesionalgrupoprofesional_idgrupoprofesional_fkey FOREIGN KEY (idgrupoprofesional) REFERENCES grupoprofesional(id) ON DELETE NO ACTION,
    constraint profesionalgrupoprofesional_idprofesional_fkey FOREIGN KEY (idprofesional) REFERENCES profesional(idpersona) ON DELETE NO ACTION
);


CREATE TABLE profesional (
    idpersona bigint not null,
    idcargo int  not null,
    constraint profesional_pkey PRIMARY KEY (idpersona),
    constraint profesional_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES persona(id) ON DELETE NO ACTION,
    constraint profesional_idcargo_fkey FOREIGN KEY (idcargo) REFERENCES cargo(id) ON DELETE NO ACTION
);

CREATE TABLE profesionalasignacion (
    idasignacion bigint not null,
    idprofesional bigint not null,
    constraint profesionalasignacion_pkey PRIMARY KEY (idasignacion, idprofesional),
    constraint profesionalasignacion_idasignacion_fkey FOREIGN KEY (idasignacion) REFERENCES asignacion(id) ON DELETE NO ACTION,
    constraint profesionalasignacion_idprofesional_fkey FOREIGN KEY (idprofesional) REFERENCES profesional(idpersona) ON DELETE NO ACTION
);

CREATE TABLE regimen (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint regimen_pkey PRIMARY KEY (id)
);

CREATE TABLE eps (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint eps_pkey PRIMARY KEY (id)
);

CREATE TABLE cita (
    id bigserial NOT NULL,
    idsolicitudatencion bigint not null,
    fecha timestamp without time zone NOT NULL,
    idestadocita INT not null,
    constraint cita_pkey PRIMARY KEY (id),
    constraint cita_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES solicitudatencion(id) ON DELETE NO ACTION,
    constraint cita_idestadocita_fkey FOREIGN KEY (idestadocita) REFERENCES estadocita(id) ON DELETE NO ACTION
);


CREATE TABLE atencion (
    id bigint NOT NULL,
    fecha timestamp without time zone NOT NULL,
    idcita bigint not null,
    constraint atencion_pkey PRIMARY KEY (id),
    constraint atencion_idcita_fkey FOREIGN KEY (idcita) REFERENCES cita(id) ON DELETE NO ACTION
);

CREATE TABLE archivoconsentimiento (
    id bigserial not null,
    idatencion bigint not null,
    contenido bytea NOT NULL,
    tipocontenido character varying(200) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(500) COLLATE pg_catalog."default" NOT NULL,
    constraint archivoconsentimiento_pkey PRIMARY KEY (id),
    constraint archivoconsentimiento_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES atencion(id) ON DELETE NO ACTION
);