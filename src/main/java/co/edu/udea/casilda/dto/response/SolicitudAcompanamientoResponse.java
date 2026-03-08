package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO Response para solicitud de acompañamiento creada
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudAcompanamientoResponse {

    private Long id;
    private String codigo;
    private String tipoSolicitud;
    private String estado;
    private LocalDateTime fechaCreacion;

    // Para las tablas del componente de consulta
    private String dependencia;
    private String profesional;

    // Datos del solicitante (resumen)
    private String nombreSolicitante;
    private String documentoSolicitante;

    // Datos del solicitante (completos)
    private String tipoDocumento;
    private String numeroDocumento;
    private String fechaNacimiento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String identidadGenero;
    private String celular;
    private String telefonoAlterno;
    private String correoInstitucional;
    private String correoPersonal;

    // Datos del remitente (si aplica)
    private String nombreRemitente;
    private String remitentePrimerNombre;
    private String remitenteSegundoNombre;
    private String remitentePrimerApellido;
    private String remitenteSegundoApellido;
    private String remitenteCargo;
    private String remitenteCampus;
    private String remitenteDependencia;
    private String remitenteFacultad;
    private String remitenteFechaSolicitud;
    private String remitenteTipoDocumento;
    private String remitenteNumeroDocumento;
}
