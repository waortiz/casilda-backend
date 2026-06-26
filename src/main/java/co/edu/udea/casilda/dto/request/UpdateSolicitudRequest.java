package co.edu.udea.casilda.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private Integer tipoDocumentoId;
    private String numeroDocumento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

    private Integer identidadGeneroId;

    @Valid
    private List<UpdateCorreoSolicitudRequest> correos = new ArrayList<>();

    @Valid
    private List<UpdateTelefonoSolicitudRequest> telefonos = new ArrayList<>();

    private String remitentePrimerNombre;
    private String remitenteSegundoNombre;
    private String remitentePrimerApellido;
    private String remitenteSegundoApellido;
    private Integer remitenteCargoId;
    private Integer remitenteCampusId;
    private Integer remitenteDependenciaId;
    private Integer remitenteFacultadId;
    private Integer remitenteTipoDocumentoId;
    private String remitenteNumeroDocumento;

    private String observacionesTelefono;
    private String observacionesCorreo;

    private Integer medioSolicitudId;
}
