--
-- PostgreSQL database dump
--

\restrict 6TrKKqxLurTRKFrziCS0zvxuDVFabe7IQJ3XdQsFZEuHY4RXjWr8aLGSL79DBMR

-- Dumped from database version 14.0
-- Dumped by pg_dump version 18.1

-- Started on 2026-02-24 08:32:05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 259 (class 1259 OID 202848)
-- Name: archivoconsentimiento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.archivoconsentimiento (
    id bigint NOT NULL,
    idatencion bigint NOT NULL,
    contenido bytea NOT NULL,
    tipocontenido character varying(200) NOT NULL,
    nombre character varying(500) NOT NULL
);


ALTER TABLE public.archivoconsentimiento OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 202847)
-- Name: archivoconsentimiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.archivoconsentimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.archivoconsentimiento_id_seq OWNER TO postgres;

--
-- TOC entry 3822 (class 0 OID 0)
-- Dependencies: 258
-- Name: archivoconsentimiento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.archivoconsentimiento_id_seq OWNED BY public.archivoconsentimiento.id;


--
-- TOC entry 253 (class 1259 OID 202772)
-- Name: asignacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asignacion (
    id bigint NOT NULL,
    fecha timestamp without time zone NOT NULL,
    idsolicitudatencion bigint NOT NULL
);


ALTER TABLE public.asignacion OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 202771)
-- Name: asignacion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.asignacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.asignacion_id_seq OWNER TO postgres;

--
-- TOC entry 3823 (class 0 OID 0)
-- Dependencies: 252
-- Name: asignacion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asignacion_id_seq OWNED BY public.asignacion.id;


--
-- TOC entry 257 (class 1259 OID 202827)
-- Name: atencion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.atencion (
    id bigint NOT NULL,
    fecha timestamp without time zone NOT NULL,
    idcita bigint NOT NULL,
    idregimen integer NOT NULL,
    ideps integer NOT NULL,
    idsolicitudatencion bigint NOT NULL
);


ALTER TABLE public.atencion OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 202486)
-- Name: campus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.campus (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.campus OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 203065)
-- Name: campus_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.campus_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.campus_id_seq OWNER TO postgres;

--
-- TOC entry 3824 (class 0 OID 0)
-- Dependencies: 276
-- Name: campus_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.campus_id_seq OWNED BY public.campus.id;


--
-- TOC entry 242 (class 1259 OID 202672)
-- Name: cargo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cargo (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.cargo OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 202522)
-- Name: caso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.caso (
    id bigint NOT NULL,
    idpersona bigint NOT NULL,
    idorientacionsexual integer,
    ididentidadgenero integer,
    iddependencia integer,
    idfacultad integer,
    idcampus integer,
    idvinvuloagresorvictima integer,
    idvinculoudea integer,
    circunstancias character varying,
    codigo character varying NOT NULL,
    fechacreacion timestamp(6) without time zone
);


ALTER TABLE public.caso OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 202521)
-- Name: caso_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.caso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.caso_id_seq OWNER TO postgres;

--
-- TOC entry 3825 (class 0 OID 0)
-- Dependencies: 230
-- Name: caso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.caso_id_seq OWNED BY public.caso.id;


--
-- TOC entry 271 (class 1259 OID 202999)
-- Name: cita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cita (
    id bigint NOT NULL,
    idsolicitudatencion bigint NOT NULL,
    fecha timestamp without time zone NOT NULL,
    idestadocita integer NOT NULL
);


ALTER TABLE public.cita OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 202998)
-- Name: cita_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cita_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cita_id_seq OWNER TO postgres;

--
-- TOC entry 3826 (class 0 OID 0)
-- Dependencies: 270
-- Name: cita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cita_id_seq OWNED BY public.cita.id;


--
-- TOC entry 251 (class 1259 OID 202750)
-- Name: contactotelefonico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contactotelefonico (
    id bigint NOT NULL,
    idsolicitudatencion bigint NOT NULL,
    fecha timestamp without time zone NOT NULL,
    idjornada integer NOT NULL,
    idresultado integer NOT NULL
);


ALTER TABLE public.contactotelefonico OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 202749)
-- Name: contactotelefonico_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contactotelefonico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contactotelefonico_id_seq OWNER TO postgres;

--
-- TOC entry 3827 (class 0 OID 0)
-- Dependencies: 250
-- Name: contactotelefonico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contactotelefonico_id_seq OWNED BY public.contactotelefonico.id;


--
-- TOC entry 223 (class 1259 OID 202452)
-- Name: correopersona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.correopersona (
    idpersona bigint NOT NULL,
    idtipo integer NOT NULL,
    correo character varying NOT NULL,
    descripcion character varying NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.correopersona OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 202862)
-- Name: correopersona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.correopersona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.correopersona_id_seq OWNER TO postgres;

--
-- TOC entry 3828 (class 0 OID 0)
-- Dependencies: 260
-- Name: correopersona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.correopersona_id_seq OWNED BY public.correopersona.id;


--
-- TOC entry 216 (class 1259 OID 202365)
-- Name: departamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.departamento (
    id integer NOT NULL,
    codigo character varying NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.departamento OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 202493)
-- Name: dependencia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dependencia (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.dependencia OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 203066)
-- Name: dependencia_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dependencia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.dependencia_id_seq OWNER TO postgres;

--
-- TOC entry 3829 (class 0 OID 0)
-- Dependencies: 277
-- Name: dependencia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dependencia_id_seq OWNED BY public.dependencia.id;


--
-- TOC entry 218 (class 1259 OID 202392)
-- Name: discapacidad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discapacidad (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.discapacidad OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 202437)
-- Name: discapacidadpersona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discapacidadpersona (
    idpersona bigint NOT NULL,
    iddiscapacidad integer NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.discapacidadpersona OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 202883)
-- Name: discapacidadpersona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.discapacidadpersona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.discapacidadpersona_id_seq OWNER TO postgres;

--
-- TOC entry 3830 (class 0 OID 0)
-- Dependencies: 261
-- Name: discapacidadpersona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.discapacidadpersona_id_seq OWNED BY public.discapacidadpersona.id;


--
-- TOC entry 256 (class 1259 OID 202820)
-- Name: eps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.eps (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.eps OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 202991)
-- Name: estadocita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estadocita (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.estadocita OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 202984)
-- Name: estadosolicitud; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estadosolicitud (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.estadosolicitud OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 202338)
-- Name: etnia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.etnia (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.etnia OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 202500)
-- Name: facultadescuelainstituto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.facultadescuelainstituto (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.facultadescuelainstituto OWNER TO postgres;

--
-- TOC entry 278 (class 1259 OID 203067)
-- Name: facultadescuelainstituto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.facultadescuelainstituto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.facultadescuelainstituto_id_seq OWNER TO postgres;

--
-- TOC entry 3831 (class 0 OID 0)
-- Dependencies: 278
-- Name: facultadescuelainstituto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.facultadescuelainstituto_id_seq OWNED BY public.facultadescuelainstituto.id;


--
-- TOC entry 272 (class 1259 OID 203022)
-- Name: grupoprofesional; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grupoprofesional (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.grupoprofesional OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 202347)
-- Name: identidadgenero; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.identidadgenero (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.identidadgenero OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 202735)
-- Name: jornada; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jornada (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.jornada OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 202565)
-- Name: modalidadviolencia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.modalidadviolencia (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.modalidadviolencia OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 202583)
-- Name: modalidadviolenciacaso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.modalidadviolenciacaso (
    idcaso bigint NOT NULL,
    idmodalidadviolencia integer NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.modalidadviolenciacaso OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 202889)
-- Name: modalidadviolenciacaso_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.modalidadviolenciacaso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.modalidadviolenciacaso_id_seq OWNER TO postgres;

--
-- TOC entry 3832 (class 0 OID 0)
-- Dependencies: 262
-- Name: modalidadviolenciacaso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.modalidadviolenciacaso_id_seq OWNED BY public.modalidadviolenciacaso.id;


--
-- TOC entry 233 (class 1259 OID 202574)
-- Name: modalidadviolenciasexual; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.modalidadviolenciasexual (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.modalidadviolenciasexual OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 202598)
-- Name: modalidadviolenciasexualcaso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.modalidadviolenciasexualcaso (
    idcaso bigint NOT NULL,
    idmodalidadviolencia integer NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.modalidadviolenciasexualcaso OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 202906)
-- Name: modalidadviolenciasexualcaso_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.modalidadviolenciasexualcaso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.modalidadviolenciasexualcaso_id_seq OWNER TO postgres;

--
-- TOC entry 3833 (class 0 OID 0)
-- Dependencies: 263
-- Name: modalidadviolenciasexualcaso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.modalidadviolenciasexualcaso_id_seq OWNED BY public.modalidadviolenciasexualcaso.id;


--
-- TOC entry 217 (class 1259 OID 202376)
-- Name: municipio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.municipio (
    id integer NOT NULL,
    codigo character varying NOT NULL,
    nombre character varying NOT NULL,
    iddepartamento integer
);


ALTER TABLE public.municipio OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 202356)
-- Name: orientacionsexual; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orientacionsexual (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.orientacionsexual OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 202300)
-- Name: pais; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pais (
    id integer NOT NULL,
    codigo character varying NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.pais OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 202409)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    id bigint NOT NULL,
    primernombre character varying NOT NULL,
    segundonombre character varying,
    primerapellido character varying NOT NULL,
    segundoapellido character varying,
    numerodocumento character varying NOT NULL,
    fechanacimiento date,
    idtipoidentificacion integer NOT NULL,
    idsexo integer,
    idetnia integer,
    idpaisnacimiento integer
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 202408)
-- Name: persona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.persona_id_seq OWNER TO postgres;

--
-- TOC entry 3834 (class 0 OID 0)
-- Dependencies: 220
-- Name: persona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.persona_id_seq OWNED BY public.persona.id;


--
-- TOC entry 254 (class 1259 OID 202783)
-- Name: profesional; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profesional (
    idpersona bigint NOT NULL,
    idcargo integer NOT NULL
);


ALTER TABLE public.profesional OWNER TO postgres;

--
-- TOC entry 274 (class 1259 OID 203044)
-- Name: profesionalasignacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profesionalasignacion (
    idasignacion bigint NOT NULL,
    idprofesional bigint NOT NULL
);


ALTER TABLE public.profesionalasignacion OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 203029)
-- Name: profesionalgrupoprofesional; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profesionalgrupoprofesional (
    idgrupoprofesional integer NOT NULL,
    idprofesional bigint NOT NULL
);


ALTER TABLE public.profesionalgrupoprofesional OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 202620)
-- Name: programa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.programa (
    id integer NOT NULL,
    nombre character varying NOT NULL,
    codigo character varying NOT NULL,
    descripcion character varying NOT NULL
);


ALTER TABLE public.programa OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 202629)
-- Name: programacaso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.programacaso (
    idcaso bigint NOT NULL,
    idprograma integer NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.programacaso OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 202929)
-- Name: programacaso_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.programacaso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.programacaso_id_seq OWNER TO postgres;

--
-- TOC entry 3835 (class 0 OID 0)
-- Dependencies: 264
-- Name: programacaso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.programacaso_id_seq OWNED BY public.programacaso.id;


--
-- TOC entry 255 (class 1259 OID 202813)
-- Name: regimen; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.regimen (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.regimen OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 202681)
-- Name: remision; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.remision (
    id bigint NOT NULL,
    idremitente bigint NOT NULL,
    idcargo integer NOT NULL,
    iddependencia integer NOT NULL,
    idfacultad integer NOT NULL,
    idcampus integer NOT NULL,
    fecha timestamp without time zone NOT NULL
);


ALTER TABLE public.remision OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 202679)
-- Name: remision_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.remision_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.remision_id_seq OWNER TO postgres;

--
-- TOC entry 3836 (class 0 OID 0)
-- Dependencies: 243
-- Name: remision_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.remision_id_seq OWNED BY public.remision.id;


--
-- TOC entry 244 (class 1259 OID 202680)
-- Name: remision_idremitente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.remision_idremitente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.remision_idremitente_seq OWNER TO postgres;

--
-- TOC entry 3837 (class 0 OID 0)
-- Dependencies: 244
-- Name: remision_idremitente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.remision_idremitente_seq OWNED BY public.remision.idremitente;


--
-- TOC entry 266 (class 1259 OID 202947)
-- Name: remitente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.remitente (
    id bigint NOT NULL,
    nombre character varying(200) NOT NULL
);


ALTER TABLE public.remitente OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 202946)
-- Name: remitente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.remitente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.remitente_id_seq OWNER TO postgres;

