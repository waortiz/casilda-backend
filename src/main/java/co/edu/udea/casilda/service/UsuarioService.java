package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.UsuarioUpsertRequest;
import co.edu.udea.casilda.dto.response.UsuarioResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.Rol;
import co.edu.udea.casilda.model.entity.Usuario;
import co.edu.udea.casilda.repository.RoleRepository;
import co.edu.udea.casilda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestión CRUD de usuarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios del sistema
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponse> obtenerTodos() {
        log.info("Obteniendo todos los usuarios");
        return usuarioRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por su ID
     */
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        log.info("Obteniendo usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return convertirAResponse(usuario);
    }

    /**
     * Crea un nuevo usuario
     */
    @Transactional
    public UsuarioResponse crear(UsuarioUpsertRequest request) {
        log.info("Creando nuevo usuario con email: {}", request.getEmail());
        
        // Verificar que el email no exista
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + request.getEmail());
        }

        // Verificar que la contraseña esté presente al crear
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria al crear un usuario");
        }

        // Obtener el rol
        Rol rol = roleRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + request.getIdRol()));

        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getId());
        
        return convertirAResponse(usuarioGuardado);
    }

    /**
     * Actualiza un usuario existente
     */
    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioUpsertRequest request) {
        log.info("Actualizando usuario con ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // Verificar que el email no esté en uso por otro usuario
        usuarioRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            if (!u.getId().equals(id)) {
                throw new IllegalArgumentException("El email ya está en uso por otro usuario");
            }
        });

        // Obtener el rol
        Rol rol = roleRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + request.getIdRol()));

        // Actualizar campos
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setRol(rol);
        
        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        // Solo actualizar la contraseña si se proporciona una nueva
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente con ID: {}", usuarioActualizado.getId());
        
        return convertirAResponse(usuarioActualizado);
    }

    /**
     * Elimina un usuario por su ID
     */
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        usuarioRepository.delete(usuario);
        log.info("Usuario eliminado exitosamente con ID: {}", id);
    }

    /**
     * Activa o desactiva un usuario
     */
    @Transactional
    public UsuarioResponse cambiarEstado(Long id, Boolean activo) {
        log.info("Cambiando estado de usuario con ID: {} a activo: {}", id, activo);
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        usuario.setActivo(activo);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        
        log.info("Estado del usuario actualizado exitosamente");
        return convertirAResponse(usuarioActualizado);
    }

    /**
     * Convierte una entidad Usuario a UsuarioResponse
     */
    private UsuarioResponse convertirAResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .idRol(usuario.getRol().getId())
                .nombreRol(usuario.getRol().getNombre())
                .activo(usuario.getActivo())
                .fechaCreacion(usuario.getFechaCreacion())
                .fechaActualizacion(usuario.getFechaActualizacion())
                .build();
    }
}
