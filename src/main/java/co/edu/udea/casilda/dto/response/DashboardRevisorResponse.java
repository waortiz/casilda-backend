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
public class DashboardRevisorResponse {
    private Stats stats;
    private List<CasoTarjeta> quejas;
    private List<CasoTarjeta> acompanamientos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Stats {
        private Integer total;
        private Integer mujeres;
        private Integer hombres;
        private Integer estudiantes;
        private Integer docentes;
        private Integer administrativos;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CasoTarjeta {
        private String id;
        private String fecha;
        private String victima;
        private String estado;
    }
}
