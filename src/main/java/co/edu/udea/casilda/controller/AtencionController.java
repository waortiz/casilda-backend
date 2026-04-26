package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.RegistroAtencionCompleteRequest;
import co.edu.udea.casilda.dto.request.RegistroOtroCasoRequest;
import co.edu.udea.casilda.dto.response.AtencionResponse;
import co.edu.udea.casilda.dto.response.OtroCasoResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/solicitudes/{solicitudId}/otros-casos")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Listar otros casos por solicitud",
            description = "Lista los casos adicionales asociados a una solicitud para la pestaña Otros Casos. **Requiere autenticación.**"
    )
    @ApiResponse(responseCode = "200", description = "Casos obtenidos exitosamente")
    public ResponseEntity<List<OtroCasoResponse>> listarOtrosCasos(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(atencionService.listarOtrosCasos(solicitudId));
    }

    @PostMapping("/solicitudes/{solicitudId}/otros-casos")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Crear otro caso",
            description = "Crea un caso adicional para una solicitud desde la pestaña Otros Casos. **Requiere autenticación.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<OtroCasoResponse> crearOtroCaso(
            @PathVariable Long solicitudId,
            @Valid @RequestBody RegistroOtroCasoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(atencionService.registrarOtroCaso(solicitudId, request));
    }

    @PutMapping("/otros-casos/{casoId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Actualizar otro caso",
            description = "Actualiza un caso adicional de la pestaña Otros Casos. **Requiere autenticación.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    public ResponseEntity<OtroCasoResponse> actualizarOtroCaso(
            @PathVariable Long casoId,
            @Valid @RequestBody RegistroOtroCasoRequest request) {
        return ResponseEntity.ok(atencionService.actualizarOtroCaso(casoId, request));
    }

    @DeleteMapping("/otros-casos/{casoId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Eliminar otro caso",
            description = "Elimina un caso adicional de la pestaña Otros Casos. **Requiere autenticación.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Caso eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    public ResponseEntity<Void> eliminarOtroCaso(@PathVariable Long casoId) {
        atencionService.eliminarOtroCaso(casoId);
        return ResponseEntity.noContent().build();
    }
}