--
-- TOC entry 3838 (class 0 OID 0)
-- Dependencies: 265
-- Name: remitente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.remitente_id_seq OWNED BY public.remitente.id;


--
-- TOC entry 249 (class 1259 OID 202742)
-- Name: resultadocontactotelefonico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resultadocontactotelefonico (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.resultadocontactotelefonico OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 202644)
-- Name: rol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rol (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.rol OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 202316)
-- Name: sexo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sexo (
    id integer NOT NULL,
    codigo character varying NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.sexo OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 203075)
-- Name: solicitud_acompanamiento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.solicitud_acompanamiento (
    id bigint NOT NULL,
    codigo character varying(50) NOT NULL,
    estado character varying(50),
    fecha_creacion timestamp(6) without time zone NOT NULL,
    rem_cargo character varying(150),
    rem_primer_apellido character varying(100),
    rem_primer_nombre character varying(100),
    rem_segundo_apellido character varying(100),
    rem_segundo_nombre character varying(100),
    sol_campus character varying(150) NOT NULL,
    sol_celular character varying(10) NOT NULL,
    sol_celular_alterno character varying(10) NOT NULL,
    sol_correo_institucional character varying(150) NOT NULL,
    sol_correo_personal character varying(150) NOT NULL,
    sol_dependencia character varying(150) NOT NULL,
    sol_edad integer NOT NULL,
    sol_facultad character varying(150) NOT NULL,
    sol_identidad_genero character varying(50) NOT NULL,
    sol_numero_documento character varying(20) NOT NULL,
    sol_primer_apellido character varying(100) NOT NULL,
    sol_primer_nombre character varying(100) NOT NULL,
    sol_segundo_apellido character varying(100),
    sol_segundo_nombre character varying(100),
    sol_tipo_documento character varying(50) NOT NULL,
    tipo_reporte character varying(20) NOT NULL,
    tipo_solicitud character varying(100) NOT NULL
);


ALTER TABLE public.solicitud_acompanamiento OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 203074)
-- Name: solicitud_acompanamiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.solicitud_acompanamiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.solicitud_acompanamiento_id_seq OWNER TO postgres;

--
-- TOC entry 3839 (class 0 OID 0)
-- Dependencies: 280
-- Name: solicitud_acompanamiento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.solicitud_acompanamiento_id_seq OWNED BY public.solicitud_acompanamiento.id;


--
-- TOC entry 247 (class 1259 OID 202714)
-- Name: solicitudatencion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.solicitudatencion (
    id bigint NOT NULL,
    idcaso bigint NOT NULL,
    idremision bigint,
    fecha timestamp without time zone NOT NULL,
    idtiposolicitud integer NOT NULL,
    idestadosolicitud integer NOT NULL
);


ALTER TABLE public.solicitudatencion OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 202713)
-- Name: solicitudatencion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.solicitudatencion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.solicitudatencion_id_seq OWNER TO postgres;

--
-- TOC entry 3840 (class 0 OID 0)
-- Dependencies: 246
-- Name: solicitudatencion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.solicitudatencion_id_seq OWNED BY public.solicitudatencion.id;


--
-- TOC entry 224 (class 1259 OID 202469)
-- Name: telefonopersona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.telefonopersona (
    idpersona bigint NOT NULL,
    idtipo integer NOT NULL,
    telefono character varying NOT NULL,
    descripcion character varying NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.telefonopersona OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 202953)
-- Name: telefonopersona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.telefonopersona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.telefonopersona_id_seq OWNER TO postgres;

--
-- TOC entry 3841 (class 0 OID 0)
-- Dependencies: 267
-- Name: telefonopersona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.telefonopersona_id_seq OWNED BY public.telefonopersona.id;


--
-- TOC entry 210 (class 1259 OID 202309)
-- Name: tipocorreo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipocorreo (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.tipocorreo OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 202327)
-- Name: tipoidentificacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipoidentificacion (
    id integer NOT NULL,
    codigo character varying NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.tipoidentificacion OWNER TO postgres;

--
-- TOC entry 279 (class 1259 OID 203068)
-- Name: tipoidentificacion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipoidentificacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tipoidentificacion_id_seq OWNER TO postgres;

--
-- TOC entry 3842 (class 0 OID 0)
-- Dependencies: 279
-- Name: tipoidentificacion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipoidentificacion_id_seq OWNED BY public.tipoidentificacion.id;


--
-- TOC entry 236 (class 1259 OID 202613)
-- Name: tiposolicitud; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tiposolicitud (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.tiposolicitud OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 203064)
-- Name: tiposolicitud_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tiposolicitud_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tiposolicitud_id_seq OWNER TO postgres;

--
-- TOC entry 3843 (class 0 OID 0)
-- Dependencies: 275
-- Name: tiposolicitud_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tiposolicitud_id_seq OWNED BY public.tiposolicitud.id;


--
-- TOC entry 219 (class 1259 OID 202401)
-- Name: tipotelefono; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipotelefono (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.tipotelefono OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 202654)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    email character varying NOT NULL,
    password character varying NOT NULL,
    nombre character varying NOT NULL,
    idrol integer NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    fechacreacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    fechaactualizacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 202653)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_id_seq OWNER TO postgres;

--
-- TOC entry 3844 (class 0 OID 0)
-- Dependencies: 240
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 228 (class 1259 OID 202507)
-- Name: vinculoagresorvictima; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vinculoagresorvictima (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.vinculoagresorvictima OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 202514)
-- Name: vinculoudea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vinculoudea (
    id integer NOT NULL,
    nombre character varying NOT NULL
);


ALTER TABLE public.vinculoudea OWNER TO postgres;

--
-- TOC entry 3404 (class 2604 OID 202851)
-- Name: archivoconsentimiento id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivoconsentimiento ALTER COLUMN id SET DEFAULT nextval('public.archivoconsentimiento_id_seq'::regclass);


--
-- TOC entry 3403 (class 2604 OID 202775)
-- Name: asignacion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asignacion ALTER COLUMN id SET DEFAULT nextval('public.asignacion_id_seq'::regclass);


--
-- TOC entry 3387 (class 2604 OID 203070)
-- Name: campus id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campus ALTER COLUMN id SET DEFAULT nextval('public.campus_id_seq'::regclass);


--
-- TOC entry 3390 (class 2604 OID 202525)
-- Name: caso id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso ALTER COLUMN id SET DEFAULT nextval('public.caso_id_seq'::regclass);


--
-- TOC entry 3406 (class 2604 OID 203002)
-- Name: cita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita ALTER COLUMN id SET DEFAULT nextval('public.cita_id_seq'::regclass);


--
-- TOC entry 3402 (class 2604 OID 202753)
-- Name: contactotelefonico id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactotelefonico ALTER COLUMN id SET DEFAULT nextval('public.contactotelefonico_id_seq'::regclass);


--
-- TOC entry 3385 (class 2604 OID 202863)
-- Name: correopersona id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.correopersona ALTER COLUMN id SET DEFAULT nextval('public.correopersona_id_seq'::regclass);


--
-- TOC entry 3388 (class 2604 OID 203071)
-- Name: dependencia id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dependencia ALTER COLUMN id SET DEFAULT nextval('public.dependencia_id_seq'::regclass);


--
-- TOC entry 3384 (class 2604 OID 202884)
-- Name: discapacidadpersona id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discapacidadpersona ALTER COLUMN id SET DEFAULT nextval('public.discapacidadpersona_id_seq'::regclass);


--
-- TOC entry 3389 (class 2604 OID 203072)
-- Name: facultadescuelainstituto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facultadescuelainstituto ALTER COLUMN id SET DEFAULT nextval('public.facultadescuelainstituto_id_seq'::regclass);


--
-- TOC entry 3391 (class 2604 OID 202890)
-- Name: modalidadviolenciacaso id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciacaso ALTER COLUMN id SET DEFAULT nextval('public.modalidadviolenciacaso_id_seq'::regclass);


--
-- TOC entry 3392 (class 2604 OID 202907)
-- Name: modalidadviolenciasexualcaso id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciasexualcaso ALTER COLUMN id SET DEFAULT nextval('public.modalidadviolenciasexualcaso_id_seq'::regclass);


--
-- TOC entry 3383 (class 2604 OID 202412)
-- Name: persona id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona ALTER COLUMN id SET DEFAULT nextval('public.persona_id_seq'::regclass);


--
-- TOC entry 3394 (class 2604 OID 202930)
-- Name: programacaso id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programacaso ALTER COLUMN id SET DEFAULT nextval('public.programacaso_id_seq'::regclass);


--
-- TOC entry 3399 (class 2604 OID 202684)
-- Name: remision id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision ALTER COLUMN id SET DEFAULT nextval('public.remision_id_seq'::regclass);


--
-- TOC entry 3400 (class 2604 OID 202685)
-- Name: remision idremitente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision ALTER COLUMN idremitente SET DEFAULT nextval('public.remision_idremitente_seq'::regclass);


--
-- TOC entry 3405 (class 2604 OID 202950)
-- Name: remitente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remitente ALTER COLUMN id SET DEFAULT nextval('public.remitente_id_seq'::regclass);


--
-- TOC entry 3407 (class 2604 OID 203078)
-- Name: solicitud_acompanamiento id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitud_acompanamiento ALTER COLUMN id SET DEFAULT nextval('public.solicitud_acompanamiento_id_seq'::regclass);


--
-- TOC entry 3401 (class 2604 OID 202717)
-- Name: solicitudatencion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitudatencion ALTER COLUMN id SET DEFAULT nextval('public.solicitudatencion_id_seq'::regclass);


--
-- TOC entry 3386 (class 2604 OID 202954)
-- Name: telefonopersona id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telefonopersona ALTER COLUMN id SET DEFAULT nextval('public.telefonopersona_id_seq'::regclass);


--
-- TOC entry 3382 (class 2604 OID 203073)
-- Name: tipoidentificacion id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipoidentificacion ALTER COLUMN id SET DEFAULT nextval('public.tipoidentificacion_id_seq'::regclass);


--
-- TOC entry 3393 (class 2604 OID 203069)
-- Name: tiposolicitud id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tiposolicitud ALTER COLUMN id SET DEFAULT nextval('public.tiposolicitud_id_seq'::regclass);


--
-- TOC entry 3395 (class 2604 OID 202657)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 3793 (class 0 OID 202848)
-- Dependencies: 259
-- Data for Name: archivoconsentimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.archivoconsentimiento (id, idatencion, contenido, tipocontenido, nombre) FROM stdin;
\.


--
-- TOC entry 3787 (class 0 OID 202772)
-- Dependencies: 253
-- Data for Name: asignacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.asignacion (id, fecha, idsolicitudatencion) FROM stdin;
\.


--
-- TOC entry 3791 (class 0 OID 202827)
-- Dependencies: 257
-- Data for Name: atencion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.atencion (id, fecha, idcita, idregimen, ideps, idsolicitudatencion) FROM stdin;
\.


--
-- TOC entry 3759 (class 0 OID 202486)
-- Dependencies: 225
-- Data for Name: campus; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.campus (id, nombre) FROM stdin;
1	Campus Apartadó
2	Campus Carepa
3	Campus Turbo
4	Campus Caucasia
5	Campus Santa Fe de Antioquia
6	Campus Yarumal
7	Campus Amalfi
8	Campus Segovia
9	Campus Andes
10	Campus La Pintada
11	Campus El Carmen de Viboral
12	Campus Sonsón
13	Campus Puerto Berrío
14	Campus Medellín
\.


--
-- TOC entry 3776 (class 0 OID 202672)
-- Dependencies: 242
-- Data for Name: cargo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cargo (id, nombre) FROM stdin;
1	Psicóloga
2	Abogada
3	Trabajadora Social
4	Psicoorientadora
5	Coordinadora de Prevención
6	Coordinadora de Atención
7	Docente
8	Administrativo
9	Otro
\.


--
-- TOC entry 3765 (class 0 OID 202522)
-- Dependencies: 231
-- Data for Name: caso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.caso (id, idpersona, idorientacionsexual, ididentidadgenero, iddependencia, idfacultad, idcampus, idvinvuloagresorvictima, idvinculoudea, circunstancias, codigo, fechacreacion) FROM stdin;
7	14	\N	1	1	1	1	\N	\N	\N	ACO-2026-0001	2026-02-23 13:30:34.230026
8	14	\N	1	1	1	1	\N	\N	\N	ACO-2026-0002	2026-02-23 13:35:00.517791
9	14	\N	1	1	1	1	\N	\N	\N	ACO-2026-0003	2026-02-23 13:38:19.908019
10	14	\N	1	1	1	1	\N	\N	\N	ACO-2026-0004	2026-02-23 13:42:28.688708
11	15	\N	4	5	4	4	\N	\N	\N	ACO-2026-0005	2026-02-23 13:44:01.80451
12	16	\N	5	2	2	1	\N	\N	\N	ACO-2026-0006	2026-02-23 15:37:06.709235
\.


