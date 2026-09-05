---
name: spring-expert
description: Directrices, convenciones y mejores prácticas para el desarrollo del backend Java 21 y Spring Boot 3 en el sistema CASILDA (estilo de código, Lombok, arquitectura en capas, entidades JPA, DTOs, mappers, manejo de excepciones, seguridad, testing y logging).
---

# 🚀 Agente Experto en Java Spring Boot — Backend CASILDA

Este documento define el rol, las directrices arquitectónicas, las convenciones de código y los estándares de desarrollo para el backend del **Sistema CASILDA** (`casilda-backend`), basado en las especificaciones de `java-spring-boot.md` y la arquitectura del proyecto.

---

## 📌 1. Stack Tecnológico

- **Java 21** (LTS)
- **Spring Boot 3.2.1**
- **Maven 3.9.x** — Empaquetado WAR (`api-casilda.war`)
- **PostgreSQL** — JPA / Hibernate (`ddl-auto: update`)
- **Lombok** (`@RequiredArgsConstructor`, `@Getter`, `@Setter`, `@Builder`, `@Slf4j`)
- **Spring Security 6** + **JWT** (`jjwt 0.12.3`)
- **Springdoc OpenAPI 2.3.0** (Swagger / OpenAPI 3)
- **Jakarta Validation** (Bean Validation)
- **JUnit 5** + **Mockito** para pruebas

---

## 📂 2. Estructura de Paquetes

```
co.edu.udea.casilda
├── config/          # Seguridad, JWT, Swagger, CORS, Beans de configuración
├── controller/      # REST Controllers (Endpoints HTTP, validación y OpenAPI)
├── dto/
│   ├── request/     # DTOs de entrada con validaciones Jakarta (@Valid)
│   └── response/    # DTOs de salida inmutables con @Builder
├── exception/       # Excepciones de negocio y GlobalExceptionHandler (@RestControllerAdvice)
├── mapper/          # Mappers (MapStruct o mappers estáticos estrictos)
├── model/
│   ├── entity/      # Entidades JPA mapeadas a PostgreSQL
│   └── enums/       # Enumeraciones del dominio
├── repository/      # Interfaces Spring Data JpaRepository
└── service/         # Lógica de negocio transaccional (@Service)
```

---

## 🎨 3. Formato y Estilo de Código Java

- **Codificación:** UTF-8.
- **Indentación:** 4 espacios (no tabs).
- **Longitud máxima de línea:** 120 caracteres.
- **Separación:** Líneas en blanco entre bloques lógicos de código.
- **Nombres descriptivos:** Clases en `PascalCase`, métodos y variables en `camelCase`, constantes en `UPPER_SNAKE_CASE`.
- **Inmutabilidad y final:**
  - Declarar todos los parámetros de métodos como `final`.
  - Declarar variables locales como `final` siempre que sea posible.
  - Evitar mutar objetos en streams o bucles for-each.
- **Tipado explícito:** Evitar la palabra clave `var`; preferir tipos explícitos para mayor claridad.
- **Constantes:** Prohibido el uso de cadenas o números mágicos; extraerlos a constantes `private static final` o enums.
- **Control de nulos y vacíos:**
  - Validar nulos y colecciones vacías antes de operar sobre ellos.
  - Evitar `Objects.isNull()` / `Objects.nonNull()` para 1 o 2 variables; preferir `variable == null` o `variable != null` por rendimiento.
- **Retornos tempranos:** Aplicar "early return" / "guard clauses" para evitar anidamientos de `if/else`.
- **Condiciones booleanas:** Extraer expresiones lógicas complejas a variables booleanas con nombres descriptivos.
- **Comentarios:** Evitar comentarios obvios o redundantes. Usar comentarios solo para regex complejas, expresiones cron, TODOs o estructura `given/when/then` en tests.
- **Excepciones:** Evitar métodos con la cláusula `throws`; utilizar excepciones no verificadas (`RuntimeException`).
- **Anotación `@Override`:** Obligatoria al implementar o sobrescribir métodos.

---

## 🏷️ 4. Uso de Lombok y Anotaciones

### Lombok
- **`@RequiredArgsConstructor`**: Obligatorio para inyección de dependencias por constructor sobre campos `private final`.
- **`@Slf4j`**: Obligatorio para logging en servicios y componentes.
- **`@Getter` y `@Setter`**: Preferir sobre `@Data` para un control granular de mutabilidad.
- **`@Builder(setterPrefix = "with")`** o `@Builder`: Usar para la construcción fluida de DTOs y objetos complejos.

