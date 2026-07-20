package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaApreciacionesRequest {
    private Long idAtencion;
    private List<ApreciacionRequest> apreciaciones;

    @Data
    public static class ApreciacionRequest {
        private Integer idTipoApreciacion;
        private String descripcion;
    }
}
