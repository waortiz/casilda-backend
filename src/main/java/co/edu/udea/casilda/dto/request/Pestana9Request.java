package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class Pestana9Request {
    private Long citaId;
    private Long idAtencion;
    private List<MedidaProteccionRequest> medidas;
}
