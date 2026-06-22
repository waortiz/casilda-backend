package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de teléfono en la búsqueda de remitente/solicitante por documento.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefonoBusquedaResponse {
    private Integer tipoId;
    private String tipo;
    private String telefono;

}
