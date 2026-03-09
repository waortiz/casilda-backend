# Copilot Instructions — casilda-backend

## Proyecto
Sistema CASILDA — backend REST para gestión de solicitudes de acompañamiento psicosocial y jurídico de la Universidad de Antioquia.

## Stack tecnológico
- **Java 21** + **Spring Boot 3.2.1**
- **Maven** 3.9.x — empaquetado como **WAR** (`api-casilda.war`)
- **PostgreSQL** con JPA/Hibernate (`ddl-auto: update`)
- **Lombok** (`@Data`, `@Builder`, `@Slf4j`, `@RequiredArgsConstructor`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- **Spring Security** + **JWT** (`jjwt 0.12.3`)
- **Springdoc OpenAPI 2.3.0** para la documentación Swagger
- **Jakarta Validation** para validaciones de request bodies

## Estructura de paquetes
```
co.edu.udea.casilda
├── config/          # SecurityConfig, JwtService, JwtAuthenticationFilter, OpenApiConfig
├── controller/      # REST controllers
├── dto/
│   ├── request/     # DTOs de entrada con validaciones Jakarta
│   └── response/    # DTOs de salida con @Builder
├── exception/       # ResourceNotFoundException, DuplicateResourceException, GlobalExceptionHandler
├── mapper/          # Clases de mapeo (si aplica)
├── model/
│   ├── entity/      # Entidades JPA
│   └── enums/       # Enumeraciones del dominio
├── repository/      # Interfaces JpaRepository
└── service/         # Lógica de negocio
```

## Convenciones de código

### Entidades (`model/entity/`)
```java
package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nombre_tabla_en_minusculas_concatenado")  // ej: solicitudatencion, contactotelefonico
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NombreEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Integer para tablas maestras, Long para transaccionales

    // FK: @ManyToOne con FetchType.LAZY y @JoinColumn(name = "id<entidad>")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identidad", nullable = false)
    private OtraEntidad otraEntidad;

    // Columnas: @Column con name en snake_case
    @Column(name = "nombre_columna", nullable = false)
    private String nombreColumna;
}
```
- Nombres de tabla: **minúsculas sin guiones bajos** (ej: `solicitudatencion`, `tipodocumento`)
- Nombres de columna FK: `id<nombreentidad>` en minúsculas (ej: `idcaso`, `idtiposolicitud`)
- Colecciones `@OneToMany`: usar `FetchType.LAZY`, `cascade = CascadeType.ALL`, `orphanRemoval = true`
- Inicializar listas: `= new ArrayList<>()`

### Repositorios (`repository/`)
```java
package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.NombreEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para gestionar entidades NombreEntidad
 */
@Repository
public interface NombreEntidadRepository extends JpaRepository<NombreEntidad, Long> {

    /**
     * Descripción del método personalizado
     */
    List<NombreEntidad> findByOtraEntidadIdOrderByFechaDesc(Long otraEntidadId);
}
```
- Siempre `@Repository` + Javadoc en métodos personalizados
- Tipo del ID: `Long` para entidades transaccionales, `Integer` para maestros

### DTOs de request (`dto/request/`)
```java
package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NombreEntityRequest {

    @NotBlank
    private String campo;

    // También: @NotNull, @Size, @Email, @Min, @Max
}
```

### DTOs de response (`dto/response/`)
```java
package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Response para NombreEntidad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NombreEntidadResponse {
    private Long id;
    private String campo;
}
```

### Servicios (`service/`)
```java
package co.edu.udea.casilda.service;

import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NombreService {

    private final EntidadRepository entidadRepository;

    @Transactional(readOnly = true)
    public List<NombreEntidadResponse> listarTodos() {
        log.info("Listar todos...");
        return entidadRepository.findAll().stream()
                .map(e -> NombreEntidadResponse.builder()
                        .id(e.getId().longValue())
                        .campo(e.getCampo())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public NombreEntidadResponse crear(NombreEntidadRequest req) {
        // Validaciones → lanzar ResourceNotFoundException o DuplicateResourceException
        Entidad entidad = entidadRepository.findById(req.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Entidad no encontrada con ID: " + req.getId()));
        // ...
    }
}
```
- Siempre `@Transactional` en métodos de escritura, `@Transactional(readOnly = true)` en lecturas
- Log descriptivo con `log.info()`
- No exponer entidades directamente — mapear siempre a DTO con `.builder()`
- Inyección únicamente por constructor (`@RequiredArgsConstructor` + `private final`)

### Controladores (`controller/`)
```java
package co.edu.udea.casilda.controller;

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
import java.util.List;

/**
 * Controlador REST para gestión de NombreEntidad
 */
@RestController
@RequestMapping("/nombre-recurso")
@RequiredArgsConstructor
@Tag(name = "NombreRecurso", description = "API para gestión de ...")
public class NombreController {

    private final NombreService service;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear ...", description = "... **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<NombreEntidadResponse> crear(@Valid @RequestBody NombreEntidadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener por ID", description = "**Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public ResponseEntity<NombreEntidadResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
```
- Siempre `@SecurityRequirement(name = "bearerAuth")` en endpoints protegidos
- `@Valid` en todos los request bodies
- Respuesta `201 CREATED` para POST, `200 OK` para GET/PUT, `204 NO_CONTENT` para DELETE
- Javadoc de clase

### Excepciones (`exception/`)
- `ResourceNotFoundException` → HTTP 404 (entidad no encontrada)
- `DuplicateResourceException` → HTTP 409 (recurso duplicado)
- `GlobalExceptionHandler` → maneja excepciones globalmente con `@RestControllerAdvice`

## Reglas generales
1. **Nunca** usar `@Autowired` — solo inyección por constructor con `@RequiredArgsConstructor`
2. **Nunca** retornar entidades JPA directamente desde controladores
3. **Siempre** anotar métodos de servicio con `@Transactional` o `@Transactional(readOnly = true)`
4. Los campos FK en entidades deben seguir el patrón `id<NombreEntidad>` en la columna de BD
5. Campos de entidades maestras (tablas de catálogo): solo `id` + `nombre` salvo que la tabla tenga más columnas
6. El campo `id` de entidades maestras es `Integer`, no `Long`
7. Los `@Builder` en responses permiten usar `.NombreResponse.builder().campo(valor).build()`
8. El contexto de la aplicación en producción es `/api-casilda`

## Comando de compilación (local Windows)
```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.10.7-hotspot"
& "C:\Program Files\apache-maven-3.9.13\bin\mvn.cmd" -f "d:\Proyectos\Java\casilda\casilda-backend\pom.xml" clean package "-DskipTests"
```
El WAR resultante es `target/api-casilda.war`.
