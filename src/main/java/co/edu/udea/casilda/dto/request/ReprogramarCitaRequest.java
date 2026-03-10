package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO Request para reprogramar una cita de atención
 */
@Data
public class ReprogramarCitaRequest {

    @NotBlank
    private String fechaCita;  // formato yyyy-MM-dd

    @NotBlank
    private String horaCita;   // formato HH:mm

    private Integer idMotivoEstadoCita;
    private String observaciones;
}
