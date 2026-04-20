package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar teléfonos de la persona solicitante.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTelefonoSolicitudRequest {

    private Integer tipoId;

    private String tipo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener entre 7 y 15 dígitos")
    private String telefono;

    @NotBlank(message = "La descripción del teléfono es obligatoria")
    private String descripcion;
}