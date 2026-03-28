package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de correo en la búsqueda de remitente/solicitante por documento.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorreoBusquedaResponse {
    private Integer tipoId;
    private String tipo;
    private String correo;
    private String descripcion;
}
