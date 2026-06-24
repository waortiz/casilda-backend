package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class Pestana8Request {
    private Long citaId;
    private Long idAtencion;
    private List<RegistroOtroCasoRequest> otrosCasos;
    private List<ActualizarOtroCasoRequest> otrosCasosActualizar;
    private List<Long> otrosCasosEliminar;
}
