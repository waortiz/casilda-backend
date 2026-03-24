package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.RegistroAtencionCompleteRequest;
import co.edu.udea.casilda.dto.response.AtencionResponse;
import co.edu.udea.casilda.service.AtencionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestión de atenciones
 */
@RestController
@RequestMapping("/atenciones")
@RequiredArgsConstructor
@Tag(name = "Atenciones", description = "API para gestión de atenciones y seguimientos")
public class AtencionController {

    private final AtencionService atencionService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Registrar atención completa",
            description = "Registra una nueva atención, actualiza datos de persona y caso, y crea seguimientos asociados. **Requiere autenticación.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atención registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Cita, Persona o Caso no encontrado")
    })
    public ResponseEntity<AtencionResponse> registrarAtencionCompleta(
            @Valid @RequestBody RegistroAtencionCompleteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(atencionService.registrarAtencionCompleta(request));
    }
}
