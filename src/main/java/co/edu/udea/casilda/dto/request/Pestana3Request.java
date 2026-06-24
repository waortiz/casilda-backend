package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class Pestana3Request {
    private Long citaId;
    private Long idAtencion;
    private CasoAtencionRequest caso;
    private List<HechoRequest> hechos;
}
