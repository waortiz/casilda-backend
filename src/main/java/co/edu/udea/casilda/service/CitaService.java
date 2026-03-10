package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.CancelarCitaRequest;
import co.edu.udea.casilda.dto.request.ReprogramarCitaRequest;
import co.edu.udea.casilda.dto.response.CitaResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.*;
import co.edu.udea.casilda.model.enums.TipoCorreoEnum;
import co.edu.udea.casilda.model.enums.TipoTelefonoEnum;
import co.edu.udea.casilda.model.enums.EstadoCitaEnum;
import co.edu.udea.casilda.repository.CitaRepository;
import co.edu.udea.casilda.repository.EstadoCitaRepository;
import co.edu.udea.casilda.repository.MotivoEstadoCitaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de citas de atención
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CitaService {

    private final CitaRepository citaRepository;
    private final EstadoCitaRepository estadoCitaRepository;
    private final MotivoEstadoCitaRepository motivoEstadoCitaRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Transactional(readOnly = true)
    public List<CitaResponse> listarTodasLasCitas() {
        log.info("Listando todas las citas");
        return citaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CitaResponse reprogramar(Long id, ReprogramarCitaRequest req) {
        log.info("Reprogramando cita id={}", id);
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        LocalDateTime nuevaFecha = LocalDateTime.parse(req.getFechaCita() + "T" + req.getHoraCita());
        cita.setFecha(nuevaFecha);
        EstadoCita estadoReprogramada = estadoCitaRepository.findById(EstadoCitaEnum.REPROGRAMADA.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita 'Reprogramada' no encontrado"));
        cita.setEstadoCita(estadoReprogramada);
        if (req.getIdMotivoEstadoCita() != null) {
            MotivoEstadoCita motivo = motivoEstadoCitaRepository.findById(req.getIdMotivoEstadoCita())
                    .orElseThrow(() -> new ResourceNotFoundException("Motivo de estado de cita no encontrado con ID: " + req.getIdMotivoEstadoCita()));
            cita.setMotivoEstadoCita(motivo);
        }
        cita.setObservaciones(req.getObservaciones());
        return mapToResponse(citaRepository.save(cita));
    }

    @Transactional
    public CitaResponse cancelar(Long id, CancelarCitaRequest req) {
        log.info("Cancelando cita id={}", id);
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        EstadoCita estadoCancelada = estadoCitaRepository.findById(EstadoCitaEnum.CANCELADA.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita 'Cancelada' no encontrado"));
        cita.setEstadoCita(estadoCancelada);
        if (req.getIdMotivoEstadoCita() != null) {
            MotivoEstadoCita motivo = motivoEstadoCitaRepository.findById(req.getIdMotivoEstadoCita())
                    .orElseThrow(() -> new ResourceNotFoundException("Motivo de estado de cita no encontrado con ID: " + req.getIdMotivoEstadoCita()));
            cita.setMotivoEstadoCita(motivo);
        }
        cita.setObservaciones(req.getObservaciones());
        return mapToResponse(citaRepository.save(cita));
    }

    // ─── Mapeo ──────────────────────────────────────────────────────────────────

    private CitaResponse mapToResponse(Cita cita) {
        SolicitudAtencion sa = cita.getSolicitudAtencion();
        Caso caso = sa.getCaso();
        Persona persona = caso.getPersona();

        // Correos
        String correoInstitucional = "";
        String correoPersonal = "";
        if (persona.getCorreos() != null) {
            for (CorreoPersona cp : persona.getCorreos()) {
                if (cp.getTipoCorreo() == null || cp.getCorreo() == null) continue;
                Integer idTipo = cp.getTipoCorreo().getId();
                if (TipoCorreoEnum.INSTITUCIONAL.getId().equals(idTipo) && correoInstitucional.isEmpty()) {
                    correoInstitucional = cp.getCorreo();
                } else if (TipoCorreoEnum.PERSONAL.getId().equals(idTipo) && correoPersonal.isEmpty()) {
                    correoPersonal = cp.getCorreo();
                } else if (correoInstitucional.isEmpty()) {
                    correoInstitucional = cp.getCorreo();
                } else if (correoPersonal.isEmpty()) {
                    correoPersonal = cp.getCorreo();
                }
            }
        }

        // Teléfonos
        String celular = "";
        String telefonoAlterno = "";
        if (persona.getTelefonos() != null) {
            for (TelefonoPersona tp : persona.getTelefonos()) {
                if (tp.getTipoTelefono() == null || tp.getTelefono() == null) continue;
                Integer idTipo = tp.getTipoTelefono().getId();
                if ((TipoTelefonoEnum.CELULAR.getId().equals(idTipo) || TipoTelefonoEnum.WHATSAPP.getId().equals(idTipo))
                        && celular.isEmpty()) {
                    celular = tp.getTelefono();
                } else if ((TipoTelefonoEnum.FIJO.getId().equals(idTipo) || TipoTelefonoEnum.OFICINA.getId().equals(idTipo))
                        && telefonoAlterno.isEmpty()) {
                    telefonoAlterno = tp.getTelefono();
                } else if (celular.isEmpty()) {
                    celular = tp.getTelefono();
                } else if (telefonoAlterno.isEmpty()) {
                    telefonoAlterno = tp.getTelefono();
                }
            }
        }

        // Para solicitudes indirectas, los datos de ubicación vienen de la remisión
        Remision remision = sa.getRemision();
        boolean esIndirecta = remision != null;

        String dependenciaNombre = esIndirecta
                ? (remision.getDependencia() != null ? remision.getDependencia().getNombre() : null)
                : (caso.getDependencia() != null ? caso.getDependencia().getNombre() : null);
        String facultadNombre = esIndirecta
                ? (remision.getFacultad() != null ? remision.getFacultad().getNombre() : null)
                : (caso.getFacultad() != null ? caso.getFacultad().getNombre() : null);
        String campusNombre = esIndirecta
                ? (remision.getCampus() != null ? remision.getCampus().getNombre() : null)
                : (caso.getCampus() != null ? caso.getCampus().getNombre() : null);

        return CitaResponse.builder()
                .id(cita.getId())
                .solicitudId(sa.getId())
                .codigoSolicitud(caso.getCodigo())
                .nombreSolicitante(persona.getNombreCompleto().trim())
                .documento(persona.getNumeroDocumento())
                .fechaCita(cita.getFecha() != null ? cita.getFecha().format(FORMATTER) : null)
                .idEstadoCita(cita.getEstadoCita() != null ? cita.getEstadoCita().getId() : null)
                .estadoCita(cita.getEstadoCita() != null ? cita.getEstadoCita().getNombre() : null)
                .motivoEstadoCita(cita.getMotivoEstadoCita() != null ? cita.getMotivoEstadoCita().getNombre() : null)
                .observaciones(cita.getObservaciones())
                .tipoSolicitud(sa.getTipoSolicitud() != null ? sa.getTipoSolicitud().getNombre() : null)
                .dependencia(dependenciaNombre)
                .facultad(facultadNombre)
                .campus(campusNombre)
                .identidadGenero(caso.getIdentidadGenero() != null ? caso.getIdentidadGenero().getNombre() : null)
                .celular(celular)
                .telefonoAlterno(telefonoAlterno)
                .correoInstitucional(correoInstitucional)
                .correoPersonal(correoPersonal)
                .build();
    }
}
