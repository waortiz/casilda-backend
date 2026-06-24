package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class Pestana6Request {
    private Long citaId;
    private Long idAtencion;
    private Boolean logroAcuerdo;
    private List<RutaAtencionRequest> rutas;
    private List<RemisionAtencionRequest> remisiones;
}
