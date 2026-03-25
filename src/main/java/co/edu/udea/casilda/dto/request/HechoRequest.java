package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO de entrada para registrar un hecho asociado al caso.
 */
@Data
public class HechoRequest {

    private String fecha;

    @NotBlank
    private String lugar;

    @NotBlank
    private String descripcion;
}