### Anotaciones de Componentes Spring
- **`@RestController`**: En controladores REST.
- **`@Service`**: En servicios de lógica de negocio.
- **`@Repository`**: En interfaces de repositorio JPA.
- **`@Component`**: Para componentes genéricos o utilitarios.
- **`@Configuration`**: Para clases de configuración.
- **`@ConfigurationProperties`**: Para mapear configuraciones tipadas en lugar de múltiples `@Value` (cuando sean más de 2 propiedades).
- **`@Transactional`**:
  - Aplicar `@Transactional(readOnly = true)` a nivel de clase o métodos de solo lectura en la capa `service`.
  - Aplicar `@Transactional` en métodos que modifican el estado (escritura/actualización/eliminación).
- **`@Validated` / `@Valid`**: Para validar cuerpos de solicitud y parámetros.
- **`@PreAuthorize`**: En controladores para autorización basada en roles (Spring Security).
- **`@Autowired`**: **PROHIBIDO** en código de producción. Usar únicamente inyección por constructor (`@RequiredArgsConstructor`).

---

## 🏛️ 5. Patrones por Capa

### 5.1 Entidades JPA (`model/entity/`)
- Nombres de tabla en **minúsculas continuas sin guiones bajos ni guiones** (ej: `solicitudatencion`, `lugarentrevista`).
- Nombres de columna en `snake_case` (ej: `fecha_creacion`).
- Claves primarias llamadas `id`:
  - `Long` para entidades transaccionales (`GenerationType.IDENTITY`).
  - `Integer` para tablas maestras/catálogos.
- Claves foráneas: `@JoinColumn(name = "id<tabla>")` en minúsculas y `@ManyToOne(fetch = FetchType.LAZY)`.
- Relaciones `@OneToMany`: `fetch = FetchType.LAZY`, `cascade = CascadeType.ALL`, `orphanRemoval = true`, e inicializar colecciones `= new ArrayList<>()`.

```java
package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "solicitudatencion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtiposolicitud", nullable = false)
    private TipoSolicitud tipoSolicitud;

    @Column(name = "motivo_consulta", nullable = false)
    private String motivoConsulta;
}
```

### 5.2 Repositorios (`repository/`)
- Extienden `JpaRepository<Entidad, ID>` y llevan la anotación `@Repository`.
- Incluir Javadoc explicativo en métodos de consulta personalizados.

```java
package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.SolicitudAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para operaciones sobre la entidad SolicitudAtencion.
 */
@Repository
public interface SolicitudAtencionRepository extends JpaRepository<SolicitudAtencion, Long> {

    /**
     * Busca solicitudes asociadas a un tipo de solicitud ordenadas por ID descendente.
     */
    List<SolicitudAtencion> findByTipoSolicitudIdOrderByIdDesc(final Integer tipoSolicitudId);
}
```

### 5.3 DTOs (`dto/request/` y `dto/response/`)
- DTOs de Request: con anotaciones de Jakarta Validation (`@NotNull`, `@NotBlank`, `@Size`, `@Email`, etc.).
- DTOs de Response: inmutables, con `@Builder`. Nunca exponer las entidades JPA directamente en los controladores.

```java
package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudAtencionRequest {

    @NotNull(message = "El tipo de solicitud es obligatorio")
    private Integer idTipoSolicitud;

    @NotBlank(message = "El motivo de consulta no puede estar vacío")
    private String motivoConsulta;
}
```

### 5.4 Mappers (`mapper/`)
Se admiten dos estrategias:

**Opción A: MapStruct (Recomendado)**
```java
@Mapper(componentModel = "spring")
public interface SolicitudAtencionMapper {
    @Mapping(source = "tipoSolicitud.id", target = "idTipoSolicitud")
    SolicitudAtencionResponse toDto(final SolicitudAtencion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoSolicitud", ignore = true)
    SolicitudAtencion toEntity(final SolicitudAtencionRequest request);
}
```

**Opción B: Mappers Estáticos Estrictos**
- Constructor privado lanzando `UnsupportedOperationException`.
- Métodos estáticos puros con parámetros `final` y verificación de nulos.

```java
public final class SolicitudAtencionMapper {

    private SolicitudAtencionMapper() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }

    public static SolicitudAtencionResponse toDto(final SolicitudAtencion entity) {
        if (entity == null) {
            return null;
        }
        return SolicitudAtencionResponse.builder()
                .id(entity.getId())
                .motivoConsulta(entity.getMotivoConsulta())
                .build();
    }
}
```

### 5.5 Servicios (`service/`)
- Métodos transaccionales delimitados.
- Lanzar excepciones de negocio personalizadas (`ResourceNotFoundException`, `DuplicateResourceException`).
- Logging contextual con `@Slf4j`.

