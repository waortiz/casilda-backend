# CASILDA Backend

Backend del sistema CASILDA desarrollado con Spring Boot, Spring Security (JWT) y PostgreSQL.

## URLs principales

- Base API: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/v1/api-docs`

## Requisitos

- Java 21
- Maven 3.9+
- PostgreSQL 12+

## Configuración

Archivo: `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/casilda
    username: postgres
    password: postgres

server:
  port: 8080
  servlet:
    context-path: /api/v1
```

## Compilar y ejecutar

```bash
mvn clean compile
mvn spring-boot:run
```

Si `mvn` no está en `PATH`, usa la ruta instalada localmente (ejemplo en Windows):

```powershell
& "C:\Users\William Ortiz\.maven\maven-3.9.12(1)\bin\mvn.cmd" spring-boot:run
```

## Autenticación JWT

### Login

`POST /auth/login`

Ejemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@udea.edu.co",
    "password": "Casilda2024!"
  }'
```

Con el token devuelto, autoriza en Swagger con:

`Bearer <token>`

## Endpoints principales

### Maestros

- `GET /maestros/sexos`
- `GET /maestros/tipos-identificacion`
- `GET /maestros/etnias`
- `GET /maestros/campus`
- `GET /maestros/facultades`
- `GET /maestros/dependencias`
- `GET /maestros/modalidades-violencia`
- `GET /maestros/programas`

### Formularios

- `POST /quejas`
- `POST /acompanamientos`
- `POST /atenciones`

### Gestión

- `GET /usuarios`
- `POST /usuarios`
- `PUT /usuarios/{id}`
- `PATCH /usuarios/{id}/estado`
- `DELETE /usuarios/{id}`
- `GET /listas`

### Casos y seguimiento

- `GET /casos`
- `GET /casos/sin-asignar`
- `PUT /casos/{codigo}`
- `POST /repartos`
- `GET /asignaciones/mis-casos`
- `GET /acompanamientos/{codigoCaso}/contactos`
- `POST /acompanamientos/{codigoCaso}/contactos`
- `GET /seguimiento/{codigoCaso}`
- `GET /dashboard/revisor`

## Scripts SQL útiles

- `database/insert_usuarios_prueba.sql` (seed de usuarios)
- `database/update_passwords_usuarios_prueba.sql` (actualización de hashes BCrypt)

## Notas importantes

- El proyecto usa `context-path=/api/v1`; no dupliques prefijos (`/api/v1/api/v1/...`).
- Para ejemplos completos de consumo, consultar `EJEMPLOS_ENDPOINTS.md`.
