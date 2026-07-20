package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaAcuerdosRequest {
    private Long idAtencion;
    private Boolean logroAcuerdo;
    private List<RutaAtencionRequest> rutas;
    private List<RemisionAtencionRequest> remisiones;
}