--
-- TOC entry 3805 (class 0 OID 202999)
-- Dependencies: 271
-- Data for Name: cita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cita (id, idsolicitudatencion, fecha, idestadocita) FROM stdin;
\.


--
-- TOC entry 3785 (class 0 OID 202750)
-- Dependencies: 251
-- Data for Name: contactotelefonico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contactotelefonico (id, idsolicitudatencion, fecha, idjornada, idresultado) FROM stdin;
\.


--
-- TOC entry 3757 (class 0 OID 202452)
-- Dependencies: 223
-- Data for Name: correopersona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.correopersona (idpersona, idtipo, correo, descripcion, id) FROM stdin;
14	1	correo@udea.edu.co	Correo institucional	13
14	2	correo@gmail.com	Correo personal	14
15	1	jaime@hotmail.com	Correo institucional	15
15	2	jaime@udea.edu.co	Correo personal	16
16	1	nancy@hotmail.com	Correo institucional	17
16	2	nancy1@hotmail.com	Correo personal	18
\.


--
-- TOC entry 3750 (class 0 OID 202365)
-- Dependencies: 216
-- Data for Name: departamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.departamento (id, codigo, nombre) FROM stdin;
1	05	Antioquia
2	08	Atlántico
3	11	Bogotá D.C.
4	13	Bolívar
5	15	Boyacá
6	17	Caldas
7	18	Caquetá
8	19	Cauca
9	20	Cesar
10	23	Córdoba
11	25	Cundinamarca
12	27	Chocó
13	41	Huila
14	44	La Guajira
15	47	Magdalena
16	50	Meta
17	52	Nariño
18	54	Norte de Santander
19	63	Quindío
20	66	Risaralda
21	68	Santander
22	70	Sucre
23	73	Tolima
24	76	Valle del Cauca
25	81	Arauca
26	85	Casanare
27	86	Putumayo
28	88	San Andrés
29	91	Amazonas
30	94	Guainía
31	95	Guaviare
32	97	Vaupés
33	99	Vichada
\.


--
-- TOC entry 3760 (class 0 OID 202493)
-- Dependencies: 226
-- Data for Name: dependencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dependencia (id, nombre) FROM stdin;
1	Dirección de Bienestar Universitario
2	Dirección de Comunicaciones
3	Dirección de Planeación y Desarrollo Institucional
4	Dirección de Posgrado
5	Dirección de Regionalización
6	Dirección de Relaciones Internacionales
7	Dirección Jurídica
8	Rectoría
9	Secretaría General
10	Vicerrectoría Administrativa
11	Vicerrectoría de Docencia
12	Vicerrectoría de Extensión
13	Vicerrectoría de Investigación
14	Vicerrectoría General
15	Publicaciones
16	División de Gestión Documental
17	Admisiones y Registro
18	Sistema de Bibliotecas
19	Escuela de Gobierno
20	Oficina de Auditoría Institucional
21	Talento Humano
22	Seguridad a Personas y Bienes
\.


--
-- TOC entry 3752 (class 0 OID 202392)
-- Dependencies: 218
-- Data for Name: discapacidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.discapacidad (id, nombre) FROM stdin;
1	Persona ciega
2	Persona con baja visión
3	Persona sordo-oralizado
4	Persona con hipoacusia
5	Persona con compromiso en miembros superiores
6	Persona con compromiso en miembros inferiores
7	Persona con compromiso en miembros superiores e inferiores
8	Discapacidad intelectual
9	Discapacidad psicosocial
10	Discapacidad múltiple
11	Talla baja
12	Sordoceguera
13	Persona sordoseñante
14	Ninguna
\.


--
-- TOC entry 3756 (class 0 OID 202437)
-- Dependencies: 222
-- Data for Name: discapacidadpersona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.discapacidadpersona (idpersona, iddiscapacidad, id) FROM stdin;
\.


--
-- TOC entry 3790 (class 0 OID 202820)
-- Dependencies: 256
-- Data for Name: eps; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.eps (id, nombre) FROM stdin;
1	Sura EPS
2	Salud Total
3	Sanitas
4	Compensar
5	Nueva EPS
6	Coomeva EPS
7	Famisanar
8	Cafesalud
9	SOS EPS
10	Medimás
11	Capital Salud
12	Aliansalud
13	EPS SAVIA SALUD
14	Mutual SER
15	Otra
16	No tiene
\.


--
-- TOC entry 3803 (class 0 OID 202991)
-- Dependencies: 269
-- Data for Name: estadocita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estadocita (id, nombre) FROM stdin;
1	Sin asignaro
2	Asiganda
\.


--
-- TOC entry 3802 (class 0 OID 202984)
-- Dependencies: 268
-- Data for Name: estadosolicitud; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estadosolicitud (id, nombre) FROM stdin;
2	Asiganda
1	Sin asignar
\.


--
-- TOC entry 3747 (class 0 OID 202338)
-- Dependencies: 213
-- Data for Name: etnia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.etnia (id, nombre) FROM stdin;
1	Afrodescendiente
2	Indígena
3	Palenquero-a
4	Gitano-a (Rrom)
5	Raizal
6	Ningún grupo étnico
\.


--
-- TOC entry 3761 (class 0 OID 202500)
-- Dependencies: 227
-- Data for Name: facultadescuelainstituto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.facultadescuelainstituto (id, nombre) FROM stdin;
1	Escuela de Microbiología
2	Escuela de Nutrición y Dietética
3	Escuela de Idiomas
4	Escuela Interamericana de Bibliotecología
5	Facultad de Artes
6	Facultad de Ciencias Agrarias
7	Facultad de Ciencias Económicas
8	Facultad de Ciencias Exactas y Naturales
9	Facultad de Ciencias Farmacéuticas y Alimentarias
10	Facultad de Ciencias Sociales y Humanas
11	Facultad de Comunicaciones
12	Facultad de Derecho y Ciencias Políticas
13	Facultad de Educación
14	Facultad de Enfermería
15	Facultad de Ingeniería
16	Facultad de Medicina
17	Facultad de Odontología
18	Facultad Nacional de Salud Pública
19	Instituto de Estudios Políticos
20	Instituto de Estudios Regionales
21	Instituto de Filosofía
22	Instituto Universitario de Educación Física y Deportes
\.


--
-- TOC entry 3806 (class 0 OID 203022)
-- Dependencies: 272
-- Data for Name: grupoprofesional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.grupoprofesional (id, nombre) FROM stdin;
\.


--
-- TOC entry 3748 (class 0 OID 202347)
-- Dependencies: 214
-- Data for Name: identidadgenero; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.identidadgenero (id, nombre) FROM stdin;
1	Mujer cisgénero
2	Hombre cisgénero
3	Mujer trans
4	Hombre trans
5	No binaria
6	Prefiere no responder
7	Otra
8	SD
\.


--
-- TOC entry 3782 (class 0 OID 202735)
-- Dependencies: 248
-- Data for Name: jornada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.jornada (id, nombre) FROM stdin;
1	Mañana
2	Tarde
3	N/A
\.


--
-- TOC entry 3766 (class 0 OID 202565)
-- Dependencies: 232
-- Data for Name: modalidadviolencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.modalidadviolencia (id, nombre) FROM stdin;
1	Acción que degrade a la otra persona
2	Omisión que degrade a la otra persona
3	Acción que busque controlar los comportamientos, creencias o decisiones de otra persona
4	Omisión que busque controlar los comportamientos, creencias o decisiones de otra persona
5	Intimidación
6	Manipulación
7	Amenaza directa
8	Amenaza indirecta
9	Humillación
10	Aislamiento
11	Constreñimiento ilegal
12	Lenguaje misógino, sexista o discursos de odio
13	Injuria
14	Calumnia
15	Injuria por vía de hecho
16	Abuso de poder y/o confianza
17	Violencia epistémica
18	Violencia psicológica facilitada por las nuevas tecnologías: Acceso, uso, control, manipulación, intercambio o publicación no autorizada de información privada y datos personales
19	Violencia psicológica facilitada por las nuevas tecnologías: Suplantación y robo de identidad
20	Violencia psicológica facilitada por las nuevas tecnologías: Actos que implican la vigilancia y el monitoreo de una persona
21	Violencia psicológica facilitada por las nuevas tecnologías: Ciberhostigamiento o ciberacecho
22	Violencia psicológica facilitada por las nuevas tecnologías: Ciberacoso
23	Violencia psicológica facilitada por las nuevas tecnologías: Ciberbullying
24	Violencia psicológica facilitada por las nuevas tecnologías: Slutshaming
25	Violencia psicológica facilitada por las nuevas tecnologías: Ataques a grupos, organizaciones o comunidades de mujeres
26	Otra conducta que implique un perjuicio a la salud psicológica, autodeterminación o al desarrollo personal
27	Agresión física
28	Feminicidio tentado o consumado
29	Restricción a la libertad física
30	Violencia física facilitada por las nuevas tecnologías
31	Pérdida, transformación, sustracción, destrucción, retención o distracción: de objetos o bienes de la persona
32	Pérdida, transformación, sustracción, destrucción, retención o distracción: de instrumentos de trabajo de la persona
33	Pérdida, transformación, sustracción, destrucción, retención o distracción: de documentos personales
34	Pérdida, transformación, sustracción, destrucción, retención o distracción: de valores o derechos económicos destinados a satisfacer las necesidades de la persona
35	Extorsión
36	Estafa
37	Inasistencia alimentaria
38	Control económico
39	Violencia económica facilitada por las nuevas tecnologías
40	Discriminación por género u orientación sexual o identidad de género
41	Hostigamiento
42	Omisión del deber de denuncia
43	Omisión al deber de debida diligencia
44	Revictimización
\.


--
-- TOC entry 3768 (class 0 OID 202583)
-- Dependencies: 234
-- Data for Name: modalidadviolenciacaso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.modalidadviolenciacaso (idcaso, idmodalidadviolencia, id) FROM stdin;
\.


--
-- TOC entry 3767 (class 0 OID 202574)
-- Dependencies: 233
-- Data for Name: modalidadviolenciasexual; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.modalidadviolenciasexual (id, nombre) FROM stdin;
1	Acceso carnal violento
2	Acto sexual violento
3	Explotación sexual: Inducción a la prostitución
4	Explotación sexual: Proxenetismo con menor de edad
5	Explotación sexual: Constreñimiento a la prostitución
6	Explotación sexual: Trata de personas
7	Explotación sexual: Estímulo a la prostitución de menores
8	Explotación sexual: Demanda de explotación sexual comercial de persona menor de 18 años de edad
9	Explotación sexual: Pornografía con personas menores de 18 años
10	Acoso sexual
11	Stealthing
12	Obligar a otra persona a realizar cualquier acto o interacción sexual con tercera persona
13	Violencia sexual correctiva
14	Violencia sexual facilitada por las nuevas tecnologías: Creación, difusión, distribución o intercambio digital de fotografías, videos o audioclips de naturaleza sexual o íntima sin consentimiento
15	Violencia sexual facilitada por las nuevas tecnologías: Amenazas directas de daño o violencia – sextorsion
16	Violencia sexual facilitada por las nuevas tecnologías: Grooming
17	Violencia sexual facilitada por las nuevas tecnologías: Sexting sin consentimiento
18	Violencia sexual facilitada por las nuevas tecnologías: Abuso, explotación y/o trata de mujeres y niñas por medio de las tecnologías
19	Otra
\.


--
-- TOC entry 3769 (class 0 OID 202598)
-- Dependencies: 235
-- Data for Name: modalidadviolenciasexualcaso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.modalidadviolenciasexualcaso (idcaso, idmodalidadviolencia, id) FROM stdin;
\.


