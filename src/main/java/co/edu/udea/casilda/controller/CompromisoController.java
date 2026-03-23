package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.CompromisoPersonaAtendidaRequest;
import co.edu.udea.casilda.dto.request.CompromisoProfesionalRequest;
import co.edu.udea.casilda.dto.response.CompromisoPersonaAtendidaResponse;
import co.edu.udea.casilda.dto.response.CompromisoProfesionalResponse;
import co.edu.udea.casilda.service.CompromisoService;
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
 * Controlador REST para gestión de compromisos de persona atendida y compromisos profesionales.
 */
@RestController
@RequestMapping("/compromisos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Compromisos", description = "API para gestión de compromisos de persona atendida y profesionales")
public class CompromisoController {

    private final CompromisoService compromisoService;

    // ─── Compromisos Persona Atendida ─────────────────────────────────────────

    @GetMapping("/persona/{atencionId}")
    @Operation(summary = "Listar compromisos de persona atendida por atención",
               description = "Retorna todos los compromisos de la persona atendida para una atención. **Requiere autenticación.**")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    public ResponseEntity<List<CompromisoPersonaAtendidaResponse>> listarCompromisoPersona(
            @PathVariable Long atencionId) {
        return ResponseEntity.ok(compromisoService.listarCompromisoPersonaPorAtencion(atencionId));
    }

    @PostMapping("/persona")
    @Operation(summary = "Crear compromiso de persona atendida",
               description = "Registra un nuevo compromiso para la persona atendida. **Requiere autenticación.**")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Compromiso creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Atención o tipo de compromiso no encontrado")
    })
    public ResponseEntity<CompromisoPersonaAtendidaResponse> crearCompromisoPersona(
            @Valid @RequestBody CompromisoPersonaAtendidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(compromisoService.crearCompromisoPersona(request));
    }

    @DeleteMapping("/persona/{atencionId}/{tipoCompromisoId}")
    @Operation(summary = "Eliminar compromiso de persona atendida",
               description = "Elimina un compromiso de persona atendida. **Requiere autenticación.**")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Compromiso no encontrado")
    })
    public ResponseEntity<Void> eliminarCompromisoPersona(
            @PathVariable Long atencionId,
            @PathVariable Integer tipoCompromisoId) {
        compromisoService.eliminarCompromisoPersona(atencionId, tipoCompromisoId);
        return ResponseEntity.noContent().build();
    }

    // ─── Compromisos Profesional ──────────────────────────────────────────────

    @GetMapping("/profesional/{atencionId}")
    @Operation(summary = "Listar compromisos profesionales por atención",
               description = "Retorna todos los compromisos profesionales para una atención. **Requiere autenticación.**")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    public ResponseEntity<List<CompromisoProfesionalResponse>> listarCompromisoProfesional(
            @PathVariable Long atencionId) {
        return ResponseEntity.ok(compromisoService.listarCompromisoProfesionalPorAtencion(atencionId));
    }

    @PostMapping("/profesional")
    @Operation(summary = "Crear compromiso profesional",
               description = "Registra un nuevo compromiso profesional. **Requiere autenticación.**")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Compromiso creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Atención, grupo o tipo de compromiso no encontrado")
    })
    public ResponseEntity<CompromisoProfesionalResponse> crearCompromisoProfesional(
            @Valid @RequestBody CompromisoProfesionalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(compromisoService.crearCompromisoProfesional(request));
    }

    @DeleteMapping("/profesional/{atencionId}/{tipoCompromisoId}")
    @Operation(summary = "Eliminar compromiso profesional",
               description = "Elimina un compromiso profesional. **Requiere autenticación.**")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Compromiso no encontrado")
    })
    public ResponseEntity<Void> eliminarCompromisoProfesional(
            @PathVariable Long atencionId,
            @PathVariable Integer tipoCompromisoId) {
        compromisoService.eliminarCompromisoProfesional(atencionId, tipoCompromisoId);
        return ResponseEntity.noContent().build();
    }
}
