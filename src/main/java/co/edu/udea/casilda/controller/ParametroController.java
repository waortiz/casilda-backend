package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.repository.ParametroSistemaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para parámetros configurables del sistema
 */
@RestController
@RequestMapping("/parametros")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Parámetros", description = "Endpoints para gestión de parámetros configurables del sistema")
public class ParametroController {

    private static final String CLAVE_MAX_LLAMADAS = "MAX_LLAMADAS_CONTACTO";
    private static final int DEFAULT_MAX_LLAMADAS = 2;

    private final ParametroSistemaRepository parametroSistemaRepository;

    @GetMapping("/max-llamadas-contacto")
    @Operation(summary = "Obtener el número máximo de llamadas de contacto antes de asignación unilateral")
    public ResponseEntity<Integer> getMaxLlamadasContacto() {
        return ResponseEntity.ok(
            parametroSistemaRepository.findByClave(CLAVE_MAX_LLAMADAS)
                .map(p -> {
                    try { return Integer.parseInt(p.getValor()); } catch (NumberFormatException e) { return DEFAULT_MAX_LLAMADAS; }
                })
                .orElse(DEFAULT_MAX_LLAMADAS)
        );
    }

    @PutMapping("/max-llamadas-contacto")
    @Operation(summary = "Actualizar el número máximo de llamadas de contacto antes de asignación unilateral")
    public ResponseEntity<Integer> updateMaxLlamadasContacto(@RequestParam int valor) {
        parametroSistemaRepository.findByClave(CLAVE_MAX_LLAMADAS).ifPresentOrElse(
            p -> { p.setValor(String.valueOf(valor)); parametroSistemaRepository.save(p); },
            () -> {
                co.edu.udea.casilda.model.entity.ParametroSistema nuevo = new co.edu.udea.casilda.model.entity.ParametroSistema();
                nuevo.setClave(CLAVE_MAX_LLAMADAS);
                nuevo.setValor(String.valueOf(valor));
                parametroSistemaRepository.save(nuevo);
            }
        );
        return ResponseEntity.ok(valor);
    }
}
