package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO con el contexto organizacional y ubicación de la atención.
 * Contiene campos que pertenecen a la entidad Atencion pero no a AtencionRegistroRequest.
 */
@Data
public class AtencionContextoRequest {

    @NotNull
    private Integer idUnidadAdministrativa;

    @NotNull
    private Integer idCampus;

    @NotNull
    private Integer idUnidadAcademica;

    @NotNull
    private Integer idVinculoUniversidad;

    private String otroVinculo;

    @NotNull
    private Integer idPrograma;

    private Integer idEtnia;

    private Integer idCiudadResidencia;

    private String direccionResidencia;
}
