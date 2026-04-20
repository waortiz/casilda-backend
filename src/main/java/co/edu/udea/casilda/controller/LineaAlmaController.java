package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.ContactoLineaAlmaRequest;
import co.edu.udea.casilda.dto.request.RegistroLineaAlmaRequest;
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

@RestController
@RequestMapping("/linea-alma")
@RequiredArgsConstructor
@Tag(name = "Línea ALMA", description = "API para gestión de registros de Línea ALMA y APH")
public class LineaAlmaController {

    private final LineaAlmaService lineaAlmaService;

    @PostMapping("/registros")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear registro Línea ALMA", description = "Crea un registro principal de Línea ALMA con información APH y remisiones. **Requiere autenticación.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Algún recurso relacionado no fue encontrado")
    })
    public ResponseEntity<RegistroLineaAlmaResponse> crearRegistro(@Valid @RequestBody RegistroLineaAlmaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lineaAlmaService.crearRegistro(request));
    }

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
}
