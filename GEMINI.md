# Directrices del Agente — Backend CASILDA (Java & Spring Boot)

Este repositorio contiene el backend REST del Sistema CASILDA desarrollado en **Java 21** y **Spring Boot 3.2.1**.

## Reglas Principales de Desarrollo

1. **Inyección de Dependencias:** Usar exclusivamente inyección por constructor mediante `@RequiredArgsConstructor` sobre atributos `private final`. Prohibido `@Autowired` en código productivo.
2. **Inmutabilidad y Buenas Prácticas:**
   - Parámetros de métodos marcados como `final`.
   - Variables locales marcadas como `final` siempre que sea posible.
   - Evitar `var`, preferir tipos explícitos.
   - Evitar valores mágicos; usar constantes o enumeraciones.
3. **Controladores y DTOs:**
   - Nunca exponer entidades JPA directamente en respuestas de controladores REST. Mapear siempre a DTOs usando `@Builder` o MapStruct.
   - Proteger endpoints con `@SecurityRequirement(name = "bearerAuth")` y documentar con anotaciones OpenAPI/Swagger.
   - Validar entradas con `@Valid` y anotaciones de Jakarta Validation.
4. **Servicios y Transaccionalidad:**
   - Métodos de solo lectura anotados con `@Transactional(readOnly = true)`.
   - Métodos de escritura con `@Transactional`.
5. **Entidades JPA y Base de Datos:**
   - Nombres de tabla en minúsculas continuas (ej. `solicitudatencion`).
   - Claves foráneas como `id<tabla>` en minúsculas.
   - Relaciones `@ManyToOne` con `FetchType.LAZY`.
6. **Logging:** Usar `@Slf4j` con placeholders `{}`. Prohibido concatenar strings o loguear credenciales/tokens.

Para instrucciones detalladas y ejemplos de código, consultar:
- Skill: [`.agents/skills/spring-expert/SKILL.md`](.agents/skills/spring-expert/SKILL.md)
- Reglas: [`.agents/rules/java-spring-boot.md`](.agents/rules/java-spring-boot.md)
