package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El tipo de correo es obligatorio")
    private String tipo; // Ej: "Personal", "Institucional"

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;

    private String descripcion;
}
