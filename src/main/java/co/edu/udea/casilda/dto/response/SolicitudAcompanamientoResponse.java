package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO Response para solicitud de acompañamiento creada
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudAcompanamientoResponse {

    private Long id;
    private String codigo;
    private String tipoSolicitud;
    private String tipoReporte;
    private String estado;
    private LocalDateTime fechaCreacion;
    private String mensaje;
    
    // Datos del solicitante (resumen)
    private String nombreSolicitante;
    private String documentoSolicitante;
    
    // Datos del remitente (si aplica)
    private String nombreRemitente;
}
