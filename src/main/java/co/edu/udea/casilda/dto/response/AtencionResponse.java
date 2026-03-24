package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Response para Atencion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtencionResponse {
    
    private Long id;
    private LocalDateTime fecha;
    private Long citaId;
    private Integer tipoServicioId;
    private String tipoServicio;
    private Integer lugarEntrevistaId;
    private String lugarEntrevista;
    private Integer regimenId;
    private String regimen;
    private Integer epsId;
    private String eps;
    private Boolean logroAcuerdo;
}
