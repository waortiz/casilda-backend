package co.edu.udea.casilda.dto.request;

import lombok.Data;

@Data
public class CasoDetalleUpdateRequest {
    private String remitentePrimerNombre;
    private String remitenteSegundoNombre;
    private String remitentePrimerApellido;
    private String remitenteSegundoApellido;
    private String cargo;
    private String dependencia;
    private String facultad;
    private String campus;
    private String pacientePrimerNombre;
    private String pacienteSegundoNombre;
    private String pacientePrimerApellido;
    private String pacienteSegundoApellido;
    private String documento;
    private Integer edad;
    private String celular;
    private String correoInst;
}
