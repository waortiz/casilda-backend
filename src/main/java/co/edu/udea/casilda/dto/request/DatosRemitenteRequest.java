package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para capturar datos del remitente (solicitudes indirectas)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosRemitenteRequest {

    @NotBlank(message = "El primer nombre del remitente es obligatorio")
    @Size(min = 2, max = 100, message = "El primer nombre debe tener entre 2 y 100 caracteres")
    private String primerNombre;

    @Size(max = 100, message = "El segundo nombre no puede exceder 100 caracteres")
    private String segundoNombre;

    @NotBlank(message = "El primer apellido del remitente es obligatorio")
    @Size(min = 2, max = 100, message = "El primer apellido debe tener entre 2 y 100 caracteres")
    private String primerApellido;

    @Size(max = 100, message = "El segundo apellido no puede exceder 100 caracteres")
    private String segundoApellido;

    @NotNull(message = "El cargo del remitente es obligatorio")
    private Integer cargoId;
}
