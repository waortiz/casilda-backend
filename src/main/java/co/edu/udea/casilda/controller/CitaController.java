package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.CancelarCitaRequest;
import co.edu.udea.casilda.dto.request.ReprogramarCitaRequest;
import co.edu.udea.casilda.dto.response.CitaResponse;
import co.edu.udea.casilda.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de citas de atención
 */
@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor
@Tag(name = "Citas", description = "API para gestión de citas de atención")
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar todas las citas", description = "Retorna todas las citas registradas en el sistema. **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de citas obtenida exitosamente")
    })
    public ResponseEntity<List<CitaResponse>> listarCitas() {
        return ResponseEntity.ok(citaService.listarTodasLasCitas());
    }

    @PutMapping("/{id}/reprogramar")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Reprogramar cita", description = "Actualiza la fecha y hora de una cita. **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita reprogramada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<CitaResponse> reprogramar(
            @PathVariable Long id,
            @Valid @RequestBody ReprogramarCitaRequest request) {
        return ResponseEntity.ok(citaService.reprogramar(id, request));
    }

    @PutMapping("/{id}/cancelar")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancelar cita", description = "Marca una cita como Cancelada con su motivo. **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita cancelada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<CitaResponse> cancelar(
            @PathVariable Long id,
            @Valid @RequestBody CancelarCitaRequest request) {
        return ResponseEntity.ok(citaService.cancelar(id, request));
    }
}
