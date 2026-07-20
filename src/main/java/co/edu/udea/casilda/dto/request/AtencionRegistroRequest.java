package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO con los datos propios de la atencion registrados desde el formulario.
 */
@Data
public class AtencionRegistroRequest {

    @NotNull
    private Long casoId;

    private Long idAtencion;

    private Integer idEstadoAtencion;

    private Integer idTipoServicio;

    private Integer idLugarEntrevista;

    private Integer idRegimen;

    private Integer idEps;

    private Boolean logroAcuerdo;

    private String archivoConsentimientoNombre;
    private String archivoConsentimientoTipo;
    private String archivoConsentimientoContenido;

    private String observacionesTelefono;
    private String observacionesCorreo;
}
