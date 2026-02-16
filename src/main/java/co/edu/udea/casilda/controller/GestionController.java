package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.ListaEditItemRequest;
import co.edu.udea.casilda.dto.request.ListaItemRequest;
import co.edu.udea.casilda.dto.request.UsuarioUpsertRequest;
import co.edu.udea.casilda.dto.response.ListasResponse;
import co.edu.udea.casilda.dto.response.UsuarioResponse;
import co.edu.udea.casilda.service.FormularioWorkflowService;
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
@Tag(name = "Gestión", description = "Gestión de usuarios y listas maestras desde frontend administrativo")
public class GestionController {

    private final FormularioWorkflowService formularioWorkflowService;

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(formularioWorkflowService.listarUsuarios());
    }

    @PostMapping("/usuarios")
    @Operation(summary = "Crear usuario")
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioUpsertRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.crearUsuario(request));
    }

    @PutMapping("/usuarios/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Long id,
                                                             @Valid @RequestBody UsuarioUpsertRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.actualizarUsuario(id, request));
    }

    @PatchMapping("/usuarios/{id}/estado")
    @Operation(summary = "Cambiar estado de usuario")
    public ResponseEntity<UsuarioResponse> toggleEstado(@PathVariable Long id) {
        return ResponseEntity.ok(formularioWorkflowService.toggleEstadoUsuario(id));
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        formularioWorkflowService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listas")
    @Operation(summary = "Obtener listas de configuración")
    public ResponseEntity<ListasResponse> obtenerListas() {
        return ResponseEntity.ok(formularioWorkflowService.obtenerListas());
    }

    @PostMapping("/listas/{nombreLista}/items")
    @Operation(summary = "Agregar ítem a lista")
    public ResponseEntity<List<String>> agregarItem(@PathVariable String nombreLista,
                                                    @Valid @RequestBody ListaItemRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.agregarItemLista(nombreLista, request));
    }

    @PutMapping("/listas/{nombreLista}/items")
    @Operation(summary = "Editar ítem de lista")
    public ResponseEntity<List<String>> editarItem(@PathVariable String nombreLista,
                                                   @Valid @RequestBody ListaEditItemRequest request) {
        return ResponseEntity.ok(formularioWorkflowService.editarItemLista(nombreLista, request));
    }

    @DeleteMapping("/listas/{nombreLista}/items")
    @Operation(summary = "Eliminar ítem de lista")
    public ResponseEntity<List<String>> eliminarItem(@PathVariable String nombreLista,
                                                     @RequestParam String item) {
        return ResponseEntity.ok(formularioWorkflowService.eliminarItemLista(nombreLista, item));
    }
}
