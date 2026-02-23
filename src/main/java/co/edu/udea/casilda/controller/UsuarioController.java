package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.UsuarioUpsertRequest;
import co.edu.udea.casilda.dto.response.UsuarioResponse;
import co.edu.udea.casilda.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de usuarios
 * Solo accesible para usuarios con rol ADMIN
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para gestión de usuarios del sistema")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Obtiene todos los usuarios
     */
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    /**
     * Obtiene un usuario por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    /**
     * Crea un nuevo usuario
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioUpsertRequest request) {
        UsuarioResponse usuario = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    /**
     * Actualiza un usuario existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario existente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpsertRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    /**
     * Elimina un usuario
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente"));
    }

    /**
     * Cambia el estado (activo/inactivo) de un usuario
     */
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un usuario")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        Boolean activo = body.get("activo");
        if (activo == null) {
            throw new IllegalArgumentException("El campo 'activo' es requerido");
        }
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, activo));
    }
}
