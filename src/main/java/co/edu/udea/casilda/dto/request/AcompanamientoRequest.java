package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcompanamientoRequest {
    @NotBlank
    private String tipoReporte;

    @NotBlank
    private String primerNombre;

    @NotBlank
    private String primerApellido;

    @NotBlank
    private String tipoDocumento;

    @NotBlank
    private String numeroDocumento;

    @NotBlank
    private String identidadGenero;

    @NotBlank
    private String celular;

    @NotBlank
    private String correoInstitucional;

    @NotBlank
    private String correoPersonal;

    @NotBlank
    private String facultad;

    private String tipoSolicitud;
    private String cargo;
    private String campus;
    private String dependencia;
    private String remitentePrimerNombre;
    private String remitentePrimerApellido;
}