```java
package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.SolicitudAtencionRequest;
import co.edu.udea.casilda.dto.response.SolicitudAtencionResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.SolicitudAtencion;
import co.edu.udea.casilda.model.entity.TipoSolicitud;
import co.edu.udea.casilda.repository.SolicitudAtencionRepository;
import co.edu.udea.casilda.repository.TipoSolicitudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudAtencionService {

    private final SolicitudAtencionRepository solicitudRepository;
    private final TipoSolicitudRepository tipoSolicitudRepository;

    @Transactional
    public SolicitudAtencionResponse crear(final SolicitudAtencionRequest request) {
        log.info("[SolicitudAtencionService] - CREAR: Creando solicitud para tipo {}", request.getIdTipoSolicitud());

        final TipoSolicitud tipoSolicitud = tipoSolicitudRepository.findById(request.getIdTipoSolicitud())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de solicitud no encontrado: " + request.getIdTipoSolicitud()));

        final SolicitudAtencion entidad = new SolicitudAtencion();
        entidad.setTipoSolicitud(tipoSolicitud);
        entidad.setMotivoConsulta(request.getMotivoConsulta());

        final SolicitudAtencion guardada = solicitudRepository.save(entidad);

        return SolicitudAtencionResponse.builder()
                .id(guardada.getId())
                .motivoConsulta(guardada.getMotivoConsulta())
                .build();
    }
}
```

### 5.6 Controladores (`controller/`)
- Definir rutas base REST en plural o recursos descriptivos (kebab-case).
- Documentación Swagger completa con `@Tag`, `@Operation`, `@ApiResponses`, `@SecurityRequirement(name = "bearerAuth")`.
- Códigos HTTP semánticos: `201 CREATED` en POST, `200 OK` en GET/PUT, `204 NO_CONTENT` en DELETE.

```java
package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.SolicitudAtencionRequest;
import co.edu.udea.casilda.dto.response.SolicitudAtencionResponse;
import co.edu.udea.casilda.service.SolicitudAtencionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes-atencion")
@RequiredArgsConstructor
@Tag(name = "SolicitudesAtencion", description = "Endpoints para la gestión de solicitudes de atención")
public class SolicitudAtencionController {

    private final SolicitudAtencionService service;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear nueva solicitud", description = "Crea un registro de solicitud de atención. Requiere autenticación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<SolicitudAtencionResponse> crear(
            @Valid @RequestBody final SolicitudAtencionRequest request) {
        final SolicitudAtencionResponse response = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

---

## 🛡️ 6. Manejo Global de Excepciones

- Centralizado con `@RestControllerAdvice` en `GlobalExceptionHandler`.
- Estructura de error consistente con timestamp, status HTTP, mensaje y detalle de campos en caso de errores de validación.

---

## 🪵 7. Logging

- Usar `@Slf4j` en lugar de instanciar `LoggerFactory.getLogger()`.
- Niveles: `DEBUG` para depuración detallada, `INFO` para operaciones de negocio principales, `WARN` para situaciones anómalas recuperables, `ERROR` para fallos y excepciones.
- Siempre formatear mensajes con placeholders `{}` (no concatenar con `+`).
- Plantilla estándar:
  ```java
  log.info("[Modulo/Servicio] - ACCION: detalle: {}, id: {}", detalle, id);
  log.error("[Modulo/Servicio] - ACCION: error: {}, id: {}", ex.getMessage(), id, ex);
  ```
- **Nunca registrar información sensible** (contraseñas, tokens JWT, datos personales protegidos).

---

## 🧪 8. Pruebas Unitarias y de Integración

- Estructura **Given / When / Then** en cada test.
- Nomenclatura descriptiva (ej: `crear_conDatosValidos_retornaRespuestaCreada`, `obtenerPorId_cuandoNoExiste_lanzaResourceNotFoundException`).
- Pruebas unitarias con Mockito (`@ExtendWith(MockitoExtension.class)` o `@Mock` / `@InjectMocks`).
- Pruebas web con `@WebMvcTest(Controlador.class)`.
- Pruebas de integración con `@SpringBootTest`.
- Evitar lógica de negocio y reflection dentro de los tests.

---

## ⚙️ 9. Comandos de Compilación y Ejecución

```powershell
# Ejecución en desarrollo
mvn spring-boot:run

# Compilación y empaquetado (WAR para Tomcat/producción)
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.10.7-hotspot"
& "C:\Program Files\apache-maven-3.9.13\bin\mvn.cmd" clean package -DskipTests
```
El archivo generado se ubicará en `target/api-casilda.war`.
