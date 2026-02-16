package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.AcompanamientoRequest;
import co.edu.udea.casilda.dto.request.AtencionRegistroRequest;
import co.edu.udea.casilda.dto.request.QuejaRequest;
import co.edu.udea.casilda.dto.response.RegistroResponse;
import co.edu.udea.casilda.service.FormularioWorkflowService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Formularios", description = "Registro de formularios de queja, acompañamiento y atención")
public class FormularioController {

    private final FormularioWorkflowService formularioWorkflowService;

    @PostMapping("/quejas")
        @Operation(
            summary = "Registrar queja",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "QuejaRequest",
                        summary = "Ejemplo de registro de queja",
                        value = """
                            {
                              \"perfil\": \"anonimo\",
                              \"descripcion\": \"Reporte de hostigamiento reiterado en espacio académico.\",
                              \"aceptaPolitica\": true,
                              \"nombreVictima\": \"Laura\",
                              \"apellidosVictima\": \"Restrepo\",
                              \"correoVictima\": \"laura@correo.com\",
                              \"generoVictima\": \"Femenino\",
                              \"cargoVictima\": \"Estudiante\",
                              \"deseaContacto\": \"si\",
                              \"correoContacto\": \"contacto@correo.com\",
                              \"whatsappContacto\": \"3001234567\"
                            }
                            """
                    )
                )
            )
        )
    public ResponseEntity<RegistroResponse> registrarQueja(@Valid @RequestBody QuejaRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.registrarQueja(request));
    }

    @PostMapping("/acompanamientos")
        @Operation(
            summary = "Registrar solicitud de acompañamiento",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "AcompanamientoRequest",
                        summary = "Ejemplo de solicitud de acompañamiento",
                        value = """
                            {
                              \"tipoReporte\": \"directa\",
                              \"primerNombre\": \"Miguel\",
                              \"primerApellido\": \"Cano\",
                              \"tipoDocumento\": \"Cédula de Ciudadanía\",
                              \"numeroDocumento\": \"71234456\",
                              \"identidadGenero\": \"Masculino\",
                              \"celular\": \"3154433221\",
                              \"correoInstitucional\": \"m.cano@udea.edu.co\",
                              \"correoPersonal\": \"miguel@gmail.com\",
                              \"facultad\": \"Derecho\",
                              \"tipoSolicitud\": \"Asesoría\",
                              \"cargo\": \"Egresado\",
                              \"campus\": \"Principal\",
                              \"dependencia\": \"Jurídica\"
                            }
                            """
                    )
                )
            )
        )
    public ResponseEntity<RegistroResponse> registrarAcompanamiento(@Valid @RequestBody AcompanamientoRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.registrarAcompanamiento(request));
    }

    @PostMapping("/atenciones")
    @Operation(summary = "Registrar atención")
    public ResponseEntity<RegistroResponse> registrarAtencion(@Valid @RequestBody AtencionRegistroRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.registrarAtencion(request));
    }
}
