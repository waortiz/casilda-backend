package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaDatosPersonaRequest {
    private Long citaId;
    private Long idCaso;
    private PersonaAtencionRequest persona;
    private Integer idRegimen;
    private Integer idEps;
    private List<DiscapacidadPersonaRequest> discapacidades;
}
