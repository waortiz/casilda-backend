package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO Request para cancelar una cita de atención
 */
@Data
public class CancelarCitaRequest {

    @NotNull
    private Integer idMotivoEstadoCita;

    private String observaciones;
}
