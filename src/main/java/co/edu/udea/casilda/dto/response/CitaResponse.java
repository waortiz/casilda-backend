package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Response para Cita de atención
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaResponse {

    private Long id;
    private Long solicitudId;
    private String codigoSolicitud;

    // Datos del solicitante
    private String nombreSolicitante;
    private String documento;

    // Datos de la cita
    private String fechaCita;
    private Integer idEstadoCita;
    private String estadoCita;
    private String motivoEstadoCita;
    private String observaciones;

    // Datos de la solicitud / caso
    private String tipoSolicitud;
    private String dependencia;
    private String facultad;
    private String campus;
    private String identidadGenero;

    // Datos de contacto
    private String celular;
    private String telefonoAlterno;
    private String correoInstitucional;
    private String correoPersonal;
}
