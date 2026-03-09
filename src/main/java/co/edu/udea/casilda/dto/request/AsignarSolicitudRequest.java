package co.edu.udea.casilda.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Request para asignar un grupo profesional a una solicitud de acompañamiento
 */
@Data
@NoArgsConstructor
public class AsignarSolicitudRequest {
    private Integer grupoProfesionalId;
    private Integer idTipoAsignacion;
    private Integer idTipoServicio;
    private String observaciones;
    private String fechaReparto;
}
