package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoResponse {
    private String codigo;
    private String tipo;
    private Integer estadoActual;
    private Integer diasDesdeRecepcion;
    private String fechaActualizacion;
    private String detalle;
    private List<EtapaResponse> etapas;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EtapaResponse {
        private Integer id;
        private String nombre;
        private String icon;
    }
}
