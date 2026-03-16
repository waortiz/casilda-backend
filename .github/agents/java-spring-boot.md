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
- Evita números y cadenas mágicas; usa constantes en su lugar.
- Verifica vacíos y nulos antes de operar sobre colecciones y cadenas.
- Evita métodos que usen la cláusula `throws`; prefiere excepciones no verificadas.

- Evita comentarios.
- Los comentarios pueden aplicarse para: expresiones cron, patrones Regex, TODOs o separación given/when/then en pruebas.
- Usa la anotación `@Override` al sobrescribir métodos.
- Evita `Objects.*isNull()` y `Objects.*nonNull()` para una o dos variables; prefiere verificaciones directas contra null por mejor rendimiento.
- Agrupa múltiples condiciones en una variable booleana para mejorar la legibilidad.
- Prefiere retornos tempranos.
- Evita sentencias else cuando no sean necesarias e intenta usar retornos tempranos.

## Anotaciones Lombok

- Usa `@RequiredArgsConstructor` de Lombok para inyección de dependencias por constructor.
- Usa `@Slf4j` de Lombok para logging.
- Usa `@Builder(setterPrefix = "with"))` para creación de objetos complejos.
- Evita la anotación `@Data`; prefiere `@Getter` y `@Setter` para un control más granular.

## Anotaciones

- **`@Service`**: para clases de lógica de negocio.
- **`@Repository`**: para clases de acceso a datos que extienden repositorios JPA o interactúan con la base de datos.
- **`@RestController`**: para controladores web.
- **`@Component`**: para componentes genéricos de Spring.
- **`@Configuration`**: para clases de configuración de Spring.
- **`@Autowired`**: prefiere inyección por constructor en código de producción e inyección por campo solo en pruebas.
- **`@ConfigurationProperties`**: para enlazar propiedades relacionadas y evitar múltiples anotaciones `@Value`. A partir de más de 2 propiedades, considera usar esta anotación.
- **`@Transactional`**: solo las clases Service deben anotarse con `@Transactional` a nivel de clase para evitar manejar transacciones en cada método.
- **`@Validated`**: para habilitar Bean Validation en parámetros de métodos o clases.
- **`@PreAuthorize`**: en la capa de controlador cuando se use Spring Security para aplicar seguridad a nivel de método.
- Deben evitarse las dependencias circulares. Evita la anotación `@Order` para resolver dependencias.

## Mappers (como equipo de desarrollo, elijan MapStruct o Mappers estáticos estrictos)

**Usar MapStruct**

- Úsalo para el mapeo entre DTOs y entidades.
- Define interfaces mapper con la anotación `@Mapper`.
- Usa la anotación `@Mapping` para mapeos de campos personalizados.
- Usa `componentModel = "spring"` para permitir que Spring gestione las instancias del mapper.
- El mapper debe tener el sufijo `Mapper` (por ejemplo, `UserMapper`).
- Nombra claramente los métodos del mapper (por ejemplo, `toDto`, `toEntity`).
- Ejemplo de interfaz Mapper:

  ```java
  @Mapper(componentModel = "spring")
  public interface UserMapper {
	  @Mapping(source = "email", target = "emailAddress")
	  UserDTO toDto(User user);
	  @Mapping(source = "emailAddress", target = "email")
	  User toEntity(UserDTO userDto);
  }
  ```

- Para probar mappers, usa `Mappers.getMapper(UserMapper.class)` para obtener una instancia del mapper.

**Usar Mappers estáticos**

- Define un constructor privado para evitar la instanciación con `UnsupportedOperationException("This class should never be instantiated")`.
- Usa métodos estáticos para mapear entre DTOs y entidades.
- Nombra claramente los métodos del mapper (por ejemplo, `toDto`, `toEntity`).
- Ejemplo de clase Mapper estática:

  ```java
  public class UserMapper {
	  private UserMapper() {
		  throw new UnsupportedOperationException("This class should never be instantiated");
	  }
	  public static UserDTO toDto(final User user) {
		  if (user == null) {
			  return null;
		  }
		  return UserDTO.builder()
			  .withId(user.getId())
			  .withEmailAddress(user.getEmail())
			  .build();
	  }
	  public static User toEntity(final UserDTO userDto) {
		  if (userDto == null) {
			  return null;
		  }
		  return User.builder()
			  .withId(userDto.getId())
			  .withEmail(userDto.getEmailAddress())
			  .build();
	  }
  }
  ```

## Manejo de Excepciones

- Excepciones personalizadas: crea clases de excepción de dominio personalizadas que extiendan `RuntimeException`.
- Manejador global de excepciones: usa `@ControllerAdvice` y `@ExceptionHandler` para manejar excepciones globalmente.
- Códigos de estado HTTP: mapea las excepciones a códigos de estado HTTP apropiados en controladores REST.
- Estructura de respuesta de error: define una estructura de respuesta de error consistente.

## Pruebas

- Usa JUnit 5 para pruebas unitarias e integración.
- Usa Mockito para simular dependencias en pruebas unitarias.
- Usa `@WebMvcTest(ControllerClass.class)` para probar controladores Spring MVC.
- Usa `@SpringBootTest` para pruebas de integración que requieran el contexto de Spring.
- Usa la estructura `given/when/then` en métodos de prueba para mayor claridad.
- El nombre de los métodos puede seguir convención snake_case o camelCase para métodos de prueba (por ejemplo, `get_user_by_id_ok`, `get_user_by_id_not_found_ko`).
- Evita reflection en pruebas.
- Evita lógica de negocio en pruebas; enfócate en verificar comportamiento.

## Logging

- Usa la anotación `@Slf4j` de Lombok para logging y evitar código repetitivo con instancias de Logger.
- Registra en niveles apropiados: `DEBUG`, `INFO`, `WARN`, `ERROR`.
- Incluye información de contexto en los logs (por ejemplo, IDs de petición, IDs de usuario).
- Evita registrar información sensible.
- Usa logging estructurado para una mejor gestión de logs.
- Da formato a los mensajes de log con placeholders (por ejemplo, `{}`) en lugar de concatenación de cadenas.
- El código de logging info podría seguir esta plantilla: `log.info("[MicroserviceName/ModuleName] - API-CALL/METHOD/ACTION: response: {}, userId: {}", body, userId);`
- El código de logging error podría seguir esta plantilla: `log.error("[MicroserviceName/ModuleName] - API-CALL/METHOD/ACTION: errorMessage: {}, userId: {}", errorMessage, userId);`
