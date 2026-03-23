package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Response para CompromisoProfesional
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompromisoProfesionalResponse {
    private Long idatencion;
    private LocalDateTime fechacompromiso;
    private Integer idgrupoprofesional;
    private String nombreGrupoProfesional;
    private Integer idtipocompromiso;
    private String nombreTipoCompromiso;
}
