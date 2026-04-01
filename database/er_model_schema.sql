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

CREATE TABLE tipodiscapacidad (
    id int PRIMARY KEY,
    nombre character varying COLLATE pg_catalog."default" NOT NULL
);

CREATE TABLE subtipodiscapacidad (
    id int PRIMARY KEY,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    idtipo INTEGER not null,
    CONSTRAINT subtipodiscapacidad_idtipo_fkey FOREIGN KEY (idtipo) REFERENCES tipodiscapacidad(id) ON DELETE NO ACTION
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
    idtipoidentificacion INT not null,
    numerodocumento character varying COLLATE pg_catalog."default" NOT NULL,
    fechanacimiento timestamp without time zone,
    idsexo INT,
    idciudadnacimiento int not null,
    CONSTRAINT persona_pkey PRIMARY KEY (id),
    constraint persona_idtipoidentificacion_fkey FOREIGN KEY (idtipoidentificacion) REFERENCES tipoidentificacion(id) ON DELETE NO ACTION,
    constraint persona_idsexo_fkey FOREIGN KEY (idsexo) REFERENCES sexo(id) ON DELETE NO ACTION,
    constraint persona_idciudadnacimiento_fkey FOREIGN KEY (idciudadnacimiento) REFERENCES municipio(id) ON DELETE NO ACTION
);

CREATE TABLE discapacidadpersona (
    idpersona bigint NOT NULL,
    idsubtipodiscapacidad INT not null,
    descripcion character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT discapacidadpersona_pkey PRIMARY KEY (idpersona, idsubtipodiscapacidad),
    CONSTRAINT discapacidadpersona_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES persona (id) ON DELETE NO ACTION,
    CONSTRAINT discapacidadpersona_idsubtipodiscapacidad_fkey FOREIGN KEY (idsubtipodiscapacidad) REFERENCES subtipodiscapacidad (id) ON DELETE NO ACTION
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

CREATE TABLE subvinculoudea
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT subvinculoudea_pkey PRIMARY KEY (id)
);

CREATE TABLE formaocurrencia
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT formaocurrencia_pkey PRIMARY KEY (id)
);

CREATE TABLE lugarocurrencia
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT lugarocurrencia_pkey PRIMARY KEY (id)
);

CREATE TABLE actividadmisional
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT actividadmisional_pkey PRIMARY KEY (id)
);

CREATE TABLE tipoviolencia
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipoviolencia_pkey PRIMARY KEY (id)
);

