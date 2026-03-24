package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO con los datos propios de la atencion registrados desde el formulario.
 */
@Data
public class AtencionRegistroRequest {

    @NotNull
    private Long citaId;

    @NotNull
    private Integer idTipoServicio;

    @NotNull
    private Integer idMunicipioEntrevista;

    @NotNull
    private Integer idRegimen;

    @NotNull
    private Integer idEps;

    private Boolean logroAcuerdo;

    private String archivoConsentimientoNombre;
    private String archivoConsentimientoTipo;
    private String archivoConsentimientoContenido;
}
