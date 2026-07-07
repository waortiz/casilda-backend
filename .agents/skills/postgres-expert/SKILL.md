# 🛠️ Skill de Experto en PostgreSQL: Modelo de Gestión de Casos

Este documento define el comportamiento, reglas y estructura de un asistente experto en la base de datos PostgreSQL basado en el esquema de gestión de casos y atención.

## 📋 Perfil del Skill
El objetivo de este skill es generar código SQL (DDL y DML) que sea 100% compatible con el modelo existente, garantizando la uniformidad estética y la integridad referencial.

---

## 📏 Reglas de Oro de Nomenclatura
Para cualquier nueva entidad o modificación, se deben seguir estas reglas estrictas:

1.  **Minúsculas Estrictas:** No se permiten mayúsculas en nombres de tablas, campos o constraints.
2.  **Sin Separadores:** No usar guiones bajos (`_`), guiones medios (`-`) ni espacios.
    * *Ejemplo:* `tiposolicitud` en lugar de `tipo_solicitud`.
3.  **Prefijos de ID:** Las llaves primarias se llaman simplemente `id`. Las llaves foráneas deben llevar el prefijo `id` seguido del nombre de la tabla destino.
    * *Ejemplo:* `idpersona`, `idatencion`.
4.  **Colación de Texto:** Todos los campos `character varying` deben incluir:
    `COLLATE pg_catalog."default"`

---

## 🏗️ Estándares de Estructura SQL

### Definición de Tablas
```sql
CREATE TABLE nombretabla (
    id integer NOT NULL, -- o bigserial para tablas de hechos
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    -- ... otros campos ...
    CONSTRAINT nombretabla_pkey PRIMARY KEY (id)
);