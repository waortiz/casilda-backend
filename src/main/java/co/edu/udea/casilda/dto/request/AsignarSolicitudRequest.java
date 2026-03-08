package co.edu.udea.casilda.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO Request para asignar profesionales a una solicitud de acompañamiento
 */
@Data
@NoArgsConstructor
public class AsignarSolicitudRequest {
    private List<Long> profesionalesIds;
    private String tipoAsignacion;
    private String servicio;
    private String observaciones;
    private String fechaReparto;
}
