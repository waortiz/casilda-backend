package co.edu.udea.casilda.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8080/api/v1").description("Servidor local"))
                .info(new Info()
                        .title("CASILDA API")
                        .version("1.0.0")
                        .description("""
                                API REST para el sistema de gestión CASILDA.

                                Base URL: `http://localhost:8080/api/v1`
                                OpenAPI JSON: `/api-docs`
                                Swagger UI: `/swagger-ui.html`

                                ## Pruebas rápidas en Swagger
                                1. Consumir `POST /auth/login` con email y password válidos.
                                2. Copiar el campo `token` de la respuesta.
                                3. Click en `Authorize` y pegar: `Bearer <token>`.
                                4. Probar endpoints de formularios, gestión y casos.
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
