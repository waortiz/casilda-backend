package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Response para SeguimientoAtencion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoAtencionResponse {
    
    private Long id;
    private Long atencionId;
    private Integer tipoSeguimientoId;
    private String tipoSeguimiento;
    private LocalDateTime fecha;
    private Integer accionId;
    private String accion;
    private Integer actividadId;
    private String actividad;
    private String descripcion;
    private Integer estadoSeguimientoId;
    private String estadoSeguimiento;
    private Integer motivoEstadoSeguimientoId;
    private String motivoEstadoSeguimiento;
}
