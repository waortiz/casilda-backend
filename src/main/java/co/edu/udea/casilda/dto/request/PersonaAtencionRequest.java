package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO con los datos de persona actualizados durante el registro de atencion.
 */
@Data
public class PersonaAtencionRequest {

    @NotNull
    private Integer idSexo;

    @NotNull
    private Integer idEtnia;

    private Integer idCiudadResidencia;

    private String direccionResidencia;
}