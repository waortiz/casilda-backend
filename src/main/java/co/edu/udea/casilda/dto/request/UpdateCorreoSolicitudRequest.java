package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar correos de la persona solicitante.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCorreoSolicitudRequest {

    private Integer tipoId;

    private String tipo;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;


}