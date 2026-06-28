package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.ContactoLineaAlmaRequest;
import co.edu.udea.casilda.dto.response.ContactoLineaAlmaResponse;
import co.edu.udea.casilda.dto.response.RegistroLineaAlmaResponse;
import co.edu.udea.casilda.service.LineaAlmaService;
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

import co.edu.udea.casilda.dto.request.Pestana0LineaAlmaRequest;
import co.edu.udea.casilda.dto.request.Pestana1LineaAlmaRequest;
import co.edu.udea.casilda.dto.request.Pestana2LineaAlmaRequest;
import co.edu.udea.casilda.dto.request.Pestana3LineaAlmaRequest;
import co.edu.udea.casilda.dto.request.Pestana4LineaAlmaRequest;
import co.edu.udea.casilda.dto.request.Pestana5LineaAlmaRequest;

@RestController
@RequestMapping("/linea-alma")
@RequiredArgsConstructor
@Tag(name = "Línea ALMA", description = "API para gestión de registros de Línea ALMA y APH")
public class LineaAlmaController {

    private final LineaAlmaService lineaAlmaService;

    @GetMapping("/registros/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener registro Línea ALMA por ID", description = "Obtiene el detalle de un registro Línea ALMA. **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<RegistroLineaAlmaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lineaAlmaService.obtenerPorId(id));
    }

    @GetMapping("/registros")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar registros Línea ALMA", description = "Lista todos los registros de Línea ALMA. **Requiere autenticación.**")
    @ApiResponse(responseCode = "200", description = "Listado de registros obtenido exitosamente")
    public ResponseEntity<List<RegistroLineaAlmaResponse>> listarRegistros() {
        return ResponseEntity.ok(lineaAlmaService.listarRegistros());
    }

    @PostMapping("/registros/{idRegistro}/contactos")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar contacto Línea ALMA", description = "Registra un contacto para un registro Línea ALMA. **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contacto registrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Registro o resultado de contacto no encontrado")
    })
    public ResponseEntity<ContactoLineaAlmaResponse> registrarContacto(
            @PathVariable Long idRegistro,
            @Valid @RequestBody ContactoLineaAlmaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lineaAlmaService.registrarContacto(idRegistro, request));
    }

    @GetMapping("/registros/{idRegistro}/contactos")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar contactos Línea ALMA", description = "Lista los contactos de un registro Línea ALMA. **Requiere autenticación.**")
    @ApiResponse(responseCode = "200", description = "Listado de contactos obtenido exitosamente")
    public ResponseEntity<List<ContactoLineaAlmaResponse>> listarContactos(@PathVariable Long idRegistro) {
        return ResponseEntity.ok(lineaAlmaService.listarContactos(idRegistro));
    }

    @PostMapping("/registros/pestana/0")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar pestaña 0 (Caso) de Línea ALMA")
    public ResponseEntity<RegistroLineaAlmaResponse> registrarPestana0(
            @Valid @RequestBody Pestana0LineaAlmaRequest request) {
        return ResponseEntity.ok(lineaAlmaService.registrarPestana0(request));
    }

    @PostMapping("/registros/pestana/1")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar pestaña 1 (Persona) de Línea ALMA")
    public ResponseEntity<RegistroLineaAlmaResponse> registrarPestana1(
            @Valid @RequestBody Pestana1LineaAlmaRequest request) {
        return ResponseEntity.ok(lineaAlmaService.registrarPestana1(request));
    }

    @PostMapping("/registros/pestana/2")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar pestaña 2 (Complementarios) de Línea ALMA")
    public ResponseEntity<RegistroLineaAlmaResponse> registrarPestana2(
            @Valid @RequestBody Pestana2LineaAlmaRequest request) {
        return ResponseEntity.ok(lineaAlmaService.registrarPestana2(request));
    }

    @PostMapping("/registros/pestana/3")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar pestaña 3 (APH) de Línea ALMA")
    public ResponseEntity<RegistroLineaAlmaResponse> registrarPestana3(
            @Valid @RequestBody Pestana3LineaAlmaRequest request) {
        return ResponseEntity.ok(lineaAlmaService.registrarPestana3(request));
    }

    @PostMapping("/registros/pestana/4")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar pestaña 4 (Contactos) de Línea ALMA")
    public ResponseEntity<RegistroLineaAlmaResponse> registrarPestana4(
            @Valid @RequestBody Pestana4LineaAlmaRequest request) {
        return ResponseEntity.ok(lineaAlmaService.registrarPestana4(request));
    }

    @PostMapping("/registros/pestana/5")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar pestaña 5 (Remisiones) de Línea ALMA")
    public ResponseEntity<RegistroLineaAlmaResponse> registrarPestana5(
            @Valid @RequestBody Pestana5LineaAlmaRequest request) {
        return ResponseEntity.ok(lineaAlmaService.registrarPestana5(request));
    }
}