--
-- TOC entry 3751 (class 0 OID 202376)
-- Dependencies: 217
-- Data for Name: municipio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.municipio (id, codigo, nombre, iddepartamento) FROM stdin;
1	05001	Medellín	1
2	05002	Abejorral	1
3	05004	Abriaquí	1
4	05021	Alejandría	1
5	05030	Amagá	1
6	05031	Amalfi	1
7	05034	Andes	1
8	05036	Angelópolis	1
9	05038	Angostura	1
10	05040	Anorí	1
11	05044	Santafé de Antioquia	1
12	05045	Anza	1
13	05051	Apartadó	1
14	05055	Arboletes	1
15	05059	Argelia	1
16	05079	Barbosa	1
17	05086	Belmira	1
18	05088	Bello	1
19	05091	Betania	1
20	05093	Betulia	1
21	05101	Ciudad Bolívar	1
22	05107	Briceño	1
23	05113	Buriticá	1
24	05120	Cáceres	1
25	05125	Caicedo	1
26	05129	Caldas	1
27	05134	Campamento	1
28	05138	Cañasgordas	1
29	05142	Caracolí	1
30	05145	Caramanta	1
31	05147	Carepa	1
32	05148	El Carmen de Viboral	1
33	05150	Carolina	1
34	05154	Caucasia	1
35	05172	Chigorodó	1
36	05190	Cisneros	1
37	05197	Cocorná	1
38	05206	Concepción	1
39	05209	Concordia	1
40	05212	Copacabana	1
41	05234	Dabeiba	1
42	05237	Donmatías	1
43	05240	Ebéjico	1
44	05250	El Bagre	1
45	05264	Entrerríos	1
46	05266	Envigado	1
47	05282	Fredonia	1
48	05284	Frontino	1
49	05306	Giraldo	1
50	05308	Girardota	1
51	05310	Gómez Plata	1
52	05315	Granada	1
53	05318	Guadalupe	1
54	05321	Guarne	1
55	05347	Guatapé	1
56	05353	Heliconia	1
57	05360	Hispania	1
58	05361	Itagüí	1
59	05364	Ituango	1
60	05368	Jardín	1
61	05376	Jericó	1
62	05380	La Ceja	1
63	05390	La Estrella	1
64	05400	La Pintada	1
65	05411	La Unión	1
66	05425	Liborina	1
67	05440	Maceo	1
68	05467	Marinilla	1
69	05475	Montebello	1
70	05480	Murindó	1
71	05483	Mutatá	1
72	05490	Nariño	1
73	05495	Necoclí	1
74	05501	Nechí	1
75	05541	Olaya	1
76	05543	Peñol	1
77	05576	Peque	1
78	05579	Pueblorrico	1
79	05585	Puerto Berrío	1
80	05591	Puerto Nare	1
81	05604	Puerto Triunfo	1
82	05607	Remedios	1
83	05615	Retiro	1
84	05628	Rionegro	1
85	05631	Sabanalarga	1
86	05642	Sabaneta	1
87	05647	Salgar	1
88	05649	San Andrés de Cuerquia	1
89	05652	San Carlos	1
90	05656	San Francisco	1
91	05658	San Jerónimo	1
92	05659	San José de la Montaña	1
93	05660	San Juan de Urabá	1
94	05664	San Luis	1
95	05665	San Pedro	1
96	05667	San Pedro de Uraba	1
97	05670	San Rafael	1
98	05674	San Roque	1
99	05679	San Vicente	1
100	05686	Santa Bárbara	1
101	05690	Santa Rosa de Osos	1
102	05697	Santo Domingo	1
103	05736	El Santuario	1
104	05756	Segovia	1
105	05761	Sonsón	1
106	05789	Sopetrán	1
107	05790	Támesis	1
108	05792	Tarazá	1
109	05809	Tarso	1
110	05819	Titiribí	1
111	05837	Toledo	1
112	05842	Turbo	1
113	05847	Uramita	1
114	05854	Urrao	1
115	05856	Valdivia	1
116	05858	Valparaíso	1
117	05861	Vegachí	1
118	05873	Venecia	1
119	05885	Vigía del Fuerte	1
120	05887	Yalí	1
121	05890	Yarumal	1
122	05893	Yolombó	1
123	05895	Yondó	1
124	05898	Zaragoza	1
\.


--
-- TOC entry 3749 (class 0 OID 202356)
-- Dependencies: 215
-- Data for Name: orientacionsexual; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orientacionsexual (id, nombre) FROM stdin;
1	Asexual
2	Bisexual
3	Heterosexual
4	Homosexual
5	Lesbiana
6	Prefiere no responder
7	Otra
\.


--
-- TOC entry 3743 (class 0 OID 202300)
-- Dependencies: 209
-- Data for Name: pais; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pais (id, codigo, nombre) FROM stdin;
1	AND	Andorra
2	ARE	Emiratos Árabes Unidos
3	AFG	Afganistán
4	ATG	Antigua y Barbuda
5	AIA	Anguila
6	ALB	Albania
7	ARM	Armenia
8	ANT	Antillas Neerlandesas
9	AGO	Angola
10	ATA	Antártida
11	ARG	Argentina
12	ASM	Samoa Americana
13	AUT	Austria
14	AUS	Australia
15	ABW	Aruba
16	ALA	Islas Åland
17	AZE	Azerbaiyán
18	BIH	Bosnia y Herzegovina
19	BRB	Barbados
20	BGD	Bangladesh
21	BEL	Bélgica
22	BFA	Burkina Faso
23	BGR	Bulgaria
24	BHR	Bahréin
25	BDI	Burundi
26	BEN	Benin
27	BLM	San Bartolomé
28	BMU	Bermudas
29	BRN	Brunéi
30	BOL	Bolivia
31	BRA	Brasil
32	BHS	Bahamas
33	BTN	Bhután
34	BVT	Isla Bouvet
35	BWA	Botsuana
36	BLR	Belarús
37	BLZ	Belice
38	CAN	Canadá
39	CCK	Islas Cocos
40	CAF	República Centro-Africana
41	COG	Congo
42	CHE	Suiza
43	CIV	Costa de Marfil
44	COK	Islas Cook
45	CHL	Chile
46	CMR	Camerún
47	CHN	China
48	COL	Colombia
49	CRI	Costa Rica
50	CUB	Cuba
51	CPV	Cabo Verde
52	CXR	Islas Christmas
53	CYP	Chipre
54	CZE	República Checa
55	DEU	Alemania
56	DJI	Yibuti
57	DNK	Dinamarca
58	DMA	Domínica
59	DOM	República Dominicana
60	DZA	Argel
61	ECU	Ecuador
62	EST	Estonia
63	EGY	Egipto
64	ESH	Sahara Occidental
65	ERI	Eritrea
66	ESP	España
67	ETH	Etiopía
68	FIN	Finlandia
69	FJI	Fiji
70	KLK	Islas Malvinas
71	FSM	Micronesia
72	FRO	Islas Faroe
73	FRA	Francia
74	GAB	Gabón
75	GBR	Reino Unido
76	GRD	Granada
77	GEO	Georgia
78	GUF	Guayana Francesa
79	GGY	Guernsey
80	GHA	Ghana
81	GIB	Gibraltar
82	GRL	Groenlandia
83	GMB	Gambia
84	GIN	Guinea
85	GLP	Guadalupe
86	GNQ	Guinea Ecuatorial
87	GRC	Grecia
88	SGS	Georgia del Sur e Islas Sandwich del Sur
89	GTM	Guatemala
90	GUM	Guam
91	GNB	Guinea-Bissau
92	GUY	Guayana
93	HKG	Hong Kong
94	HMD	Islas Heard y McDonald
95	HND	Honduras
96	HRV	Croacia
97	HTI	Haití
98	HUN	Hungría
99	IDN	Indonesia
100	IRL	Irlanda
101	ISR	Israel
102	IMN	Isla de Man
103	IND	India
104	IOT	Territorio Británico del Océano Índico
105	IRQ	Irak
106	IRN	Irán
107	ISL	Islandia
108	ITA	Italia
109	JEY	Jersey
110	JAM	Jamaica
111	JOR	Jordania
112	JPN	Japón
113	KEN	Kenia
114	KGZ	Kirguistán
115	KHM	Camboya
116	KIR	Kiribati
117	COM	Comoros
118	KNA	San Cristóbal y Nieves
119	PRK	Corea del Norte
120	KOR	Corea del Sur
121	KWT	Kuwait
122	CYM	Islas Caimán
123	KAZ	Kazajstán
124	LAO	Laos
125	LBN	Líbano
126	LCA	Santa Lucía
127	LIE	Liechtenstein
128	LKA	Sri Lanka
129	LBR	Liberia
130	LSO	Lesotho
131	LTU	Lituania
132	LUX	Luxemburgo
133	LVA	Letonia
134	LBY	Libia
135	MAR	Marruecos
136	MCO	Mónaco
137	MDA	Moldova
138	MNE	Montenegro
139	MDG	Madagascar
140	MHL	Islas Marshall
141	MKD	Macedonia
142	MLI	Mali
143	MMR	Myanmar
144	MNG	Mongolia
145	MAC	Macao
146	MTQ	Martinica
147	MRT	Mauritania
148	MSR	Montserrat
149	MLT	Malta
150	MUS	Mauricio
151	MDV	Maldivas
152	MWI	Malawi
153	MEX	México
154	MYS	Malasia
155	MOZ	Mozambique
156	NAM	Namibia
157	NCL	Nueva Caledonia
158	NER	Níger
159	NFK	Islas Norkfolk
160	NGA	Nigeria
161	NIC	Nicaragua
162	NLD	Países Bajos
163	NOR	Noruega
164	NPL	Nepal
165	NRU	Nauru
166	NIU	Niue
167	NZL	Nueva Zelanda
168	OMN	Omán
169	PAN	Panamá
170	PER	Perú
171	PYF	Polinesia Francesa
172	PNG	Papúa Nueva Guinea
173	PHL	Filipinas
174	PAK	Pakistán
175	POL	Polonia
176	SPM	San Pedro y Miquelón
177	PCN	Islas Pitcairn
178	PRI	Puerto Rico
179	PSE	Palestina
180	PRT	Portugal
181	PLW	Islas Palaos
182	PRY	Paraguay
183	QAT	Qatar
184	REU	Reunión
185	ROU	Rumanía
186	SRB	Serbia y Montenegro
187	RUS	Rusia
188	RWA	Ruanda
189	SAU	Arabia Saudita
190	SLB	Islas Salomón
191	SYC	Seychelles
192	SDN	Sudán
193	SWE	Suecia
194	SGP	Singapur
195	SHN	Santa Elena
196	SVN	Eslovenia
197	SJM	Islas Svalbard y Jan Mayen
198	SVK	Eslovaquia
199	SLE	Sierra Leona
200	SMR	San Marino
201	SEN	Senegal
202	SOM	Somalia
203	SUR	Surinam
204	STP	Santo Tomé y Príncipe
205	SLV	El Salvador
206	SYR	Siria
207	SWZ	Suazilandia
208	TCA	Islas Turcas y Caicos
209	TCD	Chad
210	ATF	Territorios Australes Franceses
211	TGO	Togo
212	THA	Tailandia
213	TZA	Tanzania
214	TJK	Tayikistán
215	TKL	Tokelau
216	TLS	Timor-Leste
217	TKM	Turkmenistán
218	TUN	Túnez
219	TON	Tonga
220	TUR	Turquía
221	TTO	Trinidad y Tobago
222	TUV	Tuvalu
223	TWN	Taiwán
224	UKR	Ucrania
225	UGA	Uganda
226	USA	Estados Unidos de América
227	URY	Uruguay
228	UZB	Uzbekistán
229	VAT	Ciudad del Vaticano
230	VCT	San Vicente y las Granadinas
231	VEN	Venezuela
232	VGB	Islas Vírgenes Británicas
233	VIR	Islas Vírgenes de los Estados Unidos de América
234	VNM	Vietnam
235	VUT	Vanuatu
236	WLF	Wallis y Futuna
237	WSM	Samoa
238	YEM	Yemen
239	MYT	Mayotte
240	ZAF	Sudáfrica
\.


--
-- TOC entry 3755 (class 0 OID 202409)
-- Dependencies: 221
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persona (id, primernombre, segundonombre, primerapellido, segundoapellido, numerodocumento, fechanacimiento, idtipoidentificacion, idsexo, idetnia, idpaisnacimiento) FROM stdin;
14	Pedro	Antonio	Zapata	Alara	836703615	1970-02-23	1	1	6	1
15	Jaime	Camilo	Zapata	Angulo	7979978	1981-02-23	2	2	6	1
16	Nancy		Zapata		664566565	1985-02-23	2	3	6	1
\.


--
-- TOC entry 3788 (class 0 OID 202783)
-- Dependencies: 254
-- Data for Name: profesional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.profesional (idpersona, idcargo) FROM stdin;
\.


