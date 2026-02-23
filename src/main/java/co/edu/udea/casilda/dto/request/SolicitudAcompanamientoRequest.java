package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Request para crear solicitud de acompañamiento
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAcompanamientoRequest {

    @NotNull(message = "El tipo de solicitud es obligatorio")
    private Integer tipoSolicitudId;

    @NotNull(message = "Los datos del solicitante son obligatorios")
    @Valid
    private DatosSolicitanteRequest datosSolicitante;

    @Valid
    private DatosRemitenteRequest datosRemitente;
}
