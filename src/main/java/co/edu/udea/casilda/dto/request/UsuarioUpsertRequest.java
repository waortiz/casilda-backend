package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioUpsertRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    private String email;

    // La contraseña solo es requerida al crear. Al actualizar es opcional.
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Integer idRol;

    private Boolean activo;
}