--
-- TOC entry 3808 (class 0 OID 203044)
-- Dependencies: 274
-- Data for Name: profesionalasignacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.profesionalasignacion (idasignacion, idprofesional) FROM stdin;
\.


--
-- TOC entry 3807 (class 0 OID 203029)
-- Dependencies: 273
-- Data for Name: profesionalgrupoprofesional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.profesionalgrupoprofesional (idgrupoprofesional, idprofesional) FROM stdin;
\.


--
-- TOC entry 3771 (class 0 OID 202620)
-- Dependencies: 237
-- Data for Name: programa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.programa (id, nombre, codigo, descripcion) FROM stdin;
1	Administración De Empresas	ADM-EMP	Pregrado
2	Administración En Salud	ADM-SAL	Pregrado
3	Administración En Salud: Énfasis En Gestión Sanitaria y Ambiental	ADM-SAL-GSA	Especialización
4	Administración En Salud: Gestión De Servicios De Salud	ADM-SAL-GSS	Especialización
5	Antropología	ANTRO	Pregrado
6	Archivística	ARCHIV	Pregrado
7	Arte Dramático	ARTE-DRAM	Pregrado
8	Artes Plásticas	ARTES-PLAS	Pregrado
9	Astronomía	ASTRO	Pregrado
10	Bibliotecología	BIBLIO	Pregrado
11	Bioingeniería	BIOING	Pregrado
12	Biología	BIOLOGIA	Pregrado
13	Ciencia Política	CIENC-POL	Pregrado
14	Ciencias Culinarias	CIENC-CULIN	Pregrado
15	Comunicación Audiovisual y Multimedial	COM-AUD-MULT	Pregrado
16	Comunicación Social Periodismo	COM-SOC-PER	Pregrado
17	Comunicaciones	COMUNICACIONES	Pregrado
18	Contaduría Pública	CONTA-PUB	Pregrado
19	Derecho	DERECHO	Pregrado
20	Desarrollo Territorial	DES-TERR	Pregrado
21	Ecología De Zonas Costeras	ECO-ZONAS-COST	Pregrado
22	Economía	ECONOMIA	Pregrado
23	Enfermería	ENFERMERIA	Pregrado
24	Entrenamiento Deportivo	ENTREN-DEP	Pregrado
25	Estadística	ESTADISTICA	Pregrado
26	Filología Hispánica	FILOL-HISP	Pregrado
27	Filosofía	FILOSOFIA	Pregrado
28	Física	FISICA	Pregrado
29	Gerencia En Sistemas De Información En Salud	GER-SIS-INF-SAL	Especialización
30	Gestión Cultural	GEST-CULT	Pregrado
31	Gestión En Ecología y Turismo	GEST-ECO-TUR	Pregrado
32	Historia	HISTORIA	Pregrado
33	Ingeniería Aeroespacial	ING-AEROESP	Pregrado
34	Ingeniería Agroindustrial	ING-AGROIND	Pregrado
35	Ingeniería Agropecuaria	ING-AGROPEC	Pregrado
36	Ingeniería Ambiental	ING-AMB	Pregrado
37	Ingeniería Ambiental Virtual	ING-AMB-VIRT	Pregrado
38	Ingeniería Bioquímica	ING-BIOQ	Pregrado
39	Ingeniería Civil	ING-CIVIL	Pregrado
40	Ingeniería De Alimentos	ING-ALIM	Pregrado
41	Ingeniería De Materiales	ING-MAT	Pregrado
42	Ingeniería De Sistemas	ING-SIST	Pregrado
43	Ingeniería De Sistemas Virtual	ING-SIST-VIRT	Pregrado
44	Ingeniería De Telecomunicaciones	ING-TELEC	Pregrado
45	Ingeniería De Telecomunicaciones Virtual	ING-TELEC-VIRT	Pregrado
46	Ingeniería Eléctrica	ING-ELEC	Pregrado
47	Ingeniería Electrónica	ING-ELECTRON	Pregrado
48	Ingeniería Energética	ING-ENERG	Pregrado
49	Ingeniería Industrial	ING-IND	Pregrado
50	Ingeniería Industrial Virtual	ING-IND-VIRT	Pregrado
51	Ingeniería Mecánica	ING-MEC	Pregrado
52	Ingeniería Oceanográfica	ING-OCEAN	Pregrado
53	Ingeniería Química	ING-QUIM	Pregrado
54	Ingeniería Sanitaria	ING-SANIT	Pregrado
55	Ingeniería Urbana	ING-URB	Pregrado
56	Instrumentación Quirúrgica	INSTRUM-QUIR	Pregrado
57	Letras: Filología Hispánica	LETRAS-FILOL	Pregrado
58	Lic En Educac Básica Énfasis Artístico Música	LIC-EB-ART-MUS	Pregrado
59	Lic. En Educ Básica Énfasis En Humanidades Lengua Castellana	LIC-EB-HUM-LC	Pregrado
60	Lic. En Educ. Básica Énfasis En Ccias Naturales y Educ Amb.	LIC-EB-CN-EA	Pregrado
61	Licenciatura Educación Básica Énfasis En Ciencias Sociales	LIC-EB-CS	Pregrado
62	Licenciatura Educación Básica Énfasis Matemáticas	LIC-EB-MAT	Pregrado
63	Licenciatura En Artes Escénicas	LIC-ARTES-ESC	Pregrado
64	Licenciatura En Artes Plásticas	LIC-ARTES-PLAS	Pregrado
65	Licenciatura En Ciencias Naturales	LIC-CN	Pregrado
66	Licenciatura En Ciencias Sociales	LIC-CS	Pregrado
67	Licenciatura En Danza	LIC-DANZA	Pregrado
68	Licenciatura En Educación Básica En Danza	LIC-EB-DANZA	Pregrado
69	Licenciatura En Educación Básica Primaria	LIC-EB-PRIM	Pregrado
70	Licenciatura En Educación Español y Literatura	LIC-ESP-LIT	Pregrado
71	Licenciatura En Educación Especial	LIC-ED-ESP	Pregrado
72	Licenciatura En Educación Física	LIC-ED-FIS	Pregrado
73	Licenciatura En Educación Infantil	LIC-ED-INF	Pregrado
74	Licenciatura En Educación Primaria	LIC-ED-PRIM	Pregrado
75	Licenciatura En Educación: Artes Plásticas	LIC-ED-ART-PLAS	Pregrado
76	Licenciatura En Filosofía	LIC-FILOS	Pregrado
77	Licenciatura En Física	LIC-FIS	Pregrado
78	Licenciatura En Lenguas Extranjeras	LIC-LENG-EXT	Pregrado
79	Licenciatura En Literatura y Lengua Castellana	LIC-LIT-LC	Pregrado
80	Licenciatura En Matemáticas	LIC-MAT	Pregrado
81	Licenciatura En Matemáticas y Física	LIC-MAT-FIS	Pregrado
82	Licenciatura En Música	LIC-MUS	Pregrado
83	Licenciatura En Pedagogía De La Madre Tierra	LIC-PED-MT	Pregrado
84	Licenciatura En Pedagogía Infantil	LIC-PED-INF	Pregrado
85	Licenciatura En Teatro	LIC-TEATRO	Pregrado
86	Licenciatura Lenguas Extranjeras Con Énfasis Inglés y Francés	LIC-LE-ING-FRA	Pregrado
87	Licenciatura Música Instrumentos: Piano	LIC-MUS-PIANO	Pregrado
88	Matemáticas	MATEMATICAS	Pregrado
89	Medicina	MEDICINA	Pregrado
90	Medicina Veterinaria	MED-VET	Pregrado
91	Microbiología Industrial y Ambiental	MICRO-IND-AMB	Pregrado
92	Microbiología y Bioanálisis	MICRO-BIOANA	Pregrado
93	Música	MUSICA	Pregrado
94	Música-Canto	MUSICA-CANTO	Pregrado
95	Música-Instrumento	MUSICA-INSTR	Pregrado
96	Nutrición y Dietética	NUTRI-DIET	Pregrado
97	Oceanografía	OCEANOGRAFIA	Pregrado
98	Odontología	ODONTOLOGIA	Pregrado
99	Pedagogía	PEDAGOGIA	Pregrado
100	Periodismo	PERIODISMO	Pregrado
101	Psicología	PSICOLOGIA	Pregrado
102	Química	QUIMICA	Pregrado
103	Química Farmacéutica	QUIM-FARM	Pregrado
104	Sociología	SOCIOLOGIA	Pregrado
105	Técnica Profesional En Atención Prehospitalaria	TEC-PROF-ATENC-PREHOSP	Técnica Profesional
106	Técnico Profesional Agropecuario	TEC-PROF-AGROPEC	Técnica Profesional
107	Tecnología Agroindustrial	TEC-AGROIND	Tecnológica
108	Tecnología Biomédica	TEC-BIOMED	Tecnológica
109	Tecnología De Alimentos	TEC-ALIM	Tecnológica
110	Tecnología En Administración De Servicios De Salud	TEC-ADM-SERV-SAL	Tecnológica
111	Tecnología En Archivística	TEC-ARCHIV	Tecnológica
112	Tecnología En Atención Prehospitalaria	TEC-ATENC-PREHOSP	Tecnológica
113	Tecnología En Ecología y Turismo	TEC-ECO-TUR	Tecnológica
114	Tecnología En Gestión De Insumos Agropecuarios	TEC-GEST-INS-AGRO	Tecnológica
115	Tecnología En Gestión De Servicios De Salud	TEC-GEST-SERV-SAL	Tecnológica
116	Tecnología En Regencia De Farmacia	TEC-REG-FARM	Tecnológica
117	Tecnología En Saneamiento Ambiental	TEC-SAN-AMB	Tecnológica
118	Tecnología Química	TEC-QUIM	Tecnológica
119	Trabajo Social	TRAB-SOCIAL	Pregrado
120	Traducción Inglés Francés Español	TRAD-ING-FRA-ESP	Pregrado
121	Zootecnia	ZOOTECNIA	Pregrado
122	Otro-a	OTRO	Otro
123	Desconocido-a	DESCONOCIDO	Desconocido
124	N/A	NA	No aplica
\.


--
-- TOC entry 3772 (class 0 OID 202629)
-- Dependencies: 238
-- Data for Name: programacaso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.programacaso (idcaso, idprograma, id) FROM stdin;
\.


--
-- TOC entry 3789 (class 0 OID 202813)
-- Dependencies: 255
-- Data for Name: regimen; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.regimen (id, nombre) FROM stdin;
1	Contributivo
2	Subsidiado
3	Especial
4	No afiliado
\.


--
-- TOC entry 3779 (class 0 OID 202681)
-- Dependencies: 245
-- Data for Name: remision; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.remision (id, idremitente, idcargo, iddependencia, idfacultad, idcampus, fecha) FROM stdin;
\.


--
-- TOC entry 3800 (class 0 OID 202947)
-- Dependencies: 266
-- Data for Name: remitente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.remitente (id, nombre) FROM stdin;
\.


--
-- TOC entry 3783 (class 0 OID 202742)
-- Dependencies: 249
-- Data for Name: resultadocontactotelefonico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resultadocontactotelefonico (id, nombre) FROM stdin;
1	Contesta y se concerta cita
2	Contesta y no se concerta cita
3	Contesta y desiste
4	No contesta
5	Número errado
6	N/A
\.


--
-- TOC entry 3773 (class 0 OID 202644)
-- Dependencies: 239
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rol (id, nombre) FROM stdin;
1	Admin
2	Coordinador
3	Profesional
4	Revisor
5	Usuario
\.


--
-- TOC entry 3745 (class 0 OID 202316)
-- Dependencies: 211
-- Data for Name: sexo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sexo (id, codigo, nombre) FROM stdin;
1	M	Mujer
2	H	Hombre
3	NB	No binario
4	PR	Prefiere no responder
\.


--
-- TOC entry 3815 (class 0 OID 203075)
-- Dependencies: 281
-- Data for Name: solicitud_acompanamiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.solicitud_acompanamiento (id, codigo, estado, fecha_creacion, rem_cargo, rem_primer_apellido, rem_primer_nombre, rem_segundo_apellido, rem_segundo_nombre, sol_campus, sol_celular, sol_celular_alterno, sol_correo_institucional, sol_correo_personal, sol_dependencia, sol_edad, sol_facultad, sol_identidad_genero, sol_numero_documento, sol_primer_apellido, sol_primer_nombre, sol_segundo_apellido, sol_segundo_nombre, sol_tipo_documento, tipo_reporte, tipo_solicitud) FROM stdin;
\.


