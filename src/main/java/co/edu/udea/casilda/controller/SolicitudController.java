package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.SolicitudAcompanamientoRequest;
import co.edu.udea.casilda.dto.response.SolicitudAcompanamientoResponse;
import co.edu.udea.casilda.service.SolicitudAcompanamientoService;
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
 * Controlador REST para gestión de solicitudes de acompañamiento
 */
@RestController
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
@Tag(name = "Solicitudes", description = "API para gestión de solicitudes de acompañamiento")
public class SolicitudController {

    private final SolicitudAcompanamientoService service;

    /**
     * Crea una nueva solicitud de acompañamiento
     */
    @PostMapping("/acompanamiento")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Crear solicitud de acompañamiento",
            description = "Crea una nueva solicitud de acompañamiento y genera un código único. **Requiere autenticación.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<SolicitudAcompanamientoResponse> crearSolicitud(
            @Valid @RequestBody SolicitudAcompanamientoRequest request) {
        SolicitudAcompanamientoResponse response = service.crearSolicitud(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtiene una solicitud por ID
     */
    @GetMapping("/acompanamiento/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Obtener solicitud por ID",
            description = "Obtiene los detalles de una solicitud específica por su ID. **Requiere autenticación.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<SolicitudAcompanamientoResponse> obtenerPorId(@PathVariable Long id) {
        SolicitudAcompanamientoResponse response = service.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una solicitud por código
     */
    @GetMapping("/acompanamiento/codigo/{codigo}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Obtener solicitud por código",
            description = "Obtiene los detalles de una solicitud por su código único (ej: ACO-2026-0001). **Requiere autenticación.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<SolicitudAcompanamientoResponse> obtenerPorCodigo(@PathVariable String codigo) {
        SolicitudAcompanamientoResponse response = service.obtenerPorCodigo(codigo);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todas las solicitudes
     */
    @GetMapping("/acompanamiento")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Listar todas las solicitudes",
            description = "Obtiene un listado con todas las solicitudes de acompañamiento. **Requiere autenticación.**"
    )
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida exitosamente")
    public ResponseEntity<List<SolicitudAcompanamientoResponse>> listarTodas() {
        List<SolicitudAcompanamientoResponse> solicitudes = service.listarTodas();
        return ResponseEntity.ok(solicitudes);
    }
}
