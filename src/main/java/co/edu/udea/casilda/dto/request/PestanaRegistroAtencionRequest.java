package co.edu.udea.casilda.dto.request;

import lombok.Data;

@Data
public class PestanaRegistroAtencionRequest {
    private Long casoId;
    private Long idAtencion;
    private Integer idTipoServicio;
    private Integer idLugarEntrevista;
    private String archivoConsentimientoNombre;
    private String archivoConsentimientoTipo;
    private String archivoConsentimientoContenido;
}