--
-- TOC entry 3781 (class 0 OID 202714)
-- Dependencies: 247
-- Data for Name: solicitudatencion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.solicitudatencion (id, idcaso, idremision, fecha, idtiposolicitud, idestadosolicitud) FROM stdin;
1	7	\N	2026-02-23 13:30:34.251933	1	1
2	8	\N	2026-02-23 13:35:00.54376	1	1
3	9	\N	2026-02-23 13:38:19.933905	1	1
4	10	\N	2026-02-23 13:42:28.72649	1	1
5	11	\N	2026-02-23 13:44:01.816932	1	1
6	12	\N	2026-02-23 15:37:06.721858	1	1
\.


--
-- TOC entry 3758 (class 0 OID 202469)
-- Dependencies: 224
-- Data for Name: telefonopersona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.telefonopersona (idpersona, idtipo, telefono, descripcion, id) FROM stdin;
14	1	5047270473	Celular principal	13
14	3	6227354389	Celular alterno	14
15	1	3008262910	Celular principal	15
15	3	3506280928	Celular alterno	16
16	1	3020000003	Celular principal	17
16	3	3120000002	Celular alterno	18
\.


--
-- TOC entry 3744 (class 0 OID 202309)
-- Dependencies: 210
-- Data for Name: tipocorreo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipocorreo (id, nombre) FROM stdin;
1	Correo Institucional
2	Correo Personal
\.


--
-- TOC entry 3746 (class 0 OID 202327)
-- Dependencies: 212
-- Data for Name: tipoidentificacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipoidentificacion (id, codigo, nombre) FROM stdin;
1	TI	Tarjeta de Identidad
2	CC	Cédula de Ciudadanía
3	CE	Cédula de Extranjería
4	PA	Pasaporte
5	PPT	PPT
6	NUIP	NUIP
7	OT	Otro
\.


--
-- TOC entry 3770 (class 0 OID 202613)
-- Dependencies: 236
-- Data for Name: tiposolicitud; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tiposolicitud (id, nombre) FROM stdin;
2	Indirecta
1	Directa
\.


--
-- TOC entry 3753 (class 0 OID 202401)
-- Dependencies: 219
-- Data for Name: tipotelefono; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipotelefono (id, nombre) FROM stdin;
1	Celular
2	Teléfono Fijo
3	WhatsApp
4	Oficina
\.


--
-- TOC entry 3775 (class 0 OID 202654)
-- Dependencies: 241
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, email, password, nombre, idrol, activo, fechacreacion, fechaactualizacion) FROM stdin;
1	admin@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Laura Estella Pineda Corcho	1	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
2	admin2@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Isabel Cristina Jaramillo González	1	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
3	coordinador.atencion@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Laura Estella Pineda Corcho	2	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
4	coordinador.prevencion@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Isabel Cristina Jaramillo González	2	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
5	andrea.salazar@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Andrea Salazar Morales	3	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
6	carmen.sanchez@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Carmen Andrea Sánchez Hernández	3	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
7	diana.sanchez@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Diana Cristina Sánchez Pérez	3	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
8	manuela.morales@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Manuela Morales Duque	3	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
9	laura.valencia@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Laura Valencia Ruíz	3	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
10	lina.rodas@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Lina María Rodas	3	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
11	revisor1@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Revisor Administrativo Uno	4	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
12	revisor2@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Revisor Administrativo Dos	4	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
13	usuario.estudiante@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	María Camila Gómez Pérez	5	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
14	usuario.docente@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Carlos Alberto Ramírez López	5	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
15	usuario.admin@udea.edu.co	$2a$10$rjll9Epf8UWi7HeH5kmBaulRKTHMP7TZ8/zWEmpadDn7SFdXpjKru	Sofía Martínez Torres	5	t	2026-02-16 01:03:33.253986	2026-02-16 02:49:20.41
16	pedro@pedro.com	$2a$10$8SlbywRWB05lfVt8PukIlOTAGm08KnzZlJ.OV.7t7cPq7SYC4SjCa	Pedro	1	t	2026-02-22 18:33:32.132323	2026-02-22 18:33:32.132323
17	prueba@correo.com	$2a$10$uLPIbmaIaLgpwD0.euvOrOLhjdp/a9VSOzOMRMRjlW6vDQNe6MAna	Prueba	1	f	2026-02-23 15:28:57.464677	2026-02-23 15:28:57.464677
\.


--
-- TOC entry 3762 (class 0 OID 202507)
-- Dependencies: 228
-- Data for Name: vinculoagresorvictima; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vinculoagresorvictima (id, nombre) FROM stdin;
1	Pareja/expareja
2	Familiar
3	Compañeros de estudio
4	Compañeros de trabajo
5	Docente
6	Estudiante
7	Jefe
8	Comparten lugar de trabajo
9	Comparten lugar de estudio
10	Amigo/amiga
11	Conocido
12	Desconocido
13	Otro
\.


--
-- TOC entry 3763 (class 0 OID 202514)
-- Dependencies: 229
-- Data for Name: vinculoudea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vinculoudea (id, nombre) FROM stdin;
1	Estudiante de pregrado
2	Estudiante de posgrado
3	Estudiante de tecnología
4	Estudiante de técnica
5	Docente vinculado
6	Docente ocasional
7	Docente cátedra
8	Docente cátedra 50
9	Personal no docente
10	Prestador de servicios
11	Otro tipo de vínculo: Egresado
12	Otro tipo de vínculo: Jubilado
13	Otro tipo de vínculo: Pensionado
14	Otro tipo de vínculo: Contratista
15	Externo
16	Otro
17	N/A
\.


--
-- TOC entry 3845 (class 0 OID 0)
-- Dependencies: 258
-- Name: archivoconsentimiento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.archivoconsentimiento_id_seq', 1, false);


--
-- TOC entry 3846 (class 0 OID 0)
-- Dependencies: 252
-- Name: asignacion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asignacion_id_seq', 1, false);


--
-- TOC entry 3847 (class 0 OID 0)
-- Dependencies: 276
-- Name: campus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.campus_id_seq', 15, false);


--
-- TOC entry 3848 (class 0 OID 0)
-- Dependencies: 230
-- Name: caso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.caso_id_seq', 12, true);


--
-- TOC entry 3849 (class 0 OID 0)
-- Dependencies: 270
-- Name: cita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cita_id_seq', 1, false);


--
-- TOC entry 3850 (class 0 OID 0)
-- Dependencies: 250
-- Name: contactotelefonico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contactotelefonico_id_seq', 1, false);


--
-- TOC entry 3851 (class 0 OID 0)
-- Dependencies: 260
-- Name: correopersona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.correopersona_id_seq', 18, true);


--
-- TOC entry 3852 (class 0 OID 0)
-- Dependencies: 277
-- Name: dependencia_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dependencia_id_seq', 23, false);


--
-- TOC entry 3853 (class 0 OID 0)
-- Dependencies: 261
-- Name: discapacidadpersona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.discapacidadpersona_id_seq', 1, false);


--
-- TOC entry 3854 (class 0 OID 0)
-- Dependencies: 278
-- Name: facultadescuelainstituto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.facultadescuelainstituto_id_seq', 23, false);


--
-- TOC entry 3855 (class 0 OID 0)
-- Dependencies: 262
-- Name: modalidadviolenciacaso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.modalidadviolenciacaso_id_seq', 1, false);


--
-- TOC entry 3856 (class 0 OID 0)
-- Dependencies: 263
-- Name: modalidadviolenciasexualcaso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.modalidadviolenciasexualcaso_id_seq', 1, false);


--
-- TOC entry 3857 (class 0 OID 0)
-- Dependencies: 220
-- Name: persona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.persona_id_seq', 16, true);


--
-- TOC entry 3858 (class 0 OID 0)
-- Dependencies: 264
-- Name: programacaso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.programacaso_id_seq', 1, false);


--
-- TOC entry 3859 (class 0 OID 0)
-- Dependencies: 243
-- Name: remision_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.remision_id_seq', 1, false);


--
-- TOC entry 3860 (class 0 OID 0)
-- Dependencies: 244
-- Name: remision_idremitente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.remision_idremitente_seq', 1, false);


--
-- TOC entry 3861 (class 0 OID 0)
-- Dependencies: 265
-- Name: remitente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.remitente_id_seq', 1, false);


--
-- TOC entry 3862 (class 0 OID 0)
-- Dependencies: 280
-- Name: solicitud_acompanamiento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.solicitud_acompanamiento_id_seq', 1, false);


--
-- TOC entry 3863 (class 0 OID 0)
-- Dependencies: 246
-- Name: solicitudatencion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.solicitudatencion_id_seq', 6, true);


--
-- TOC entry 3864 (class 0 OID 0)
-- Dependencies: 267
-- Name: telefonopersona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.telefonopersona_id_seq', 18, true);


--
-- TOC entry 3865 (class 0 OID 0)
-- Dependencies: 279
-- Name: tipoidentificacion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipoidentificacion_id_seq', 8, false);


--
-- TOC entry 3866 (class 0 OID 0)
-- Dependencies: 275
-- Name: tiposolicitud_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tiposolicitud_id_seq', 16, true);


--
-- TOC entry 3867 (class 0 OID 0)
-- Dependencies: 240
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 17, true);


--
-- TOC entry 3533 (class 2606 OID 202855)
-- Name: archivoconsentimiento archivoconsentimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivoconsentimiento
    ADD CONSTRAINT archivoconsentimiento_pkey PRIMARY KEY (id);


--
-- TOC entry 3523 (class 2606 OID 202777)
-- Name: asignacion asignacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asignacion
    ADD CONSTRAINT asignacion_pkey PRIMARY KEY (id);


--
-- TOC entry 3531 (class 2606 OID 202831)
-- Name: atencion atencion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atencion
    ADD CONSTRAINT atencion_pkey PRIMARY KEY (id);


--
-- TOC entry 3469 (class 2606 OID 202492)
-- Name: campus campus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campus
    ADD CONSTRAINT campus_pkey PRIMARY KEY (id);


--
-- TOC entry 3511 (class 2606 OID 202678)
-- Name: cargo cargo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT cargo_pkey PRIMARY KEY (id);


--
-- TOC entry 3479 (class 2606 OID 203021)
-- Name: caso caso_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_codigo_key UNIQUE (codigo);


--
-- TOC entry 3481 (class 2606 OID 202529)
-- Name: caso caso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_pkey PRIMARY KEY (id);


--
-- TOC entry 3541 (class 2606 OID 203004)
-- Name: cita cita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_pkey PRIMARY KEY (id);


--
-- TOC entry 3521 (class 2606 OID 202755)
-- Name: contactotelefonico contactotelefonico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactotelefonico
    ADD CONSTRAINT contactotelefonico_pkey PRIMARY KEY (id);


--
-- TOC entry 3461 (class 2606 OID 202871)
-- Name: correopersona correopersona_idpersona_tipo_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.correopersona
    ADD CONSTRAINT correopersona_idpersona_tipo_fkey UNIQUE (idpersona, idtipo);


--
-- TOC entry 3439 (class 2606 OID 202373)
-- Name: departamento departamento_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_codigo_key UNIQUE (codigo);


--
-- TOC entry 3441 (class 2606 OID 202375)
-- Name: departamento departamento_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_nombre_key UNIQUE (nombre);


--
-- TOC entry 3443 (class 2606 OID 202371)
-- Name: departamento departamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_pkey PRIMARY KEY (id);


--
-- TOC entry 3471 (class 2606 OID 202499)
-- Name: dependencia dependencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dependencia
    ADD CONSTRAINT dependencia_pkey PRIMARY KEY (id);


--
-- TOC entry 3451 (class 2606 OID 202400)
-- Name: discapacidad discapacidad_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discapacidad
    ADD CONSTRAINT discapacidad_nombre_key UNIQUE (nombre);


--
-- TOC entry 3453 (class 2606 OID 202398)
-- Name: discapacidad discapacidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discapacidad
    ADD CONSTRAINT discapacidad_pkey PRIMARY KEY (id);


--
-- TOC entry 3459 (class 2606 OID 202441)
-- Name: discapacidadpersona discapacidadpersona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discapacidadpersona
    ADD CONSTRAINT discapacidadpersona_pkey PRIMARY KEY (idpersona, iddiscapacidad);


--
-- TOC entry 3529 (class 2606 OID 202826)
-- Name: eps eps_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eps
    ADD CONSTRAINT eps_pkey PRIMARY KEY (id);


--
-- TOC entry 3539 (class 2606 OID 202997)
-- Name: estadocita estadocita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estadocita
    ADD CONSTRAINT estadocita_pkey PRIMARY KEY (id);


