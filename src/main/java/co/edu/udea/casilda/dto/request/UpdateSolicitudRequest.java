package co.edu.udea.casilda.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Request para actualizar datos de una solicitud de acompañamiento
 */
@Data
@NoArgsConstructor
public class UpdateSolicitudRequest {
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String identidadGenero;
}
