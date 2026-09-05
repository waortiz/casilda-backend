# Reglas y Estándares para Java Spring Boot — Backend CASILDA

## Formato de Código
- Indentación: 4 espacios.
- Líneas en blanco: úsalas para separar bloques lógicos de código.
- Longitud de línea: máximo 120 caracteres.
- Usa el estilo de código por defecto de IntelliJ IDEA para Java.

## Estilo Java
- Usa codificación UTF-8.
- Usa nombres descriptivos para clases, métodos y variables.
- Evita la palabra clave `var`; prefiere tipos explícitos.
- Todos los parámetros de métodos deben ser `final`.
- Todas las variables deben declararse como `final` cuando sea posible.
- Preferencia por la inmutabilidad:
  - Evita mutaciones de objetos, especialmente al usar bucles for-each o la Stream API con `forEach()`.
  - Evita números y cadenas mágicas; usa constantes o enums en su lugar.
  - Verifica vacíos y nulos antes de operar sobre colecciones y cadenas.
  - Evita métodos que usen la cláusula `throws`; prefiere excepciones no verificadas (`RuntimeException`).
- Evita comentarios obvios; resérvalos para regex complejas, expresiones cron, TODOs o estructura given/when/then en pruebas.
- Usa la anotación `@Override` al sobrescribir métodos.
- Evita `Objects.isNull()` y `Objects.nonNull()` para una o dos variables; prefiere verificaciones directas contra null (`obj == null` / `obj != null`).
- Agrupa múltiples condiciones en una variable booleana para mejorar la legibilidad.
- Prefiere retornos tempranos (early return / guard clauses) y evita sentencias `else` innecesarias.

## Anotaciones Lombok
- Usa `@RequiredArgsConstructor` para inyección de dependencias por constructor. Prohibido `@Autowired` en código productivo.
- Usa `@Slf4j` para logging.
- Usa `@Builder` o `@Builder(setterPrefix = "with")` para creación de DTOs y objetos complejos.
- Evita `@Data` cuando se requiera control granular de mutabilidad; prefiere `@Getter` y `@Setter`.

## Anotaciones y Capas Spring
- **`@Service`**: para clases de lógica de negocio transaccionales (`@Transactional`).
- **`@Repository`**: para interfaces de acceso a datos que extienden `JpaRepository`.
- **`@RestController`**: para controladores REST, documentados con OpenAPI 3 y protegidos con `@SecurityRequirement(name = "bearerAuth")`.
- **`@Component` / `@Configuration`**: para componentes genéricos y clases de configuración.
- **`@ConfigurationProperties`**: para enlazar propiedades relacionadas (cuando sean más de 2 propiedades).
- **`@Validated` / `@Valid`**: para validar Bean Validation en parámetros y request bodies.
- **`@PreAuthorize`**: en la capa de controlador para seguridad por roles.
- Evitar dependencias circulares y la anotación `@Order` para resolverlas.

## Mappers
- **MapStruct**: interfaz `@Mapper(componentModel = "spring")`, sufijo `Mapper`, métodos `toDto`, `toEntity`.
- **Mappers Estáticos**: constructor privado lanzando `UnsupportedOperationException`, métodos estáticos con parámetros `final` y verificación de nulos.

## Manejo de Excepciones
- Excepciones personalizadas del dominio extendiendo `RuntimeException` (`ResourceNotFoundException`, `DuplicateResourceException`).
- Manejo global centralizado con `@RestControllerAdvice` y `@ExceptionHandler`.
- Códigos HTTP semánticos y estructura uniforme de respuesta de error.

## Logging
- Logging con `@Slf4j` formateado con placeholders `{}` (no concatenar strings).
- Nunca registrar credenciales, tokens o datos sensibles.
- Formato: `log.info("[Modulo/Servicio] - ACCION: {}, id: {}", detalle, id);`

## Pruebas
- JUnit 5 y Mockito.
- Estructura `given / when / then`.
- Pruebas web con `@WebMvcTest` y de integración con `@SpringBootTest`.
