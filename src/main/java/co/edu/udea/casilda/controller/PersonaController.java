package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.response.PersonaSearchResponse;
import co.edu.udea.casilda.service.SolicitudAcompanamientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para consultas de persona.
 */
@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "API para consultas de personas")
public class PersonaController {

    private final SolicitudAcompanamientoService solicitudAcompanamientoService;

    /**
     * Busca persona por tipo y número de documento.
     */
    @GetMapping("/documento/{numeroDocumento}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar persona por documento", description = "Busca datos de persona para autocompletar formulario. **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public ResponseEntity<PersonaSearchResponse> buscarPersonaPorDocumento(
            @PathVariable String numeroDocumento,
            @RequestParam(required = false) Integer tipoDocumentoId) {
        return ResponseEntity.ok(solicitudAcompanamientoService.buscarPersonaPorDocumento(numeroDocumento, tipoDocumentoId));
    }
}
