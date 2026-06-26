package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.AtencionAphRequest;
import co.edu.udea.casilda.dto.request.ContactoLineaAlmaRequest;
import co.edu.udea.casilda.dto.request.RegistroLineaAlmaRequest;
import co.edu.udea.casilda.dto.request.RemisionRegistroAlmaRequest;
import co.edu.udea.casilda.dto.response.AtencionAphResponse;
import co.edu.udea.casilda.dto.response.ContactoLineaAlmaResponse;
import co.edu.udea.casilda.dto.response.RegistroLineaAlmaResponse;
import co.edu.udea.casilda.dto.response.RemisionRegistroAlmaResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.*;
import co.edu.udea.casilda.model.enums.TipoServicioEnum;
import co.edu.udea.casilda.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LineaAlmaService {

    private final RegistroLineaAlmaRepository registroLineaAlmaRepository;
    private final AtencionAphRepository atencionAphRepository;
    private final RemisionRegistroAlmaRepository remisionRegistroAlmaRepository;
    private final ContactoLineaAlmaRepository contactoLineaAlmaRepository;

    private final PersonaRepository personaRepository;
    private final TipoReporteAlmaRepository tipoReporteAlmaRepository;
    private final CanalContactoRepository canalContactoRepository;
    private final UsuarioRepository usuarioRepository;
    private final GrupoProfesionalRepository grupoProfesionalRepository;
    private final TipoServicioRepository tipoServicioRepository;
    private final LugarEntrevistaRepository lugarEntrevistaRepository;
    private final IdentidadGeneroRepository identidadGeneroRepository;
    private final OrientacionSexualRepository orientacionSexualRepository;
    private final EtniaRepository etniaRepository;
    private final MunicipioRepository municipioRepository;
    private final VinculoUdeARepository vinculoUdeARepository;
    private final SubVinculoUdeARepository subVinculoUdeARepository;
    private final FacultadEscuelaInstitutoRepository facultadRepository;
    private final ProgramaRepository programaRepository;
    private final DependenciaRepository dependenciaRepository;
    private final CampusRepository campusRepository;
    private final ProtocoloAphRepository protocoloAphRepository;
    private final ResultadoTriageRepository resultadoTriageRepository;
    private final TipoRemisionRepository tipoRemisionRepository;
    private final ResultadoContactoTelefonicoRepository resultadoContactoTelefonicoRepository;
    private final ActorRemitenteRepository actorRemitenteRepository;

    @Transactional
    public RegistroLineaAlmaResponse crearRegistro(RegistroLineaAlmaRequest request) {
        log.info("Creando registro de Línea ALMA para persona ID: {}", request.getIdPersona());

        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

        RegistroLineaAlma registro = new RegistroLineaAlma();
        registro.setPersona(personaRepository.findById(request.getIdPersona())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + request.getIdPersona())));
        registro.setTipoReporte(tipoReporteAlmaRepository.findById(request.getIdTipoReporte())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo reporte ALMA no encontrado con ID: " + request.getIdTipoReporte())));
        registro.setCanalContacto(canalContactoRepository.findById(request.getIdCanalContacto())
                .orElseThrow(() -> new ResourceNotFoundException("Canal de contacto no encontrado con ID: " + request.getIdCanalContacto())));
        if (request.getIdQuienRemite() != null) {
            registro.setQuienRemite(actorRemitenteRepository.findById(request.getIdQuienRemite())
                    .orElseThrow(() -> new ResourceNotFoundException("Actor remitente no encontrado con ID: " + request.getIdQuienRemite())));
        }
        registro.setFechaHoraAtencion(LocalDateTime.now());
        registro.setPersonaAtiende(grupoProfesionalRepository.findById(request.getIdPersonaAtiende().intValue())
                .orElseThrow(() -> new ResourceNotFoundException("Grupo profesional no encontrado con ID: " + request.getIdPersonaAtiende())));
        registro.setTipoServicio(tipoServicioRepository.findById(TipoServicioEnum.ATENCION_APH.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo servicio " + TipoServicioEnum.ATENCION_APH.getNombre()
                        + " no encontrado con ID: " + TipoServicioEnum.ATENCION_APH.getId())));
        registro.setPersonaRegistra(usuarioRepository.findById(request.getIdPersonaRegistra())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario registra no encontrado con ID: " + request.getIdPersonaRegistra())));

        if (request.getIdLugarEntrevista() != null) {
            registro.setLugarEntrevista(lugarEntrevistaRepository.findById(request.getIdLugarEntrevista())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar entrevista no encontrado con ID: " + request.getIdLugarEntrevista())));
        }

        registro.setIdentidadGenero(identidadGeneroRepository.findById(request.getIdIdentidadGenero())
                .orElseThrow(() -> new ResourceNotFoundException("Identidad de género no encontrada con ID: " + request.getIdIdentidadGenero())));

        if (request.getIdOrientacionSexual() != null) {
            registro.setOrientacionSexual(orientacionSexualRepository.findById(request.getIdOrientacionSexual())
                    .orElseThrow(() -> new ResourceNotFoundException("Orientación sexual no encontrada con ID: " + request.getIdOrientacionSexual())));
        }
        if (request.getIdEtnia() != null) {
            registro.setEtnia(etniaRepository.findById(request.getIdEtnia())
                    .orElseThrow(() -> new ResourceNotFoundException("Etnia no encontrada con ID: " + request.getIdEtnia())));
        }
        if (request.getIdCiudadResidencia() != null) {
            registro.setCiudadResidencia(municipioRepository.findById(request.getIdCiudadResidencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + request.getIdCiudadResidencia())));
        }

        registro.setDireccionResidencia(request.getDireccionResidencia());

        if (request.getIdVinculoUdeA() != null) {
            registro.setVinculoUdeA(vinculoUdeARepository.findById(request.getIdVinculoUdeA())
                    .orElseThrow(() -> new ResourceNotFoundException("Vínculo UdeA no encontrado con ID: " + request.getIdVinculoUdeA())));
        }
        if (request.getIdFacultad() != null) {
            registro.setFacultad(facultadRepository.findById(request.getIdFacultad())
                    .orElseThrow(() -> new ResourceNotFoundException("Facultad no encontrada con ID: " + request.getIdFacultad())));
        }
        if (request.getIdPrograma() != null) {
            registro.setPrograma(programaRepository.findById(request.getIdPrograma())
                    .orElseThrow(() -> new ResourceNotFoundException("Programa no encontrado con ID: " + request.getIdPrograma())));
        }
        if (request.getIdDependencia() != null) {
            registro.setDependencia(dependenciaRepository.findById(request.getIdDependencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Dependencia no encontrada con ID: " + request.getIdDependencia())));
        }
        if (request.getIdCampus() != null) {
            registro.setCampus(campusRepository.findById(request.getIdCampus())
                    .orElseThrow(() -> new ResourceNotFoundException("Campus no encontrado con ID: " + request.getIdCampus())));
        }

        registro.setUsuarioCreacion(usuarioAutenticado);
        registro.setUsuarioActualizacion(usuarioAutenticado);

        registro = registroLineaAlmaRepository.save(registro);

        if (request.getAtencionAph() != null) {
            atencionAphRepository.save(construirAtencionAph(registro, request.getAtencionAph(), usuarioAutenticado));
        }

        if (request.getRemisiones() != null && !request.getRemisiones().isEmpty()) {
            for (RemisionRegistroAlmaRequest remisionRequest : request.getRemisiones()) {
                RemisionRegistroAlma remision = new RemisionRegistroAlma();
                remision.setIdregistrolinealma(registro.getId());
                remision.setIdtiporemision(remisionRequest.getIdTipoRemision());
                remision.setRegistroLineaAlma(registro);
                remision.setTipoRemision(tipoRemisionRepository.findById(remisionRequest.getIdTipoRemision())
                        .orElseThrow(() -> new ResourceNotFoundException("Tipo remisión no encontrado con ID: " + remisionRequest.getIdTipoRemision())));
                remision.setCual(remisionRequest.getCual());
                remision.setFecha(remisionRequest.getFecha());
                remisionRegistroAlmaRepository.save(remision);
            }
        }

        return obtenerPorId(registro.getId());
    }

    @Transactional
    public RegistroLineaAlmaResponse registrarPestana(int tabIndex, RegistroLineaAlmaRequest request) {
        log.info("Registrando pestaña {} para registro ID: {}", tabIndex, request.getId());
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

        RegistroLineaAlma registro;
        if (request.getId() != null) {
            registro = registroLineaAlmaRepository.findById(request.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado con ID: " + request.getId()));
        } else {
            registro = new RegistroLineaAlma();
            registro.setFechaHoraAtencion(request.getFechaHoraAtencion() != null ? request.getFechaHoraAtencion() : LocalDateTime.now());
            registro.setUsuarioCreacion(usuarioAutenticado);
            registro.setPersona(personaRepository.findById(request.getIdPersona())
                    .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + request.getIdPersona())));
            registro.setTipoReporte(tipoReporteAlmaRepository.findById(request.getIdTipoReporte())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo reporte ALMA no encontrado con ID: " + request.getIdTipoReporte())));
            registro.setCanalContacto(canalContactoRepository.findById(request.getIdCanalContacto())
                    .orElseThrow(() -> new ResourceNotFoundException("Canal de contacto no encontrado con ID: " + request.getIdCanalContacto())));
            if (request.getIdQuienRemite() != null) {
                registro.setQuienRemite(actorRemitenteRepository.findById(request.getIdQuienRemite()).orElse(null));
            }
            registro.setPersonaAtiende(grupoProfesionalRepository.findById(request.getIdPersonaAtiende().intValue())
                    .orElseThrow(() -> new ResourceNotFoundException("Grupo profesional no encontrado con ID: " + request.getIdPersonaAtiende())));
            registro.setTipoServicio(tipoServicioRepository.findById(TipoServicioEnum.ATENCION_APH.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo servicio " + TipoServicioEnum.ATENCION_APH.getNombre()
                            + " no encontrado con ID: " + TipoServicioEnum.ATENCION_APH.getId())));
            registro.setPersonaRegistra(usuarioRepository.findById(request.getIdPersonaRegistra())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario registra no encontrado con ID: " + request.getIdPersonaRegistra())));
            registro.setIdentidadGenero(identidadGeneroRepository.findById(request.getIdIdentidadGenero())
                    .orElseThrow(() -> new ResourceNotFoundException("Identidad de género no encontrada con ID: " + request.getIdIdentidadGenero())));
        }

        registro.setUsuarioActualizacion(usuarioAutenticado);

        switch (tabIndex) {
            case 0:
                registro.setPersona(personaRepository.findById(request.getIdPersona())
                        .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + request.getIdPersona())));
                registro.setTipoReporte(tipoReporteAlmaRepository.findById(request.getIdTipoReporte())
                        .orElseThrow(() -> new ResourceNotFoundException("Tipo reporte ALMA no encontrado con ID: " + request.getIdTipoReporte())));
                registro.setCanalContacto(canalContactoRepository.findById(request.getIdCanalContacto())
                        .orElseThrow(() -> new ResourceNotFoundException("Canal de contacto no encontrado con ID: " + request.getIdCanalContacto())));
                if (request.getIdQuienRemite() != null) {
                    registro.setQuienRemite(actorRemitenteRepository.findById(request.getIdQuienRemite())
                            .orElseThrow(() -> new ResourceNotFoundException("Actor remitente no encontrado con ID: " + request.getIdQuienRemite())));
                } else {
                    registro.setQuienRemite(null);
                }
                registro.setPersonaAtiende(grupoProfesionalRepository.findById(request.getIdPersonaAtiende().intValue())
                        .orElseThrow(() -> new ResourceNotFoundException("Grupo profesional no encontrado con ID: " + request.getIdPersonaAtiende())));
                registro.setTipoServicio(tipoServicioRepository.findById(TipoServicioEnum.ATENCION_APH.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tipo servicio " + TipoServicioEnum.ATENCION_APH.getNombre()
                                + " no encontrado con ID: " + TipoServicioEnum.ATENCION_APH.getId())));
                registro.setPersonaRegistra(usuarioRepository.findById(request.getIdPersonaRegistra())
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario registra no encontrado con ID: " + request.getIdPersonaRegistra())));
                if (registro.getId() == null) {
                    registro.setIdentidadGenero(identidadGeneroRepository.findById(request.getIdIdentidadGenero())
                            .orElseThrow(() -> new ResourceNotFoundException("Identidad de género no encontrada con ID: " + request.getIdIdentidadGenero())));
                }
                break;

            case 1:
                registro.setIdentidadGenero(identidadGeneroRepository.findById(request.getIdIdentidadGenero())
                        .orElseThrow(() -> new ResourceNotFoundException("Identidad de género no encontrada con ID: " + request.getIdIdentidadGenero())));
                if (request.getIdOrientacionSexual() != null) {
                    registro.setOrientacionSexual(orientacionSexualRepository.findById(request.getIdOrientacionSexual()).orElse(null));
                } else {
                    registro.setOrientacionSexual(null);
                }
                if (request.getIdEtnia() != null) {
                    registro.setEtnia(etniaRepository.findById(request.getIdEtnia()).orElse(null));
                } else {
                    registro.setEtnia(null);
                }
                if (request.getIdCiudadResidencia() != null) {
                    registro.setCiudadResidencia(municipioRepository.findById(request.getIdCiudadResidencia()).orElse(null));
                } else {
                    registro.setCiudadResidencia(null);
                }
                registro.setDireccionResidencia(request.getDireccionResidencia());
                if (request.getFechaNacimiento() != null) {
                    Persona pers = registro.getPersona();
                    if (pers != null) {
                        pers.setFechaNacimiento(request.getFechaNacimiento());
                        personaRepository.save(pers);
                    }
                }
                break;

            case 2:
                if (request.getIdVinculoUdeA() != null) {
                    registro.setVinculoUdeA(vinculoUdeARepository.findById(request.getIdVinculoUdeA()).orElse(null));
                } else {
                    registro.setVinculoUdeA(null);
                }
                if (request.getIdFacultad() != null) {
                    registro.setFacultad(facultadRepository.findById(request.getIdFacultad()).orElse(null));
                } else {
                    registro.setFacultad(null);
                }
                if (request.getIdPrograma() != null) {
                    registro.setPrograma(programaRepository.findById(request.getIdPrograma()).orElse(null));
                } else {
                    registro.setPrograma(null);
                }
                if (request.getIdDependencia() != null) {
                    registro.setDependencia(dependenciaRepository.findById(request.getIdDependencia()).orElse(null));
                } else {
                    registro.setDependencia(null);
                }
                if (request.getIdCampus() != null) {
                    registro.setCampus(campusRepository.findById(request.getIdCampus()).orElse(null));
                } else {
                    registro.setCampus(null);
                }
                break;

            case 3:
                if (request.getAtencionAph() != null) {
                    atencionAphRepository.save(construirAtencionAph(registro, request.getAtencionAph(), usuarioAutenticado));
                }
                break;

            case 4:
                List<ContactoLineaAlma> existingContactos = contactoLineaAlmaRepository.findByRegistroLineaAlmaIdOrderByFechaCreacionDesc(registro.getId());
                contactoLineaAlmaRepository.deleteAll(existingContactos);

                if (request.getContactos() != null && !request.getContactos().isEmpty()) {
                    for (ContactoLineaAlmaRequest cReq : request.getContactos()) {
                        ContactoLineaAlma contacto = new ContactoLineaAlma();
                        contacto.setRegistroLineaAlma(registro);
                        contacto.setFecha(cReq.getFecha() != null ? cReq.getFecha() : LocalDateTime.now());
                        contacto.setResultado(resultadoContactoTelefonicoRepository.findById(cReq.getIdResultado())
                                .orElseThrow(() -> new ResourceNotFoundException("Resultado contacto no encontrado con ID: " + cReq.getIdResultado())));
                        contacto.setUsuarioCreacion(usuarioAutenticado);
                        contacto.setUsuarioActualizacion(usuarioAutenticado);
                        contactoLineaAlmaRepository.save(contacto);
                    }
                }
                break;

            case 5:
                List<RemisionRegistroAlma> existingRemisiones = remisionRegistroAlmaRepository.findByIdregistrolinealmaOrderByFechaDesc(registro.getId());
                remisionRegistroAlmaRepository.deleteAll(existingRemisiones);

                if (request.getRemisiones() != null && !request.getRemisiones().isEmpty()) {
                    for (RemisionRegistroAlmaRequest remisionRequest : request.getRemisiones()) {
                        RemisionRegistroAlma remision = new RemisionRegistroAlma();
                        remision.setIdregistrolinealma(registro.getId());
                        remision.setIdtiporemision(remisionRequest.getIdTipoRemision());
                        remision.setRegistroLineaAlma(registro);
                        remision.setTipoRemision(tipoRemisionRepository.findById(remisionRequest.getIdTipoRemision())
                                .orElseThrow(() -> new ResourceNotFoundException("Tipo remisión no encontrado con ID: " + remisionRequest.getIdTipoRemision())));
                        remision.setCual(remisionRequest.getCual());
                        remision.setFecha(remisionRequest.getFecha());
                        remisionRegistroAlmaRepository.save(remision);
                    }
                }
                break;

            default:
                break;
        }

        registro = registroLineaAlmaRepository.save(registro);
        return obtenerPorId(registro.getId());
    }

    @Transactional(readOnly = true)
    public RegistroLineaAlmaResponse obtenerPorId(Long id) {
        RegistroLineaAlma registro = registroLineaAlmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro Línea ALMA no encontrado con ID: " + id));
        AtencionAph atencionAph = atencionAphRepository.findByRegistroLineaAlmaId(id).orElse(null);
        List<RemisionRegistroAlma> remisiones = remisionRegistroAlmaRepository.findByIdregistrolinealmaOrderByFechaDesc(id);
        return mapToResponse(registro, atencionAph, remisiones);
    }

    @Transactional(readOnly = true)
    public List<RegistroLineaAlmaResponse> listarRegistros() {
        List<RegistroLineaAlmaResponse> response = new ArrayList<>();
        for (RegistroLineaAlma registro : registroLineaAlmaRepository.findAllByOrderByFechaCreacionDesc()) {
            AtencionAph atencionAph = atencionAphRepository.findByRegistroLineaAlmaId(registro.getId()).orElse(null);
            List<RemisionRegistroAlma> remisiones = remisionRegistroAlmaRepository.findByIdregistrolinealmaOrderByFechaDesc(registro.getId());
            response.add(mapToResponse(registro, atencionAph, remisiones));
        }
        return response;
    }

    @Transactional
    public ContactoLineaAlmaResponse registrarContacto(Long idRegistro, ContactoLineaAlmaRequest request) {
        RegistroLineaAlma registro = registroLineaAlmaRepository.findById(idRegistro)
                .orElseThrow(() -> new ResourceNotFoundException("Registro Línea ALMA no encontrado con ID: " + idRegistro));

        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

        ContactoLineaAlma contacto = new ContactoLineaAlma();
        contacto.setRegistroLineaAlma(registro);
        contacto.setFecha(request.getFecha() != null ? request.getFecha() : LocalDateTime.now());
        contacto.setResultado(resultadoContactoTelefonicoRepository.findById(request.getIdResultado())
                .orElseThrow(() -> new ResourceNotFoundException("Resultado contacto no encontrado con ID: " + request.getIdResultado())));
        contacto.setUsuarioCreacion(usuarioAutenticado);
        contacto.setUsuarioActualizacion(usuarioAutenticado);

        contacto = contactoLineaAlmaRepository.save(contacto);
        return mapContacto(contacto);
    }

    @Transactional(readOnly = true)
    public List<ContactoLineaAlmaResponse> listarContactos(Long idRegistro) {
        registroLineaAlmaRepository.findById(idRegistro)
                .orElseThrow(() -> new ResourceNotFoundException("Registro Línea ALMA no encontrado con ID: " + idRegistro));
        return contactoLineaAlmaRepository.findByRegistroLineaAlmaIdOrderByFechaCreacionDesc(idRegistro)
                .stream()
                .map(this::mapContacto)
                .toList();
    }

    private AtencionAph construirAtencionAph(RegistroLineaAlma registro, AtencionAphRequest request, Usuario usuarioAutenticado) {
        AtencionAph atencionAph = atencionAphRepository.findByRegistroLineaAlmaId(registro.getId()).orElse(new AtencionAph());
        atencionAph.setRegistroLineaAlma(registro);
        atencionAph.setFechaHora(request.getFechaHora());
        atencionAph.setProtocoloAph(protocoloAphRepository.findById(request.getIdProtocoloAph())
                .orElseThrow(() -> new ResourceNotFoundException("Protocolo APH no encontrado con ID: " + request.getIdProtocoloAph())));
        atencionAph.setPracticoTriage(Boolean.TRUE.equals(request.getPracticoTriage()));
        if (request.getIdResultadoTriage() != null) {
            atencionAph.setResultadoTriage(resultadoTriageRepository.findById(request.getIdResultadoTriage())
                    .orElseThrow(() -> new ResourceNotFoundException("Resultado triage no encontrado con ID: " + request.getIdResultadoTriage())));
        } else {
            atencionAph.setResultadoTriage(null);
        }
        atencionAph.setNotaAph(request.getNotaAph());
        atencionAph.setMotivoNoTriage(request.getMotivoNoTriage());
        atencionAph.setAceptaPsicologia(Boolean.TRUE.equals(request.getAceptaPsicologia()));
        atencionAph.setRequiereRemision(Boolean.TRUE.equals(request.getRequiereRemision()));
        if (atencionAph.getId() == null) {
            atencionAph.setUsuarioCreacion(usuarioAutenticado);
        }
        atencionAph.setUsuarioActualizacion(usuarioAutenticado);
        return atencionAph;
    }

    private RegistroLineaAlmaResponse mapToResponse(RegistroLineaAlma registro,
                                                    AtencionAph atencionAph,
                                                    List<RemisionRegistroAlma> remisiones) {
        return RegistroLineaAlmaResponse.builder()
                .id(registro.getId())
                .idPersona(registro.getPersona() != null ? registro.getPersona().getId() : null)
                .idTipoReporte(registro.getTipoReporte() != null ? registro.getTipoReporte().getId() : null)
                .tipoReporte(registro.getTipoReporte() != null ? registro.getTipoReporte().getNombre() : null)
                .idCanalContacto(registro.getCanalContacto() != null ? registro.getCanalContacto().getId() : null)
                .canalContacto(registro.getCanalContacto() != null ? registro.getCanalContacto().getNombre() : null)
                .quienRemite(registro.getQuienRemite() != null ? registro.getQuienRemite().getNombre() : null)
                .idQuienRemite(registro.getQuienRemite() != null ? registro.getQuienRemite().getId() : null)
                .fechaHoraAtencion(registro.getFechaHoraAtencion())
                .idPersonaAtiende(registro.getPersonaAtiende() != null ? registro.getPersonaAtiende().getId().longValue() : null)
                .idTipoServicio(registro.getTipoServicio() != null ? registro.getTipoServicio().getId() : null)
                .tipoServicio(registro.getTipoServicio() != null ? registro.getTipoServicio().getNombre() : null)
                .idPersonaRegistra(registro.getPersonaRegistra() != null ? registro.getPersonaRegistra().getId() : null)
                .idLugarEntrevista(registro.getLugarEntrevista() != null ? registro.getLugarEntrevista().getId() : null)
                .lugarEntrevista(registro.getLugarEntrevista() != null ? registro.getLugarEntrevista().getNombre() : null)
                .idIdentidadGenero(registro.getIdentidadGenero() != null ? registro.getIdentidadGenero().getId() : null)
                .idOrientacionSexual(registro.getOrientacionSexual() != null ? registro.getOrientacionSexual().getId() : null)
                .idEtnia(registro.getEtnia() != null ? registro.getEtnia().getId() : null)
                .idCiudadResidencia(registro.getCiudadResidencia() != null ? registro.getCiudadResidencia().getId() : null)
                .direccionResidencia(registro.getDireccionResidencia())
                .idVinculoUdeA(registro.getVinculoUdeA() != null ? registro.getVinculoUdeA().getId() : null)
                .idFacultad(registro.getFacultad() != null ? registro.getFacultad().getId() : null)
                .idPrograma(registro.getPrograma() != null ? registro.getPrograma().getId() : null)
                .idDependencia(registro.getDependencia() != null ? registro.getDependencia().getId() : null)
                .idCampus(registro.getCampus() != null ? registro.getCampus().getId() : null)
                .atencionAph(mapAtencionAph(atencionAph))
                .remisiones(mapRemisiones(remisiones))
                .fechaCreacion(registro.getFechaCreacion())
                .idUsuarioCreacion(registro.getUsuarioCreacion() != null ? registro.getUsuarioCreacion().getId() : null)
                .fechaActualizacion(registro.getFechaActualizacion())
                .idUsuarioActualizacion(registro.getUsuarioActualizacion() != null ? registro.getUsuarioActualizacion().getId() : null)
                .build();
    }

    private AtencionAphResponse mapAtencionAph(AtencionAph atencionAph) {
        if (atencionAph == null) {
            return null;
        }
        return AtencionAphResponse.builder()
                .id(atencionAph.getId())
                .fechaHora(atencionAph.getFechaHora())
                .idProtocoloAph(atencionAph.getProtocoloAph() != null ? atencionAph.getProtocoloAph().getId() : null)
                .protocoloAph(atencionAph.getProtocoloAph() != null ? atencionAph.getProtocoloAph().getNombre() : null)
                .practicoTriage(atencionAph.isPracticoTriage())
                .idResultadoTriage(atencionAph.getResultadoTriage() != null ? atencionAph.getResultadoTriage().getId() : null)
                .resultadoTriage(atencionAph.getResultadoTriage() != null ? atencionAph.getResultadoTriage().getNombre() : null)
                .notaAph(atencionAph.getNotaAph())
                .motivoNoTriage(atencionAph.getMotivoNoTriage())
                .aceptaPsicologia(atencionAph.isAceptaPsicologia())
                .requiereRemision(atencionAph.isRequiereRemision())
                .build();
    }

    private List<RemisionRegistroAlmaResponse> mapRemisiones(List<RemisionRegistroAlma> remisiones) {
        return remisiones.stream()
                .map(remision -> RemisionRegistroAlmaResponse.builder()
                        .idTipoRemision(remision.getIdtiporemision())
                        .tipoRemision(remision.getTipoRemision() != null ? remision.getTipoRemision().getNombre() : null)
                        .cual(remision.getCual())
                        .fecha(remision.getFecha())
                        .build())
                .toList();
    }

    private ContactoLineaAlmaResponse mapContacto(ContactoLineaAlma contacto) {
        return ContactoLineaAlmaResponse.builder()
                .id(contacto.getId())
                .idRegistroLineaAlma(contacto.getRegistroLineaAlma() != null ? contacto.getRegistroLineaAlma().getId() : null)
                .fecha(contacto.getFecha())
                .idResultado(contacto.getResultado() != null ? contacto.getResultado().getId() : null)
                .resultado(contacto.getResultado() != null ? contacto.getResultado().getNombre() : null)
                .fechaCreacion(contacto.getFechaCreacion())
                .build();
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }
        return usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + authentication.getName()));
    }
}
