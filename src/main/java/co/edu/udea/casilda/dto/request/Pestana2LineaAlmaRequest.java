package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Pestana2LineaAlmaRequest {
    @NotNull(message = "El ID del registro es obligatorio")
    private Long id;

    @NotNull(message = "El vínculo con la UdeA es obligatorio")
    private Integer idVinculoUdeA;

    private Integer idUnidadAcademica;
    private Integer idPrograma;
    private Integer idUnidadAdministrativa;
    private Integer idCampus;
}