CREATE TABLE modalidadviolencia
(
    id integer NOT NULL,
    nombre character varying(200) COLLATE pg_catalog."default" NOT NULL,
    idtipoviolencia integer NOT NULL,
    CONSTRAINT modalidadviolencia_pkey PRIMARY KEY (id),
    CONSTRAINT modalidadviolencia_idtipoviolencia_fkey FOREIGN KEY (idtipoviolencia)
        REFERENCES public.tipoviolencia (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE identidadsexual
(
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT identidadsexual_pkey PRIMARY KEY (id)
);

CREATE TABLE agresorvictima (
    id bigserial NOT NULL,
    idcaso bigint not null,
    primernombre character varying COLLATE pg_catalog."default" NOT NULL,
    segundonombre character varying COLLATE pg_catalog."default",
    primerapellido character varying COLLATE pg_catalog."default" NOT NULL,
    segundoapellido character varying COLLATE pg_catalog."default",
    idvinculoudea INT not null,
    idvinvuloagresorvictima INT not null,
    CONSTRAINT agresorvictima_pkey PRIMARY KEY (id),
    constraint agresorvictima_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES caso(id) ON DELETE NO ACTION, 
    constraint agresorvictima_idvinculoudea_fkey FOREIGN KEY (idvinculoudea) REFERENCES vinculoudea(id) ON DELETE NO ACTION,
    constraint agresorvictima_idvinvuloagresorvictima_fkey FOREIGN KEY (idvinvuloagresorvictima) REFERENCES vinculoagresorvictima(id) ON DELETE NO ACTION
);

CREATE TABLE hecho (
    id bigserial NOT NULL,
    idcaso bigint not null,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP not null,
    lugar character varying COLLATE pg_catalog."default" NOT NULL,
    descripcion character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT hecho_pkey PRIMARY KEY (id),
    constraint hecho_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES caso(id) ON DELETE NO ACTION 
);

CREATE TABLE modalidadviolenciacaso (
    idcaso INT not null,
    idmodalidadviolencia  integer NOT NULL,
    CONSTRAINT modalidadviolenciacaso_pkey PRIMARY KEY (idcaso, idmodalidadviolencia),
    CONSTRAINT modalidadviolenciacaso_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES caso(id) ON DELETE NO ACTION,
    CONSTRAINT modalidadviolenciacaso_idmodalidadviolencia_fkey FOREIGN KEY (idmodalidadviolencia) REFERENCES modalidadviolencia(id) ON DELETE NO ACTION
);

CREATE TABLE apreciacion (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT apreciacion_pkey PRIMARY KEY (id)
);

CREATE TABLE tipoapreciacion (
    id integer NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    idapreciacion integer NOT NULL,
    CONSTRAINT tipoapreciacion_pkey PRIMARY KEY (id),
    CONSTRAINT tipoapreciacion_idapreciacion_fkey FOREIGN KEY (idapreciacion) REFERENCES apreciacion(id) ON DELETE NO ACTION
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

ALTER TABLE caso
    ADD CONSTRAINT caso_idusuariocreacion_fkey FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION,
    ADD CONSTRAINT caso_idusuarioactualizacion_fkey FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION;

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
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuariocreacion bigint,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuarioactualizacion bigint,
    constraint remision_pkey PRIMARY KEY (id),
    constraint remision_idremitente_fkey FOREIGN KEY (idremitente) REFERENCES persona(id) ON DELETE NO ACTION,
    constraint remision_idcargo_fkey FOREIGN KEY (idcargo) REFERENCES cargo(id) ON DELETE NO ACTION,
    constraint remision_iddependencia_fkey FOREIGN KEY (iddependencia) REFERENCES dependencia(id) ON DELETE NO ACTION,
    constraint remision_idfacultad_fkey FOREIGN KEY (idfacultad) REFERENCES facultadescuelainstituto(id) ON DELETE NO ACTION,
    constraint remision_idcampus_fkey FOREIGN KEY (idcampus) REFERENCES campus(id) ON DELETE NO ACTION,
    constraint remision_idusuariocreacion_fkey FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION,
    constraint remision_idusuarioactualizacion_fkey FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION
);


CREATE TABLE solicitudatencion (
    id bigserial NOT NULL,
    idsolicitante bigint not null,
    idremision bigint,
    ididentidadgenero INT,
    idusuarioactualizacion bigint,
    idtiposolicitud INT not null,
    idestadosolicitud INT not null,
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuariocreacion bigint,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint solicitudatencion_pkey PRIMARY KEY (id),
    constraint solicitudatencion_idsolicitante_fkey FOREIGN KEY (idsolicitante) REFERENCES persona(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idremision_fkey FOREIGN KEY (idremision) REFERENCES remision(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idtiposolicitud_fkey FOREIGN KEY (idtiposolicitud) REFERENCES tiposolicitud(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idestadosolicitud_fkey FOREIGN KEY (idestadosolicitud) REFERENCES estadosolicitud(id) ON DELETE NO ACTION,
    constraint solicitudatencion_ididentidadgenero_fkey FOREIGN KEY (ididentidadgenero) REFERENCES identidadgenero(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idusuariocreacion_fkey FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION,
    constraint solicitudatencion_idusuarioactualizacion_fkey FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION
);


CREATE TABLE caso (
    id bigserial NOT NULL,
    idsolicitud bigint not null,
    codigo character varying COLLATE pg_catalog."default" UNIQUE NOT NULL,
    idorientacionsexual INT,
    ididentidadsexual int not null,
    hacecuantooccurrio character varying COLLATE pg_catalog."default",
    idformaocurrencia INT,
    idlugarocurrencia INT,
    violenciabasadagenero boolean,
    hechoviolenciaocurrioactividadesmisionales boolean,
    idactivadmisional int,
    tipoviolenciapsicologica boolean,
    tipoviolenciafisica boolean,
    tipoviolenciasexual boolean,
    tipoviolenciainstitucional boolean,
    tipoviolenciaeconomicapatrimonial boolean,
    tipoviolenciasexualinformatica boolean,
    tipoviolenciaporprejuicio boolean,
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuariocreacion bigint,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuarioactualizacion bigint,
    CONSTRAINT caso_pkey PRIMARY KEY (id),
    constraint caso_idsolicitud_fkey FOREIGN KEY (idsolicitud) REFERENCES solicitudatencion(id) ON DELETE NO ACTION,
    constraint caso_idorientacionsexual_fkey FOREIGN KEY (idorientacionsexual) REFERENCES orientacionsexual(id) ON DELETE NO ACTION,
    constraint caso_ididentidadsexual_fkey FOREIGN KEY (ididentidadsexual) REFERENCES identidadsexual(id) ON DELETE NO ACTION,
    constraint caso_idformaocurrencia_fkey FOREIGN KEY (idformaocurrencia) REFERENCES formaocurrencia(id) ON DELETE NO ACTION,
    constraint caso_idlugarocurrencia_fkey FOREIGN KEY (idlugarocurrencia) REFERENCES lugarocurrencia(id) ON DELETE NO ACTION,
    constraint caso_idactivadmisional_fkey FOREIGN KEY (idactivadmisional) REFERENCES actividadmisional(id) ON DELETE NO ACTION,
);

CREATE TABLE resultadocontactotelefonico (
    id int NOT NULL,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint resultadocontactotelefonico_pkey PRIMARY KEY (id)
);

CREATE TABLE contactotelefonico (
    id bigserial NOT NULL,
    idsolicitudatencion bigint not null,
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuariocreacion bigint,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuarioactualizacion bigint,
    idresultado INT not null,
    CONSTRAINT contactotelefonico_pkey PRIMARY KEY (id),
    CONSTRAINT contactotelefonico_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES solicitudatencion(id) ON DELETE NO ACTION,
    CONSTRAINT contactotelefonico_idresultado_fkey FOREIGN KEY (idresultado) REFERENCES resultadocontactotelefonico(id) ON DELETE NO ACTION,
    CONSTRAINT contactotelefonico_idusuariocreacion_fkey FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION,
    CONSTRAINT contactotelefonico_idusuarioactualizacion_fkey FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION
);


CREATE TABLE profesional (
    idpersona bigint not null,
    idcargo int  not null,
    constraint profesional_pkey PRIMARY KEY (idpersona),
    constraint profesional_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES persona(id) ON DELETE NO ACTION,
    constraint profesional_idcargo_fkey FOREIGN KEY (idcargo) REFERENCES cargo(id) ON DELETE NO ACTION
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

CREATE TABLE tipoasignacion (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint tipoasignacion_pkey PRIMARY KEY (id)
);

CREATE TABLE tiposervicio (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint tiposervicio_pkey PRIMARY KEY (id)
);

CREATE TABLE asignacion (
    id bigserial NOT NULL,
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuariocreacion bigint,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuarioactualizacion bigint,
    idsolicitudatencion bigint not null,
    idgrupoprofesional int not null,
    idtipoasignacion int not null,
    idtiposervicio int not null,
    constraint asignacion_pkey PRIMARY KEY (id),
    constraint asignacion_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES solicitudatencion(id) ON DELETE NO ACTION,
    constraint asignacion_idgrupoprofesional_fkey FOREIGN KEY (idgrupoprofesional) REFERENCES grupoprofesional(id) ON DELETE NO ACTION,
    constraint asignacion_idtipoasignacion_fkey FOREIGN KEY (idtipoasignacion) REFERENCES tipoasignacion(id) ON DELETE NO ACTION,
    constraint asignacion_idtiposervicio_fkey FOREIGN KEY (idtiposervicio) REFERENCES tiposervicio(id) ON DELETE NO ACTION,
    constraint asignacion_idusuariocreacion_fkey FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION,
    constraint asignacion_idusuarioactualizacion_fkey FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION
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

CREATE TABLE motivoestadocita (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint motivoestadocita_pkey PRIMARY KEY (id)
);

CREATE TABLE cita (
    id bigserial NOT NULL,
    idsolicitudatencion bigint not null,
    fecha timestamp without time zone NOT NULL,
    idestadocita INT not null,
    idmotivoestadocita int,
    observaciones character varying COLLATE pg_catalog."default",
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuariocreacion bigint,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuarioactualizacion bigint,
    constraint cita_pkey PRIMARY KEY (id),
    constraint cita_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES solicitudatencion(id) ON DELETE NO ACTION,
    constraint cita_idestadocita_fkey FOREIGN KEY (idestadocita) REFERENCES estadocita(id) ON DELETE NO ACTION,
    constraint cita_idmotivoestadocita_fkey FOREIGN KEY (idmotivoestadocita) REFERENCES motivoestadocita(id) ON DELETE NO ACTION,
    constraint cita_idusuariocreacion_fkey FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION,
    constraint cita_idusuarioactualizacion_fkey FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION
);

CREATE TABLE atencion (
    id bigserial NOT NULL,
    idcita bigint not null,
    idetnia INT,
    idciudadresidencia int,
    direccionresidencia character varying COLLATE pg_catalog."default",
    idprograma int,
    iddependencia INT,
    idfacultad INT,
    idcampus INT,
    idvinculoudea INT,
    idsubvinculoudea INT,
    idtiposervicio int not null,
    idlugarentrevista int not null,
    idregimen int not null,
    ideps int not null,
    logroacuerdo boolean NOT NULL,
    fechacreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuariocreacion bigint,
    fechaactualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idusuarioactualizacion bigint,
    constraint atencion_pkey PRIMARY KEY (id),
    constraint atencion_idcita_fkey FOREIGN KEY (idcita) REFERENCES cita(id) ON DELETE NO ACTION,
    constraint atencion_idetnia_fkey FOREIGN KEY (idetnia) REFERENCES etnia(id) ON DELETE NO ACTION,
    constraint atencion_idciudadresidencia_fkey FOREIGN KEY (idciudadresidencia) REFERENCES municipio(id) ON DELETE NO ACTION,
    constraint atencion_idprograma_fkey FOREIGN KEY (idprograma) REFERENCES programa(id) ON DELETE NO ACTION,
    constraint atencion_iddependencia_fkey FOREIGN KEY (iddependencia) REFERENCES dependencia(id) ON DELETE NO ACTION,
    constraint atencion_idfacultad_fkey FOREIGN KEY (idfacultad) REFERENCES facultadescuelainstituto(id) ON DELETE NO ACTION,
    constraint atencion_idcampus_fkey FOREIGN KEY (idcampus) REFERENCES campus(id) ON DELETE NO ACTION,
    constraint atencion_idvinculoudea_fkey FOREIGN KEY (idvinculoudea) REFERENCES vinculoudea(id) ON DELETE NO ACTION,
    constraint atencion_idsubvinculoudea_fkey FOREIGN KEY (idsubvinculoudea) REFERENCES subvinculoudea(id) ON DELETE NO ACTION,
    constraint atencion_idtiposervicio_fkey FOREIGN KEY (idtiposervicio) REFERENCES tiposervicio(id) ON DELETE NO ACTION,
    constraint atencion_idlugarentrevista_fkey FOREIGN KEY (idlugarentrevista) REFERENCES municipio(id) ON DELETE NO ACTION,   
    constraint atencion_idregimen_fkey FOREIGN KEY (idregimen) REFERENCES regimen(id) ON DELETE NO ACTION,
    constraint atencion_ideps_fkey FOREIGN KEY (ideps) REFERENCES eps(id) ON DELETE NO ACTION,
    constraint atencion_idusuariocreacion_fkey FOREIGN KEY (idusuariocreacion) REFERENCES usuario(id) ON DELETE NO ACTION,
    constraint atencion_idusuarioactualizacion_fkey FOREIGN KEY (idusuarioactualizacion) REFERENCES usuario(id) ON DELETE NO ACTION
);

CREATE TABLE apreciacionatencion (
    idatencion INT not null,
    idtipoapreciacion integer NOT NULL,
    descripcion character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT apreciacionatencion_pkey PRIMARY KEY (idatencion, idtipoapreciacion),
    CONSTRAINT apreciacionatencion_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES atencion(id) ON DELETE NO ACTION,
    CONSTRAINT apreciacionatencion_idtipoapreciacion_fkey FOREIGN KEY (idtipoapreciacion) REFERENCES tipoapreciacion(id) ON DELETE NO ACTION
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

CREATE TABLE parametrosistema (
    id serial NOT NULL,
    clave character varying NOT NULL,
    valor character varying NOT NULL,
    CONSTRAINT parametrosistema_pkey PRIMARY KEY (id),
    CONSTRAINT parametrosistema_clave_unique UNIQUE (clave)
);

CREATE TABLE tiporutaactivacion (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint tiporutaactivacion_pkey PRIMARY KEY (id)
);

CREATE TABLE rutaactivacion (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint rutaactivacion_pkey PRIMARY KEY (id)
); 

CREATE TABLE rutaatencion (
    idatencion bigint not null,
    idtiporutaactivacion int not null,
    idrutaactivacion int not null,
    constraint rutaatencion_pkey PRIMARY KEY (idatencion, idtiporutaactivacion, idrutaactivacion),
    constraint rutaatencion_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES atencion(id) ON DELETE NO ACTION,
    constraint rutaatencion_idtiporutaactivacion_fkey FOREIGN KEY (idtiporutaactivacion) REFERENCES tiporutaactivacion(id) ON DELETE NO ACTION,
    constraint rutaatencion_idrutaactivacion_fkey FOREIGN KEY (idrutaactivacion) REFERENCES rutaactivacion(id) ON DELETE NO ACTION
);

CREATE TABLE tiporemision (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint tiporemision_pkey PRIMARY KEY (id)
);

CREATE TABLE remisionatencion (
    idatencion bigint not null,
    idtiporemision int not null,
    cual character varying COLLATE pg_catalog."default",
    fecha timestamp without time zone NOT NULL,
    constraint remisionatencion_pkey PRIMARY KEY (idatencion, idtiporemision),
    constraint remisionatencion_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES atencion(id) ON DELETE NO ACTION,
    constraint remisionatencion_idtiporemision_fkey FOREIGN KEY (idtiporemision) REFERENCES tiporemision(id) ON DELETE NO ACTION

);

CREATE TABLE grupoatencion (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint grupoatencion_pkey PRIMARY KEY (id)
);

CREATE TABLE tipocompromiso (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint tipocompromiso_pkey PRIMARY KEY (id)
);

CREATE TABLE compromisopersonaatendida (
    idatencion bigint not null,
    fechacompromiso timestamp without time zone NOT NULL,
    idtipocompromiso int not null,
    constraint compromisopersonaatendida_pkey PRIMARY KEY (idatencion, idtipocompromiso),
    constraint compromisopersonaatendida_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES atencion(id) ON DELETE NO ACTION,
    constraint compromisopersonaatendida_idtipocompromiso_fkey FOREIGN KEY (idtipocompromiso) REFERENCES tipocompromiso(id) ON DELETE NO ACTION
);

CREATE TABLE compromisoprofesional (
    idatencion bigint not null,
    fechacompromiso timestamp without time zone NOT NULL,
    idgrupoprofesional int not null,
    idtipocompromiso int not null,
    constraint compromisoprofesional_pkey PRIMARY KEY (idatencion, idtipocompromiso),
    constraint compromisoprofesional_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES atencion(id) ON DELETE NO ACTION,
    constraint compromisoprofesional_idtipocompromiso_fkey FOREIGN KEY (idtipocompromiso) REFERENCES tipocompromiso(id) ON DELETE NO ACTION,
    constraint compromisoprofesional_idgrupoprofesional_fkey FOREIGN KEY (idgrupoprofesional) REFERENCES grupoatencion(id) ON DELETE NO ACTION
);

CREATE TABLE tipocompromiso (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint tipocompromiso_pkey PRIMARY KEY (id)
);


CREATE TABLE tiposeguimiento (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint tiposeguimiento_pkey PRIMARY KEY (id)
);


CREATE TABLE accion (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint accion_pkey PRIMARY KEY (id)
);

CREATE TABLE actividad (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint actividad_pkey PRIMARY KEY (id)
);

CREATE TABLE estadoseguimiento (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint estadoseguimiento_pkey PRIMARY KEY (id)
);

CREATE TABLE motivoestadoseguimiento (
    id int not null,
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    constraint motivoestadoseguimiento_pkey PRIMARY KEY (id)
);

CREATE TABLE seguimientoatencion (
    id bigserial NOT NULL,
    idatencion bigint not null,
    idtiposeguimiento int not null,
    fecha timestamp without time zone NOT NULL,
    idaccion int not null,
    idactividad int not null,
    descripcion character varying COLLATE pg_catalog."default" NOT NULL,
    idestadoseguimiento int not null,
    idmotivoestado int not null,
    constraint seguimientoatencion_pkey PRIMARY KEY (id),
    constraint seguimientoatencion_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES atencion(id) ON DELETE NO ACTION,
    constraint seguimientoatencion_idtiposeguimiento_fkey FOREIGN KEY (idtiposeguimiento) REFERENCES tiposeguimiento(id) ON DELETE NO ACTION,
    constraint seguimientoatencion_idaccion_fkey FOREIGN KEY (idaccion) REFERENCES accion(id) ON DELETE NO ACTION,
    constraint seguimientoatencion_idactividad_fkey FOREIGN KEY (idactividad) REFERENCES actividad(id) ON DELETE NO ACTION,
    constraint seguimientoatencion_idestadoseguimiento_fkey FOREIGN KEY (idestadoseguimiento) REFERENCES estadoseguimiento(id) ON DELETE NO ACTION,
    constraint seguimientoatencion_idmotivoestado_fkey FOREIGN KEY (idmotivoestado) REFERENCES motivoestadoseguimiento(id) ON DELETE NO ACTION
);

CREATE TABLE archivoseguimientoatencion (
    id bigserial not null,
    idseguimientoatencion bigint not null,
    contenido bytea NOT NULL,
    tipocontenido character varying(200) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(500) COLLATE pg_catalog."default" NOT NULL,
    constraint archivoseguimientoatencion_pkey PRIMARY KEY (id),
    constraint archivoseguimientoatencion_idseguimientoatencion_fkey FOREIGN KEY (idseguimientoatencion) REFERENCES seguimientoatencion(id) ON DELETE NO ACTION
);
