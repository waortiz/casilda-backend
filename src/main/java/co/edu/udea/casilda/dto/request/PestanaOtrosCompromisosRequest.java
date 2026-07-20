package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaOtrosCompromisosRequest {
    private Long idAtencion;
    private List<CompromisoPersonaAtendidaRequest> persona;
    private List<CompromisoProfesionalRequest> profesional;
}
