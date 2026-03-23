package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Response para CompromisoPersonaAtendida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompromisoPersonaAtendidaResponse {
    private Long idatencion;
    private LocalDateTime fechacompromiso;
    private Integer idtipocompromiso;
    private String nombreTipoCompromiso;
}
