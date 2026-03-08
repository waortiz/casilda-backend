package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para un correo electrónico del solicitante
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorreoSolicitanteRequest {

    @NotNull(message = "El tipo de correo es obligatorio")
    private Integer tipoId;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;

    private String descripcion;
}
