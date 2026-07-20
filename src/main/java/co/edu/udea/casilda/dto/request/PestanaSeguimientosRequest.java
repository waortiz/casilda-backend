package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaSeguimientosRequest {
    private Long idAtencion;
    private List<SeguimientoAtencionRequest> seguimientos;
}
