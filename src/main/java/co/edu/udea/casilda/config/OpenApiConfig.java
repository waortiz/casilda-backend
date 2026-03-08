package co.edu.udea.casilda.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CASILDA API")
                        .version("1.0.0")
                        .description("""
                                API REST para el sistema de gestión CASILDA.

                                Base URL: `<protocolo>://<sitio>/...` (tomada automáticamente del host actual)
                                OpenAPI JSON: `/api-docs`
                                Swagger UI: `/swagger-ui.html`

                                ## Módulos disponibles
                                - **Autenticación**: Login y gestión de tokens JWT
                                - **Usuarios**: Administración de usuarios del sistema (requiere rol ADMIN)
                                - **Maestros**: Gestión de datos maestros (tipos de solicitud, campus, dependencias, etc.)
                                - **Solicitudes**: Gestión de solicitudes de acompañamiento con generación automática de códigos únicos

                                ## Pruebas rápidas en Swagger
                                1. Consumir `POST /auth/login` con email y password válidos.
                                2. Copiar el campo `token` de la respuesta.
                                3. Click en `Authorize` y pegar: `Bearer <token>`.
                                4. Probar endpoints de usuarios, maestros y solicitudes.
                                """)
                        .contact(new Contact()
                                .name("Equipo CASILDA")
                                .email("contacto@casilda.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
