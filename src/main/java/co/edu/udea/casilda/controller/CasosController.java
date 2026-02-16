package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.CasoDetalleUpdateRequest;
import co.edu.udea.casilda.dto.request.ContactoTelefonicoRequest;
import co.edu.udea.casilda.dto.request.RepartoRequest;
import co.edu.udea.casilda.dto.response.*;
import co.edu.udea.casilda.service.FormularioWorkflowService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Casos", description = "Consulta, reparto, seguimiento y gestión de casos")
public class CasosController {

    private final FormularioWorkflowService formularioWorkflowService;

    @GetMapping("/casos")
    @Operation(summary = "Listar casos")
    public ResponseEntity<List<CasoResumenResponse>> listarCasos() {
        return ResponseEntity.ok(formularioWorkflowService.listarCasos());
    }

    @GetMapping("/casos/sin-asignar")
    @Operation(summary = "Listar casos sin asignar")
    public ResponseEntity<List<CasoResumenResponse>> listarCasosSinAsignar() {
        return ResponseEntity.ok(formularioWorkflowService.listarCasosSinAsignar());
    }

    @PutMapping("/casos/{codigo}")
    @Operation(summary = "Actualizar datos de caso")
    public ResponseEntity<CasoResumenResponse> actualizarCaso(@PathVariable String codigo,
                                                              @RequestBody CasoDetalleUpdateRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.actualizarCaso(codigo, request));
    }

    @PostMapping("/repartos")
        @Operation(
            summary = "Asignar caso (reparto)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "RepartoRequest",
                        summary = "Ejemplo de asignación de caso",
                        value = """
                            {
                              \"casoId\": 12,
                              \"revisorId\": 4,
                              \"comentario\": \"Asignación por disponibilidad y carga actual.\"
                            }
                            """
                    )
                )
            )
        )
    public ResponseEntity<RegistroResponse> asignarCaso(@Valid @RequestBody RepartoRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.asignarCaso(request));
    }

    @GetMapping("/asignaciones/mis-casos")
    @Operation(summary = "Listar mis asignaciones")
    public ResponseEntity<List<CasoResumenResponse>> listarMisAsignaciones(@RequestParam String profesional) {
        return ResponseEntity.ok(formularioWorkflowService.listarMisAsignaciones(profesional));
    }

    @GetMapping("/acompanamientos/{codigoCaso}/contactos")
    @Operation(summary = "Listar historial de contactos telefónicos")
    public ResponseEntity<List<ContactoTelefonicoResponse>> listarContactos(@PathVariable String codigoCaso) {
        return ResponseEntity.ok(formularioWorkflowService.listarContactos(codigoCaso));
    }

    @PostMapping("/acompanamientos/{codigoCaso}/contactos")
    @Operation(summary = "Registrar intento de contacto telefónico")
    public ResponseEntity<List<ContactoTelefonicoResponse>> registrarContacto(@PathVariable String codigoCaso,
                                                                               @Valid @RequestBody ContactoTelefonicoRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.registrarContacto(codigoCaso, request));
    }

    @GetMapping("/seguimiento/{codigoCaso}")
    @Operation(summary = "Consultar seguimiento de trámite por código")
    public ResponseEntity<SeguimientoResponse> buscarSeguimiento(@PathVariable String codigoCaso) {
        return ResponseEntity.ok(formularioWorkflowService.buscarSeguimiento(codigoCaso));
    }

    @GetMapping("/dashboard/revisor")
    @Operation(summary = "Obtener datos de dashboard de revisor")
    public ResponseEntity<DashboardRevisorResponse> dashboardRevisor() {
        return ResponseEntity.ok(formularioWorkflowService.obtenerDashboard());
    }
}