--
-- TOC entry 3537 (class 2606 OID 202990)
-- Name: estadosolicitud estadosolicitud_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estadosolicitud
    ADD CONSTRAINT estadosolicitud_pkey PRIMARY KEY (id);


--
-- TOC entry 3427 (class 2606 OID 202346)
-- Name: etnia etnia_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.etnia
    ADD CONSTRAINT etnia_nombre_key UNIQUE (nombre);


--
-- TOC entry 3429 (class 2606 OID 202344)
-- Name: etnia etnia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.etnia
    ADD CONSTRAINT etnia_pkey PRIMARY KEY (id);


--
-- TOC entry 3473 (class 2606 OID 202506)
-- Name: facultadescuelainstituto facultadescuelainstituto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facultadescuelainstituto
    ADD CONSTRAINT facultadescuelainstituto_pkey PRIMARY KEY (id);


--
-- TOC entry 3543 (class 2606 OID 203028)
-- Name: grupoprofesional grupoprofesional_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupoprofesional
    ADD CONSTRAINT grupoprofesional_pkey PRIMARY KEY (id);


--
-- TOC entry 3431 (class 2606 OID 202355)
-- Name: identidadgenero identidadgenero_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identidadgenero
    ADD CONSTRAINT identidadgenero_nombre_key UNIQUE (nombre);


--
-- TOC entry 3433 (class 2606 OID 202353)
-- Name: identidadgenero identidadgenero_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.identidadgenero
    ADD CONSTRAINT identidadgenero_pkey PRIMARY KEY (id);


--
-- TOC entry 3517 (class 2606 OID 202741)
-- Name: jornada jornada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jornada
    ADD CONSTRAINT jornada_pkey PRIMARY KEY (id);


--
-- TOC entry 3483 (class 2606 OID 202573)
-- Name: modalidadviolencia modalidadviolencia_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolencia
    ADD CONSTRAINT modalidadviolencia_nombre_key UNIQUE (nombre);


--
-- TOC entry 3485 (class 2606 OID 202571)
-- Name: modalidadviolencia modalidadviolencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolencia
    ADD CONSTRAINT modalidadviolencia_pkey PRIMARY KEY (id);


--
-- TOC entry 3491 (class 2606 OID 202896)
-- Name: modalidadviolenciacaso modalidadviolenciacaso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciacaso
    ADD CONSTRAINT modalidadviolenciacaso_pkey PRIMARY KEY (idcaso, idmodalidadviolencia);


--
-- TOC entry 3487 (class 2606 OID 202582)
-- Name: modalidadviolenciasexual modalidadviolenciasexual_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciasexual
    ADD CONSTRAINT modalidadviolenciasexual_nombre_key UNIQUE (nombre);


--
-- TOC entry 3489 (class 2606 OID 202580)
-- Name: modalidadviolenciasexual modalidadviolenciasexual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciasexual
    ADD CONSTRAINT modalidadviolenciasexual_pkey PRIMARY KEY (id);


--
-- TOC entry 3493 (class 2606 OID 202913)
-- Name: modalidadviolenciasexualcaso modalidadviolenciasexualcaso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciasexualcaso
    ADD CONSTRAINT modalidadviolenciasexualcaso_pkey PRIMARY KEY (idcaso, idmodalidadviolencia);


--
-- TOC entry 3445 (class 2606 OID 202384)
-- Name: municipio municipio_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.municipio
    ADD CONSTRAINT municipio_codigo_key UNIQUE (codigo);


--
-- TOC entry 3447 (class 2606 OID 202386)
-- Name: municipio municipio_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.municipio
    ADD CONSTRAINT municipio_nombre_key UNIQUE (nombre);


--
-- TOC entry 3449 (class 2606 OID 202382)
-- Name: municipio municipio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.municipio
    ADD CONSTRAINT municipio_pkey PRIMARY KEY (id);


--
-- TOC entry 3435 (class 2606 OID 202364)
-- Name: orientacionsexual orientacionsexual_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orientacionsexual
    ADD CONSTRAINT orientacionsexual_nombre_key UNIQUE (nombre);


--
-- TOC entry 3437 (class 2606 OID 202362)
-- Name: orientacionsexual orientacionsexual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orientacionsexual
    ADD CONSTRAINT orientacionsexual_pkey PRIMARY KEY (id);


--
-- TOC entry 3409 (class 2606 OID 202308)
-- Name: pais pais_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais
    ADD CONSTRAINT pais_codigo_key UNIQUE (codigo);


--
-- TOC entry 3411 (class 2606 OID 202306)
-- Name: pais pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais
    ADD CONSTRAINT pais_pkey PRIMARY KEY (id);


--
-- TOC entry 3457 (class 2606 OID 202416)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- TOC entry 3525 (class 2606 OID 202787)
-- Name: profesional profesional_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesional
    ADD CONSTRAINT profesional_pkey PRIMARY KEY (idpersona);


--
-- TOC entry 3547 (class 2606 OID 203048)
-- Name: profesionalasignacion profesionalasignacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionalasignacion
    ADD CONSTRAINT profesionalasignacion_pkey PRIMARY KEY (idasignacion, idprofesional);


--
-- TOC entry 3545 (class 2606 OID 203033)
-- Name: profesionalgrupoprofesional profesionalgrupoprofesional_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionalgrupoprofesional
    ADD CONSTRAINT profesionalgrupoprofesional_pkey PRIMARY KEY (idgrupoprofesional, idprofesional);


--
-- TOC entry 3497 (class 2606 OID 202628)
-- Name: programa programa_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programa
    ADD CONSTRAINT programa_codigo_key UNIQUE (codigo);


--
-- TOC entry 3499 (class 2606 OID 202626)
-- Name: programa programa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programa
    ADD CONSTRAINT programa_pkey PRIMARY KEY (id);


--
-- TOC entry 3501 (class 2606 OID 202936)
-- Name: programacaso programacaso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programacaso
    ADD CONSTRAINT programacaso_pkey PRIMARY KEY (idcaso, idprograma);


--
-- TOC entry 3527 (class 2606 OID 202819)
-- Name: regimen regimen_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.regimen
    ADD CONSTRAINT regimen_pkey PRIMARY KEY (id);


--
-- TOC entry 3513 (class 2606 OID 202687)
-- Name: remision remision_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision
    ADD CONSTRAINT remision_pkey PRIMARY KEY (id);


--
-- TOC entry 3535 (class 2606 OID 202952)
-- Name: remitente remitente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remitente
    ADD CONSTRAINT remitente_pkey PRIMARY KEY (id);


--
-- TOC entry 3519 (class 2606 OID 202748)
-- Name: resultadocontactotelefonico resultadocontactotelefonico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resultadocontactotelefonico
    ADD CONSTRAINT resultadocontactotelefonico_pkey PRIMARY KEY (id);


--
-- TOC entry 3503 (class 2606 OID 202652)
-- Name: rol rol_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_nombre_key UNIQUE (nombre);


--
-- TOC entry 3505 (class 2606 OID 202650)
-- Name: rol rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id);


--
-- TOC entry 3415 (class 2606 OID 202324)
-- Name: sexo sexo_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sexo
    ADD CONSTRAINT sexo_codigo_key UNIQUE (codigo);


--
-- TOC entry 3417 (class 2606 OID 202326)
-- Name: sexo sexo_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sexo
    ADD CONSTRAINT sexo_nombre_key UNIQUE (nombre);


--
-- TOC entry 3419 (class 2606 OID 202322)
-- Name: sexo sexo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sexo
    ADD CONSTRAINT sexo_pkey PRIMARY KEY (id);


--
-- TOC entry 3549 (class 2606 OID 203082)
-- Name: solicitud_acompanamiento solicitud_acompanamiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitud_acompanamiento
    ADD CONSTRAINT solicitud_acompanamiento_pkey PRIMARY KEY (id);


--
-- TOC entry 3515 (class 2606 OID 202719)
-- Name: solicitudatencion solicitudatencion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitudatencion
    ADD CONSTRAINT solicitudatencion_pkey PRIMARY KEY (id);


--
-- TOC entry 3465 (class 2606 OID 202962)
-- Name: telefonopersona telefonopersona_idpersona_tipo_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telefonopersona
    ADD CONSTRAINT telefonopersona_idpersona_tipo_fkey UNIQUE (idpersona, idtipo);


--
-- TOC entry 3495 (class 2606 OID 202619)
-- Name: tiposolicitud tipo_solicitud_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tiposolicitud
    ADD CONSTRAINT tipo_solicitud_pkey PRIMARY KEY (id);


--
-- TOC entry 3413 (class 2606 OID 202315)
-- Name: tipocorreo tipocorreo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipocorreo
    ADD CONSTRAINT tipocorreo_pkey PRIMARY KEY (id);


--
-- TOC entry 3421 (class 2606 OID 202335)
-- Name: tipoidentificacion tipoidentificacion_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipoidentificacion
    ADD CONSTRAINT tipoidentificacion_codigo_key UNIQUE (codigo);


--
-- TOC entry 3423 (class 2606 OID 202337)
-- Name: tipoidentificacion tipoidentificacion_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipoidentificacion
    ADD CONSTRAINT tipoidentificacion_nombre_key UNIQUE (nombre);


--
-- TOC entry 3425 (class 2606 OID 202333)
-- Name: tipoidentificacion tipoidentificacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipoidentificacion
    ADD CONSTRAINT tipoidentificacion_pkey PRIMARY KEY (id);


--
-- TOC entry 3455 (class 2606 OID 202407)
-- Name: tipotelefono tipotelefono_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipotelefono
    ADD CONSTRAINT tipotelefono_pkey PRIMARY KEY (id);


--
-- TOC entry 3551 (class 2606 OID 203084)
-- Name: solicitud_acompanamiento uk_eyn1xoy7rgjpdngvwxvxuhrxd; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitud_acompanamiento
    ADD CONSTRAINT uk_eyn1xoy7rgjpdngvwxvxuhrxd UNIQUE (codigo);


--
-- TOC entry 3467 (class 2606 OID 202977)
-- Name: telefonopersona ukecck417lu9vbkt6c5dibcncb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telefonopersona
    ADD CONSTRAINT ukecck417lu9vbkt6c5dibcncb7 UNIQUE (idpersona, idtipo);


--
-- TOC entry 3463 (class 2606 OID 202975)
-- Name: correopersona ukjt7uqh4v45ka0yg4rdyml6j9p; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.correopersona
    ADD CONSTRAINT ukjt7uqh4v45ka0yg4rdyml6j9p UNIQUE (idpersona, idtipo);


--
-- TOC entry 3507 (class 2606 OID 202666)
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- TOC entry 3509 (class 2606 OID 202664)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3475 (class 2606 OID 202513)
-- Name: vinculoagresorvictima vinculoagresorvictima_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vinculoagresorvictima
    ADD CONSTRAINT vinculoagresorvictima_pkey PRIMARY KEY (id);


--
-- TOC entry 3477 (class 2606 OID 202520)
-- Name: vinculoudea vinculoudea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vinculoudea
    ADD CONSTRAINT vinculoudea_pkey PRIMARY KEY (id);


--
-- TOC entry 3597 (class 2606 OID 202856)
-- Name: archivoconsentimiento archivoconsentimiento_idatencion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.archivoconsentimiento
    ADD CONSTRAINT archivoconsentimiento_idatencion_fkey FOREIGN KEY (idatencion) REFERENCES public.atencion(id);


--
-- TOC entry 3590 (class 2606 OID 202778)
-- Name: asignacion asignacion_idsolicitudatencion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asignacion
    ADD CONSTRAINT asignacion_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES public.solicitudatencion(id);


--
-- TOC entry 3593 (class 2606 OID 202832)
-- Name: atencion atencion_idcita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atencion
    ADD CONSTRAINT atencion_idcita_fkey FOREIGN KEY (idcita) REFERENCES public.solicitudatencion(id);


--
-- TOC entry 3594 (class 2606 OID 202842)
-- Name: atencion atencion_ideps_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atencion
    ADD CONSTRAINT atencion_ideps_fkey FOREIGN KEY (ideps) REFERENCES public.eps(id);


--
-- TOC entry 3595 (class 2606 OID 202837)
-- Name: atencion atencion_idregimen_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atencion
    ADD CONSTRAINT atencion_idregimen_fkey FOREIGN KEY (idregimen) REFERENCES public.regimen(id);


--
-- TOC entry 3563 (class 2606 OID 202550)
-- Name: caso caso_idcampus_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_idcampus_fkey FOREIGN KEY (idcampus) REFERENCES public.campus(id);


