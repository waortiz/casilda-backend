package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para un teléfono del solicitante
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoSolicitanteRequest {

    @NotBlank(message = "El tipo de teléfono es obligatorio")
    private String tipo; // Ej: "Personal", "Institucional", "Laboral"

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener entre 7 y 15 dígitos")
    private String telefono;

    private String descripcion;
}
