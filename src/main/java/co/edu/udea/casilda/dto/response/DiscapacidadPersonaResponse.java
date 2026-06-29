package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscapacidadPersonaResponse {
    private Integer idSubTipoDiscapacidad;
    private String subTipo;
    private String tipo;
    private String descripcion;
}