--
-- TOC entry 3564 (class 2606 OID 202540)
-- Name: caso caso_iddependencia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_iddependencia_fkey FOREIGN KEY (iddependencia) REFERENCES public.dependencia(id);


--
-- TOC entry 3565 (class 2606 OID 202545)
-- Name: caso caso_idfacultad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_idfacultad_fkey FOREIGN KEY (idfacultad) REFERENCES public.facultadescuelainstituto(id);


--
-- TOC entry 3566 (class 2606 OID 202535)
-- Name: caso caso_ididentidadgenero_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_ididentidadgenero_fkey FOREIGN KEY (ididentidadgenero) REFERENCES public.identidadgenero(id);


--
-- TOC entry 3567 (class 2606 OID 202530)
-- Name: caso caso_idorientacionsexual_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_idorientacionsexual_fkey FOREIGN KEY (idorientacionsexual) REFERENCES public.orientacionsexual(id);


--
-- TOC entry 3568 (class 2606 OID 202560)
-- Name: caso caso_idvinculoudea_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_idvinculoudea_fkey FOREIGN KEY (idvinculoudea) REFERENCES public.vinculoudea(id);


--
-- TOC entry 3569 (class 2606 OID 202555)
-- Name: caso caso_idvinvuloagresorvictima_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT caso_idvinvuloagresorvictima_fkey FOREIGN KEY (idvinvuloagresorvictima) REFERENCES public.vinculoagresorvictima(id);


--
-- TOC entry 3598 (class 2606 OID 203010)
-- Name: cita cita_idestadocita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_idestadocita_fkey FOREIGN KEY (idestadocita) REFERENCES public.estadocita(id);


--
-- TOC entry 3599 (class 2606 OID 203005)
-- Name: cita cita_idsolicitudatencion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES public.solicitudatencion(id);


--
-- TOC entry 3587 (class 2606 OID 202761)
-- Name: contactotelefonico contactotelefonico_idjornada_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactotelefonico
    ADD CONSTRAINT contactotelefonico_idjornada_fkey FOREIGN KEY (idjornada) REFERENCES public.jornada(id);


--
-- TOC entry 3588 (class 2606 OID 202766)
-- Name: contactotelefonico contactotelefonico_idresultado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactotelefonico
    ADD CONSTRAINT contactotelefonico_idresultado_fkey FOREIGN KEY (idresultado) REFERENCES public.resultadocontactotelefonico(id);


--
-- TOC entry 3589 (class 2606 OID 202756)
-- Name: contactotelefonico contactotelefonico_idsolicitudatencion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactotelefonico
    ADD CONSTRAINT contactotelefonico_idsolicitudatencion_fkey FOREIGN KEY (idsolicitudatencion) REFERENCES public.solicitudatencion(id);


--
-- TOC entry 3559 (class 2606 OID 202872)
-- Name: correopersona correopersona_idpersona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.correopersona
    ADD CONSTRAINT correopersona_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES public.persona(id);


--
-- TOC entry 3560 (class 2606 OID 202464)
-- Name: correopersona correopersona_idtipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.correopersona
    ADD CONSTRAINT correopersona_idtipo_fkey FOREIGN KEY (idtipo) REFERENCES public.tipocorreo(id);


--
-- TOC entry 3557 (class 2606 OID 202447)
-- Name: discapacidadpersona discapacidadpersona_iddiscapacidad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discapacidadpersona
    ADD CONSTRAINT discapacidadpersona_iddiscapacidad_fkey FOREIGN KEY (iddiscapacidad) REFERENCES public.discapacidad(id);


--
-- TOC entry 3558 (class 2606 OID 202442)
-- Name: discapacidadpersona discapacidadpersona_idpersona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discapacidadpersona
    ADD CONSTRAINT discapacidadpersona_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES public.persona(id);


--
-- TOC entry 3596 (class 2606 OID 203059)
-- Name: atencion fk5xkqo32kqxwppjjb50dcn6cul; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atencion
    ADD CONSTRAINT fk5xkqo32kqxwppjjb50dcn6cul FOREIGN KEY (idsolicitudatencion) REFERENCES public.solicitudatencion(id);


--
-- TOC entry 3570 (class 2606 OID 202978)
-- Name: caso fkpd2kjxmqx2jw3m6t95mb6fdg2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caso
    ADD CONSTRAINT fkpd2kjxmqx2jw3m6t95mb6fdg2 FOREIGN KEY (idpersona) REFERENCES public.persona(id);


--
-- TOC entry 3571 (class 2606 OID 202897)
-- Name: modalidadviolenciacaso modalidadviolenciacaso_idcaso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciacaso
    ADD CONSTRAINT modalidadviolenciacaso_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES public.caso(id);


--
-- TOC entry 3572 (class 2606 OID 202593)
-- Name: modalidadviolenciacaso modalidadviolenciacaso_idmodalidadviolencia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciacaso
    ADD CONSTRAINT modalidadviolenciacaso_idmodalidadviolencia_fkey FOREIGN KEY (idmodalidadviolencia) REFERENCES public.modalidadviolencia(id);


--
-- TOC entry 3573 (class 2606 OID 202914)
-- Name: modalidadviolenciasexualcaso modalidadviolenciasexualcaso_idcaso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciasexualcaso
    ADD CONSTRAINT modalidadviolenciasexualcaso_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES public.caso(id);


--
-- TOC entry 3574 (class 2606 OID 202608)
-- Name: modalidadviolenciasexualcaso modalidadviolenciasexualcaso_idmodalidadviolencia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modalidadviolenciasexualcaso
    ADD CONSTRAINT modalidadviolenciasexualcaso_idmodalidadviolencia_fkey FOREIGN KEY (idmodalidadviolencia) REFERENCES public.modalidadviolenciasexual(id);


--
-- TOC entry 3552 (class 2606 OID 202387)
-- Name: municipio municipio_iddepartamento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.municipio
    ADD CONSTRAINT municipio_iddepartamento_fkey FOREIGN KEY (iddepartamento) REFERENCES public.departamento(id);


--
-- TOC entry 3553 (class 2606 OID 202427)
-- Name: persona persona_idetnia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_idetnia_fkey FOREIGN KEY (idetnia) REFERENCES public.etnia(id);


--
-- TOC entry 3554 (class 2606 OID 202432)
-- Name: persona persona_idpaisnacimiento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_idpaisnacimiento_fkey FOREIGN KEY (idpaisnacimiento) REFERENCES public.pais(id);


--
-- TOC entry 3555 (class 2606 OID 202422)
-- Name: persona persona_idsexo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_idsexo_fkey FOREIGN KEY (idsexo) REFERENCES public.sexo(id);


--
-- TOC entry 3556 (class 2606 OID 202417)
-- Name: persona persona_idtipoidentificacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_idtipoidentificacion_fkey FOREIGN KEY (idtipoidentificacion) REFERENCES public.tipoidentificacion(id);


--
-- TOC entry 3591 (class 2606 OID 202793)
-- Name: profesional profesional_idcargo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesional
    ADD CONSTRAINT profesional_idcargo_fkey FOREIGN KEY (idcargo) REFERENCES public.cargo(id);


--
-- TOC entry 3592 (class 2606 OID 202788)
-- Name: profesional profesional_idpersona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesional
    ADD CONSTRAINT profesional_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES public.persona(id);


--
-- TOC entry 3602 (class 2606 OID 203049)
-- Name: profesionalasignacion profesionalasignacion_idasignacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionalasignacion
    ADD CONSTRAINT profesionalasignacion_idasignacion_fkey FOREIGN KEY (idasignacion) REFERENCES public.asignacion(id);


--
-- TOC entry 3603 (class 2606 OID 203054)
-- Name: profesionalasignacion profesionalasignacion_idprofesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionalasignacion
    ADD CONSTRAINT profesionalasignacion_idprofesional_fkey FOREIGN KEY (idprofesional) REFERENCES public.profesional(idpersona);


--
-- TOC entry 3600 (class 2606 OID 203034)
-- Name: profesionalgrupoprofesional profesionalgrupoprofesional_idgrupoprofesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionalgrupoprofesional
    ADD CONSTRAINT profesionalgrupoprofesional_idgrupoprofesional_fkey FOREIGN KEY (idgrupoprofesional) REFERENCES public.grupoprofesional(id);


--
-- TOC entry 3601 (class 2606 OID 203039)
-- Name: profesionalgrupoprofesional profesionalgrupoprofesional_idprofesional_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesionalgrupoprofesional
    ADD CONSTRAINT profesionalgrupoprofesional_idprofesional_fkey FOREIGN KEY (idprofesional) REFERENCES public.profesional(idpersona);


--
-- TOC entry 3575 (class 2606 OID 202937)
-- Name: programacaso programacaso_idcaso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programacaso
    ADD CONSTRAINT programacaso_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES public.caso(id);


--
-- TOC entry 3576 (class 2606 OID 202639)
-- Name: programacaso programacaso_idprograma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programacaso
    ADD CONSTRAINT programacaso_idprograma_fkey FOREIGN KEY (idprograma) REFERENCES public.programa(id);


--
-- TOC entry 3578 (class 2606 OID 202708)
-- Name: remision remision_idcampus_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision
    ADD CONSTRAINT remision_idcampus_fkey FOREIGN KEY (idcampus) REFERENCES public.campus(id);


--
-- TOC entry 3579 (class 2606 OID 202693)
-- Name: remision remision_idcargo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision
    ADD CONSTRAINT remision_idcargo_fkey FOREIGN KEY (idcargo) REFERENCES public.cargo(id);


--
-- TOC entry 3580 (class 2606 OID 202698)
-- Name: remision remision_iddependencia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision
    ADD CONSTRAINT remision_iddependencia_fkey FOREIGN KEY (iddependencia) REFERENCES public.dependencia(id);


--
-- TOC entry 3581 (class 2606 OID 202703)
-- Name: remision remision_idfacultad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision
    ADD CONSTRAINT remision_idfacultad_fkey FOREIGN KEY (idfacultad) REFERENCES public.facultadescuelainstituto(id);


--
-- TOC entry 3582 (class 2606 OID 202688)
-- Name: remision remision_idremitente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.remision
    ADD CONSTRAINT remision_idremitente_fkey FOREIGN KEY (idremitente) REFERENCES public.persona(id);


--
-- TOC entry 3583 (class 2606 OID 202720)
-- Name: solicitudatencion solicitudatencion_idcaso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitudatencion
    ADD CONSTRAINT solicitudatencion_idcaso_fkey FOREIGN KEY (idcaso) REFERENCES public.caso(id);


--
-- TOC entry 3584 (class 2606 OID 203015)
-- Name: solicitudatencion solicitudatencion_idestadosolicitud_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitudatencion
    ADD CONSTRAINT solicitudatencion_idestadosolicitud_fkey FOREIGN KEY (idestadosolicitud) REFERENCES public.estadosolicitud(id);


--
-- TOC entry 3585 (class 2606 OID 202725)
-- Name: solicitudatencion solicitudatencion_idremision_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitudatencion
    ADD CONSTRAINT solicitudatencion_idremision_fkey FOREIGN KEY (idremision) REFERENCES public.remision(id);


--
-- TOC entry 3586 (class 2606 OID 202730)
-- Name: solicitudatencion solicitudatencion_idtiposolicitud_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solicitudatencion
    ADD CONSTRAINT solicitudatencion_idtiposolicitud_fkey FOREIGN KEY (idtiposolicitud) REFERENCES public.tiposolicitud(id);


--
-- TOC entry 3561 (class 2606 OID 202963)
-- Name: telefonopersona telefonopersona_idpersona_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telefonopersona
    ADD CONSTRAINT telefonopersona_idpersona_fkey FOREIGN KEY (idpersona) REFERENCES public.persona(id);


--
-- TOC entry 3562 (class 2606 OID 202481)
-- Name: telefonopersona telefonopersona_idtipo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telefonopersona
    ADD CONSTRAINT telefonopersona_idtipo_fkey FOREIGN KEY (idtipo) REFERENCES public.tipotelefono(id);


--
-- TOC entry 3577 (class 2606 OID 202667)
-- Name: usuario usuario_idrol_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_idrol_fkey FOREIGN KEY (idrol) REFERENCES public.rol(id);


-- Completed on 2026-02-24 08:32:05

--
-- PostgreSQL database dump complete
--

\unrestrict 6TrKKqxLurTRKFrziCS0zvxuDVFabe7IQJ3XdQsFZEuHY4RXjWr8aLGSL79DBMR

