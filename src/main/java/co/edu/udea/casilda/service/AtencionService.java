package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.*;
import co.edu.udea.casilda.dto.response.AtencionResponse;
import co.edu.udea.casilda.dto.response.OtroCasoResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.*;
import co.edu.udea.casilda.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de atenciones
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AtencionService {

    private final AtencionRepository atencionRepository;
    private final SeguimientoAtencionRepository seguimientoAtencionRepository;
    private final CompromisoService compromisoService;

    // Repositories de entidades relacionadas
    private final CitaRepository citaRepository;
    private final SolicitudAtencionRepository solicitudAtencionRepository;
    private final PersonaRepository personaRepository;
    private final CasoRepository casoRepository;
    private final UsuarioRepository usuarioRepository;

    // Repositories de maestros
    private final TipoServicioRepository tipoServicioRepository;
    private final MunicipioRepository municipioRepository;
    private final RegimenRepository regimenRepository;
    private final EPSRepository epsRepository;
    private final TipoSeguimientoRepository tipoSeguimientoRepository;
    private final AccionRepository accionRepository;
    private final ActividadRepository actividadRepository;
    private final EstadoSeguimientoRepository estadoSeguimientoRepository;
    private final MotivoEstadoSeguimientoRepository motivoEstadoSeguimientoRepository;
    private final SexoRepository sexoRepository;
    private final EtniaRepository etniaRepository;
    private final DependenciaRepository dependenciaRepository;
    private final CampusRepository campusRepository;
    private final FacultadEscuelaInstitutoRepository facultadRepository;
    private final VinculoUdeARepository vinculoUdeARepository;
    private final SubVinculoUdeARepository subVinculoUdeARepository;
    private final FormaOcurrenciaRepository formaOcurrenciaRepository;
    private final LugarOcurrenciaRepository lugarOcurrenciaRepository;
    private final ActividadMisionalRepository actividadMisionalRepository;
    private final VinculoAgresorVictimaRepository vinculoAgresorVictimaRepository;
    private final AgresorVictimaRepository agresorVictimaRepository;
    private final ArchivoConsentimientoRepository archivoConsentimientoRepository;
    private final ArchivoSeguimientoAtencionRepository archivoSeguimientoAtencionRepository;
    private final ModalidadViolenciaRepository modalidadViolenciaRepository;
    private final HechoRepository hechoRepository;
    private final EstadoAtencionRepository estadoAtencionRepository;
    private final OrientacionSexualRepository orientacionSexualRepository;
    private final IdentidadGeneroRepository identidadGeneroRepository;
    private final ProgramaRepository programaRepository;
    private final TiempoOcurridoUnidadRepository tiempoOcurridoUnidadRepository;
    private final LugarEntrevistaRepository lugarEntrevistaRepository;
    private final RutaAtencionRepository rutaAtencionRepository;
    private final RemisionAtencionRepository remisionAtencionRepository;
    private final MedidaProteccionRepository medidaProteccionRepository;
    private final TipoMedidaRepository tipoMedidaRepository;
    private final SubTipoMedidaRepository subTipoMedidaRepository;
    private final ResponsableMedidaProteccionRepository responsableMedidaProteccionRepository;
    private final PresuntoAgresorRepository presuntoAgresorRepository;



    /**
     * Pestaña 0 - Registro de atención: crea/actualiza la atención con tipoServicio, lugarEntrevista, consentimiento.
     */
    @Transactional
    public AtencionResponse registrarPestana0(AtencionRegistroRequest request) {
        log.info("Pestaña 0: Registrando atención para cita ID: {}", request.getCitaId());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Cita cita = citaRepository.findById(request.getCitaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + request.getCitaId()));

        Atencion atencion;
        if (request.getIdAtencion() != null) {
            atencion = atencionRepository.findById(request.getIdAtencion()).orElse(null);
        } else {
            atencion = null;
        }

        if (atencion == null) {
            // Crear nueva atención
            RegistroAtencionCompleteRequest wrapper = new RegistroAtencionCompleteRequest();
            wrapper.setAtencion(request);
            atencion = crearAtencion(cita, wrapper, usuario);
        } else {
            // Actualizar atención existente
            atencion.setUsuarioActualizacion(usuario);
            atencion.setFechaActualizacion(LocalDateTime.now());

            if (request.getIdTipoServicio() != null) {
                atencion.setTipoServicio(tipoServicioRepository.findById(request.getIdTipoServicio()).orElse(null));
            }
            if (request.getIdLugarEntrevista() != null) {
                atencion.setLugarEntrevista(lugarEntrevistaRepository.findById(request.getIdLugarEntrevista()).orElse(null));
            }
            if (request.getArchivoConsentimientoContenido() != null &&
                    !request.getArchivoConsentimientoContenido().isBlank()) {
                RegistroAtencionCompleteRequest wrapper = new RegistroAtencionCompleteRequest();
                wrapper.setAtencion(request);
                crearArchivoConsentimiento(atencion, wrapper);
            }

            atencionRepository.save(atencion);
        }

        log.info("Pestaña 0: Atención guardada con ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 1 - Datos de la persona: actualiza persona (sexo, correos, teléfonos) + régimen/eps en la atención.
     */
    @Transactional
    public AtencionResponse registrarPestana1(Pestana1Request request) {
        log.info("Pestaña 1: Actualizando datos de persona para atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());

        // Actualizar régimen y EPS en la atención
        if (request.getIdRegimen() != null) {
            atencion.setRegimen(regimenRepository.findById(request.getIdRegimen()).orElse(null));
        }
        if (request.getIdEps() != null) {
            atencion.setEps(epsRepository.findById(request.getIdEps()).orElse(null));
        }
        atencionRepository.save(atencion);

        // Actualizar datos de la persona
        if (request.getPersona() != null) {
            SolicitudAtencion solicitud = atencion.getCita().getSolicitudAtencion();
            Persona persona = resolverPersonaAtencion(solicitud);
            RegistroAtencionCompleteRequest wrapper = new RegistroAtencionCompleteRequest();
            AtencionRegistroRequest atReq = new AtencionRegistroRequest();
            atReq.setCitaId(request.getCitaId());
            wrapper.setAtencion(atReq);
            wrapper.setPersona(request.getPersona());
            actualizarPersona(persona, wrapper);
        }

        log.info("Pestaña 1: Datos de persona actualizados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 2 - Datos complementarios: actualiza contexto (dependencia, campus, etc.) + observaciones.
     */
    @Transactional
    public AtencionResponse registrarPestana2(Pestana2Request request) {
        log.info("Pestaña 2: Actualizando datos complementarios para atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());

        // Actualizar contexto (dependencia, campus, facultad, vínculo, programa)
        if (request.getAtencionContexto() != null) {
            RegistroAtencionCompleteRequest wrapper = new RegistroAtencionCompleteRequest();
            AtencionRegistroRequest atReq = new AtencionRegistroRequest();
            atReq.setCitaId(request.getCitaId());
            wrapper.setAtencion(atReq);
            wrapper.setAtencionContexto(request.getAtencionContexto());
            actualizarAtencion(atencion, wrapper);
        }

        // Actualizar observaciones en la solicitud
        SolicitudAtencion solicitud = atencion.getCita().getSolicitudAtencion();
        if (request.getObservacionesTelefono() != null) {
            solicitud.setObservacionesTelefono(request.getObservacionesTelefono());
        }
        if (request.getObservacionesCorreo() != null) {
            solicitud.setObservacionesCorreo(request.getObservacionesCorreo());
        }
        solicitudAtencionRepository.save(solicitud);

        log.info("Pestaña 2: Datos complementarios actualizados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 3 - Documentación: crea/actualiza caso principal + hechos.
     */
    @Transactional
    public AtencionResponse registrarPestana3(Pestana3Request request) {
        log.info("Pestaña 3: Actualizando documentación para atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        SolicitudAtencion solicitud = atencion.getCita().getSolicitudAtencion();

        if (request.getCaso() != null) {
            Caso caso = obtenerOCrearCasoPrincipal(solicitud, request.getCaso(), usuario);
            RegistroAtencionCompleteRequest wrapper = new RegistroAtencionCompleteRequest();
            AtencionRegistroRequest atReq = new AtencionRegistroRequest();
            atReq.setCitaId(request.getCitaId());
            wrapper.setAtencion(atReq);
            wrapper.setCaso(request.getCaso());
            actualizarCaso(caso, wrapper);

            // Guardar hechos
            if (request.getHechos() != null) {
                guardarHechos(caso, request.getHechos());
            }
        }

        log.info("Pestaña 3: Documentación actualizada para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 4 - Tipo de violencia: actualiza modalidades de violencia del caso principal.
     */
    @Transactional
    public AtencionResponse registrarPestana4(Pestana4Request request) {
        log.info("Pestaña 4: Actualizando tipos de violencia para atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        SolicitudAtencion solicitud = atencion.getCita().getSolicitudAtencion();

        if (request.getCaso() != null) {
            Caso caso = obtenerOCrearCasoPrincipal(solicitud, request.getCaso(), usuario);
            RegistroAtencionCompleteRequest wrapper = new RegistroAtencionCompleteRequest();
            AtencionRegistroRequest atReq = new AtencionRegistroRequest();
            atReq.setCitaId(request.getCitaId());
            wrapper.setAtencion(atReq);
            wrapper.setCaso(request.getCaso());
            actualizarCaso(caso, wrapper);
        }

        log.info("Pestaña 4: Tipos de violencia actualizados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 5 - Presunto agresor: crea/actualiza datos del agresor/víctima.
     */
    @Transactional
    public AtencionResponse registrarPestana5(Pestana5Request request) {
        log.info("Pestaña 5: Actualizando datos de presunto agresor para atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        SolicitudAtencion solicitud = atencion.getCita().getSolicitudAtencion();

        if (request.getAgresores() != null) {
            // Obtener el caso principal (debe existir, creado en pestaña 3/4)
            List<Caso> casos = casoRepository.findBySolicitudAtencionIdOrderByFechaCreacionDesc(solicitud.getId());
            if (!casos.isEmpty()) {
                guardarPresuntosAgresores(casos.get(0), request.getAgresores());
            } else {
                log.warn("No se encontró caso para guardar agresor/víctima en solicitud ID: {}", solicitud.getId());
            }
        }

        log.info("Pestaña 5: Datos de presunto agresor actualizados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 6 - Acuerdos y compromisos / Apreciaciones: actualiza logroAcuerdo, Activación de ruta, y Remisiones.
     */
    @Transactional
    public AtencionResponse registrarPestana6(Pestana6Request request) {
        log.info("Pestaña 6: Guardando acuerdos, rutas y remisiones para atención ID: {}", request.getIdAtencion());
        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());

        if (request.getLogroAcuerdo() != null) {
            atencion.setLogroAcuerdo(request.getLogroAcuerdo());
        }
        atencionRepository.save(atencion);

        // Guardar Activación de rutas
        rutaAtencionRepository.deleteByIdAtencion(atencion.getId());
        if (request.getRutas() != null) {
            for (RutaAtencionRequest r : request.getRutas()) {
                if (r.getIdTipoRutaActivacion() != null && r.getIdRutaActivacion() != null) {
                    RutaAtencion ra = new RutaAtencion(atencion.getId(), r.getIdTipoRutaActivacion(), r.getIdRutaActivacion());
                    rutaAtencionRepository.save(ra);
                }
            }
        }

        // Guardar Remisiones
        remisionAtencionRepository.deleteByIdAtencion(atencion.getId());
        if (request.getRemisiones() != null) {
            for (RemisionAtencionRequest r : request.getRemisiones()) {
                if (r.getIdTipoRemision() != null) {
                    RemisionAtencion ra = new RemisionAtencion(
                        atencion.getId(),
                        r.getIdTipoRemision(),
                        r.getCual(),
                        r.getFecha() != null ? r.getFecha() : LocalDateTime.now()
                    );
                    remisionAtencionRepository.save(ra);
                }
            }
        }

        return mapToResponse(atencion);
    }

    /**
     * Pestaña 7 - Acuerdos y compromisos: actualiza logroAcuerdo.
     */
    @Transactional
    public AtencionResponse registrarPestana7(Pestana7Request request) {
        log.info("Pestaña 7: Actualizando acuerdos para atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());

        if (request.getLogroAcuerdo() != null) {
            atencion.setLogroAcuerdo(request.getLogroAcuerdo());
        }

        atencionRepository.save(atencion);
        log.info("Pestaña 7: Acuerdos actualizados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 8 - Otros Casos: crear/actualizar/eliminar otros casos de la solicitud.
     */
    @Transactional
    public AtencionResponse registrarPestana8(Pestana8Request request) {
        log.info("Pestaña 8: Gestionando otros casos para atención ID: {}", request.getIdAtencion());

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        SolicitudAtencion solicitud = atencion.getCita().getSolicitudAtencion();

        // Registrar nuevos
        if (request.getOtrosCasos() != null && !request.getOtrosCasos().isEmpty()) {
            for (RegistroOtroCasoRequest otroCasoRequest : request.getOtrosCasos()) {
                registrarOtroCaso(solicitud.getId(), otroCasoRequest);
            }
        }

        // Actualizar existentes
        if (request.getOtrosCasosActualizar() != null && !request.getOtrosCasosActualizar().isEmpty()) {
            for (ActualizarOtroCasoRequest actualizarRequest : request.getOtrosCasosActualizar()) {
                RegistroOtroCasoRequest wrapped = new RegistroOtroCasoRequest();
                wrapped.setCaso(actualizarRequest.getCaso());
                wrapped.setHechos(actualizarRequest.getHechos());
                actualizarOtroCaso(actualizarRequest.getIdCaso(), wrapped);
            }
        }

        // Eliminar
        if (request.getOtrosCasosEliminar() != null && !request.getOtrosCasosEliminar().isEmpty()) {
            for (Long idCaso : request.getOtrosCasosEliminar()) {
                eliminarOtroCaso(idCaso);
            }
        }

        log.info("Pestaña 8: Otros casos gestionados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 9 - Medidas de protección: guarda la lista de medidas asociadas a la atención.
     */
    @Transactional
    public AtencionResponse registrarPestana9(Pestana9Request request) {
        log.info("Pestaña 9: Guardando medidas de protección para atención ID: {}", request.getIdAtencion());
        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());
        atencionRepository.save(atencion);

        // Limpiar medidas existentes para esta atención
        medidaProteccionRepository.deleteByAtencionId(atencion.getId());

        // Registrar las nuevas medidas
        if (request.getMedidas() != null) {
            for (MedidaProteccionRequest m : request.getMedidas()) {
                if (m.getTipoMedidaId() != null && m.getSubtipoMedidaId() != null && m.getResponsableId() != null) {
                    MedidaProteccion mp = new MedidaProteccion();
                    mp.setAtencion(atencion);
                    
                    TipoMedida tm = tipoMedidaRepository.findById(m.getTipoMedidaId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tipo de medida no encontrado: " + m.getTipoMedidaId()));
                    mp.setTipoMedida(tm);

                    SubTipoMedida stm = subTipoMedidaRepository.findById(m.getSubtipoMedidaId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subtipo de medida no encontrado: " + m.getSubtipoMedidaId()));
                    mp.setSubtipoMedida(stm);

                    ResponsableMedidaProteccion resp = responsableMedidaProteccionRepository.findById(m.getResponsableId())
                        .orElseThrow(() -> new ResourceNotFoundException("Responsable de medida no encontrado: " + m.getResponsableId()));
                    mp.setResponsable(resp);

                    mp.setFechaRegistro(m.getFechaRegistro() != null ? m.getFechaRegistro() : LocalDateTime.now());
                    mp.setDescripcion(m.getDescripcion() != null ? m.getDescripcion() : "");
                    medidaProteccionRepository.save(mp);
                }
            }
        }

        return mapToResponse(atencion);
    }

    /**
     * Pestaña 10 - Otros compromisos: crear compromisos de persona y profesional.
     */
    @Transactional
    public AtencionResponse registrarPestana10(Pestana10Request request) {
        log.info("Pestaña 10: Creando compromisos para atención ID: {}", request.getIdAtencion());

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());

        if (request.getCompromisos() != null) {
            crearCompromisosAtencion(atencion, request.getCompromisos());
        }

        log.info("Pestaña 10: Compromisos creados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 11 - Seguimientos: crear seguimientos de atención.
     */
    @Transactional
    public AtencionResponse registrarPestana11(Pestana11Request request) {
        log.info("Pestaña 11: Creando seguimientos para atención ID: {}", request.getIdAtencion());

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());

        if (request.getSeguimientos() != null && !request.getSeguimientos().isEmpty()) {
            for (SeguimientoAtencionRequest segRequest : request.getSeguimientos()) {
                crearSeguimiento(atencion, segRequest);
            }
        }

        log.info("Pestaña 11: Seguimientos creados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 12 - Estado de la atención: actualiza estadoAtencion.
     */
    @Transactional
    public AtencionResponse registrarPestana12(Pestana12Request request) {
        log.info("Pestaña 12: Actualizando estado de atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion(), request.getCitaId());
        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());

        if (request.getIdEstadoAtencion() != null) {
            atencion.setEstadoAtencion(estadoAtencionRepository.findById(request.getIdEstadoAtencion()).orElse(null));
        }

        atencionRepository.save(atencion);
        log.info("Pestaña 12: Estado de atención actualizado para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }


    @Transactional
    public AtencionResponse registrarAtencionCompleta(RegistroAtencionCompleteRequest request) {
        log.info("Registrando atención completa para cita ID: {}", request.getAtencion().getCitaId());

        // Obtener usuario actual de security context
        Usuario usuario = obtenerUsuarioAutenticado();
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }

        // Paso 1: Validar que la cita existe
        Cita cita = citaRepository.findById(request.getAtencion().getCitaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + request.getAtencion().getCitaId()));

        // Paso 2: Obtener la Persona desde la cita
        SolicitudAtencion solicitud = cita.getSolicitudAtencion();
        Persona persona = resolverPersonaAtencion(solicitud);

        // Paso 2.0: Actualizar observaciones de correo y teléfono en la solicitud
        solicitud.setObservacionesTelefono(request.getAtencion().getObservacionesTelefono());
        solicitud.setObservacionesCorreo(request.getAtencion().getObservacionesCorreo());
        solicitudAtencionRepository.save(solicitud);

        // Paso 2.1: Obtener o crear Caso
        Caso caso = null;
        List<Caso> casosExistentes = casoRepository.findBySolicitudAtencionIdOrderByFechaCreacionDesc(solicitud.getId());
        if (!casosExistentes.isEmpty()) {
            caso = casosExistentes.get(0);
            caso.setUsuarioActualizacion(usuario);
            caso.setFechaActualizacion(LocalDateTime.now());
        } else if (request.getCaso() != null) {
            caso = crearCaso(solicitud, request.getCaso(), usuario);
        }

        // Paso 3: Actualizar datos de la Persona
        if (request.getPersona() != null) {
            actualizarPersona(persona, request);
        }

        // Paso 4: Actualizar datos del Caso
        if (caso != null && request.getCaso() != null) {
            actualizarCaso(caso, request);
        }

        // Paso 4.1: Guardar/actualizar datos de agresores
        if (caso != null && request.getCaso() != null && request.getCaso().getAgresores() != null) {
            guardarPresuntosAgresores(caso, request.getCaso().getAgresores());
        }

        // Paso 4.2: Guardar/actualizar hechos asociados al caso
        if (caso != null && request.getHechos() != null) {
            guardarHechos(caso, request.getHechos());
        }

        // Paso 5: Obtener o crear la Atención
        Atencion atencion = null;
        if (request.getAtencion().getIdAtencion() != null) {
            atencion = atencionRepository.findById(request.getAtencion().getIdAtencion()).orElse(null);
        }
        if (atencion == null) {
            atencion = crearAtencion(cita, request, usuario);
        } else {
            atencion.setUsuarioActualizacion(usuario);
            atencion.setFechaActualizacion(LocalDateTime.now());
            
            // Actualizar campos base de Atencion
            AtencionRegistroRequest atencionRequest = request.getAtencion();
            if (atencionRequest.getIdTipoServicio() != null) {
                atencion.setTipoServicio(tipoServicioRepository.findById(atencionRequest.getIdTipoServicio()).orElse(null));
            }
            if (atencionRequest.getIdLugarEntrevista() != null) {
                atencion.setLugarEntrevista(lugarEntrevistaRepository.findById(atencionRequest.getIdLugarEntrevista()).orElse(null));
            }
            if (atencionRequest.getIdRegimen() != null) {
                atencion.setRegimen(regimenRepository.findById(atencionRequest.getIdRegimen()).orElse(null));
            }
            if (atencionRequest.getIdEps() != null) {
                atencion.setEps(epsRepository.findById(atencionRequest.getIdEps()).orElse(null));
            }
            if (atencionRequest.getLogroAcuerdo() != null) {
                atencion.setLogroAcuerdo(atencionRequest.getLogroAcuerdo());
            }
            if (atencionRequest.getIdEstadoAtencion() != null) {
                atencion.setEstadoAtencion(estadoAtencionRepository.findById(atencionRequest.getIdEstadoAtencion()).orElse(null));
            }
            
            // Persistir archivo de consentimiento si se proporciona
            if (atencionRequest.getArchivoConsentimientoContenido() != null &&
                    !atencionRequest.getArchivoConsentimientoContenido().isBlank()) {
                crearArchivoConsentimiento(atencion, request);
            }
            
            atencionRepository.save(atencion);
        }

        // Paso 5.1: Actualizar campos contextuales de la Atención (dependencia,
        // programa, etc.)
        actualizarAtencion(atencion, request);

        // Paso 6: Crear los Seguimientos si existen
        if (request.getSeguimientos() != null && !request.getSeguimientos().isEmpty()) {
            for (SeguimientoAtencionRequest segRequest : request.getSeguimientos()) {
                crearSeguimiento(atencion, segRequest);
            }
        }

        // Paso 7: Crear los Compromisos si existen
        if (request.getCompromisos() != null) {
            crearCompromisosAtencion(atencion, request.getCompromisos());
        }

        // Paso 8: Registrar Otros Casos nuevos si existen
        if (request.getOtrosCasos() != null && !request.getOtrosCasos().isEmpty()) {
            for (RegistroOtroCasoRequest otroCasoRequest : request.getOtrosCasos()) {
                registrarOtroCaso(solicitud.getId(), otroCasoRequest);
            }
        }

        // Paso 9: Actualizar Otros Casos existentes si existen
        if (request.getOtrosCasosActualizar() != null && !request.getOtrosCasosActualizar().isEmpty()) {
            for (ActualizarOtroCasoRequest actualizarRequest : request.getOtrosCasosActualizar()) {
                RegistroOtroCasoRequest wrapped = new RegistroOtroCasoRequest();
                wrapped.setCaso(actualizarRequest.getCaso());
                wrapped.setHechos(actualizarRequest.getHechos());
                actualizarOtroCaso(actualizarRequest.getIdCaso(), wrapped);
            }
        }

        // Paso 10: Eliminar Otros Casos si existen
        if (request.getOtrosCasosEliminar() != null && !request.getOtrosCasosEliminar().isEmpty()) {
            for (Long idCaso : request.getOtrosCasosEliminar()) {
                eliminarOtroCaso(idCaso);
            }
        }

        log.info("Atención registrada exitosamente con ID: {}", atencion.getId());

        return mapToResponse(atencion);
    }

    @Transactional(readOnly = true)
    public List<OtroCasoResponse> listarOtrosCasos(Long solicitudId) {
        log.info("Listando otros casos para solicitud ID: {}", solicitudId);

        solicitudAtencionRepository.findById(solicitudId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con ID: " + solicitudId));

        return casoRepository.findBySolicitudAtencionIdOrderByFechaCreacionDesc(solicitudId).stream()
                .map(this::mapearOtroCaso)
                .collect(Collectors.toList());
    }

    @Transactional
    public OtroCasoResponse registrarOtroCaso(Long solicitudId, RegistroOtroCasoRequest request) {
        log.info("Creando otro caso para solicitud ID: {}", solicitudId);

        SolicitudAtencion solicitud = solicitudAtencionRepository.findById(solicitudId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con ID: " + solicitudId));

        Usuario usuario = obtenerUsuarioAutenticado();
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }

        Caso caso = crearCaso(solicitud, request.getCaso(), usuario);
        guardarPresuntosAgresores(caso, request.getCaso().getAgresores());
        guardarHechos(caso, request.getHechos());

        return mapearOtroCaso(caso);
    }

    @Transactional
    public OtroCasoResponse actualizarOtroCaso(Long casoId, RegistroOtroCasoRequest request) {
        log.info("Actualizando otro caso ID: {}", casoId);

        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + casoId));

        actualizarDatosCasoOtro(caso, request.getCaso());
        guardarPresuntosAgresores(caso, request.getCaso().getAgresores());
        guardarHechos(caso, request.getHechos());

        return mapearOtroCaso(caso);
    }

    @Transactional
    public void eliminarOtroCaso(Long casoId) {
        log.info("Eliminando otro caso ID: {}", casoId);

        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + casoId));

        hechoRepository.deleteByCasoId(casoId);
        agresorVictimaRepository.deleteByCasoId(casoId);
        casoRepository.delete(caso);
    }

    /**
     * Crea un nuevo caso asociado a la solicitud con los datos del DTO
     */
    private Caso crearCaso(SolicitudAtencion solicitud, CasoAtencionRequest casoRequest, Usuario usuario) {
        log.info("Creando caso para solicitud ID: {}", solicitud.getId());

        Caso caso = new Caso();
        caso.setSolicitudAtencion(solicitud);
        caso.setCodigo(generarCodigoCaso(solicitud));
        caso.setUsuarioCreacion(usuario);
        caso.setUsuarioActualizacion(usuario);

        // Establecer datos iniciales del caso desde el DTO
        caso.setHacecuantooccurrio(casoRequest.getTiempoOcurridoValor());
        if (casoRequest.getIdTiempoOcurridoUnidad() != null) {
            caso.setTiempoOcurridoUnidad(tiempoOcurridoUnidadRepository.findById(casoRequest.getIdTiempoOcurridoUnidad()).orElse(null));
        }

        // Orientación sexual
        if (casoRequest.getIdOrientacionSexual() != null) {
            OrientacionSexual orientacionSexual = orientacionSexualRepository.findById(casoRequest.getIdOrientacionSexual()).orElse(null);
            caso.setOrientacionSexual(orientacionSexual);
        }

        // Identidad de género
        if (casoRequest.getIdIdentidadGenero() != null) {
            IdentidadGenero identidadGenero = identidadGeneroRepository.findById(casoRequest.getIdIdentidadGenero()).orElse(null);
            caso.setIdentidadGenero(identidadGenero);
        }

        // Forma de ocurrencia
        if (casoRequest.getIdFormaOcurrencia() != null) {
            FormaOcurrencia formaOcurrencia = formaOcurrenciaRepository.findById(casoRequest.getIdFormaOcurrencia()).orElse(null);
            caso.setFormaOcurrencia(formaOcurrencia);
        }

        // Lugar de ocurrencia
        if (casoRequest.getIdLugarOcurrencia() != null) {
            LugarOcurrencia lugarOcurrencia = lugarOcurrenciaRepository.findById(casoRequest.getIdLugarOcurrencia()).orElse(null);
            caso.setLugarOcurrencia(lugarOcurrencia);
        }

        // Booleanos de violencia
        caso.setViolenciaBasadaGenero(
                casoRequest.getViolenciaGenero() != null ? casoRequest.getViolenciaGenero() : false);
        caso.setHechoViolenciaOcurrioActividadesMisionales(
                casoRequest.getViolenciaMisional() != null ? casoRequest.getViolenciaMisional() : false);

        // Actividad misional
        if (casoRequest.getIdActividadMisional() != null) {
            ActividadMisional actividadMisional = actividadMisionalRepository.findById(casoRequest.getIdActividadMisional()).orElse(null);
            caso.setActividadMisional(actividadMisional);
        }

        // Modalidades de violencia
        List<Integer> todosIdsModalidades = new ArrayList<>();
        if (casoRequest.getModalidadesViolenciaPsicologica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPsicologica());
        if (casoRequest.getModalidadesViolenciaFisica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaFisica());
        if (casoRequest.getModalidadesViolenciaSexual() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaSexual());
        if (casoRequest.getModalidadesViolenciaInstitucional() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInstitucional());
        if (casoRequest.getModalidadesViolenciaEconomica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaEconomica());
        if (casoRequest.getModalidadesViolenciaInformatica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInformatica());
        if (casoRequest.getModalidadesViolenciaPrejuicio() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPrejuicio());

        // Flags de tipo de violencia
        caso.setTipoViolenciaPsicologica(casoRequest.getModalidadesViolenciaPsicologica() != null
                && !casoRequest.getModalidadesViolenciaPsicologica().isEmpty());
        caso.setTipoViolenciaFisica(casoRequest.getModalidadesViolenciaFisica() != null
                && !casoRequest.getModalidadesViolenciaFisica().isEmpty());
        caso.setTipoViolenciaSexual(casoRequest.getModalidadesViolenciaSexual() != null
                && !casoRequest.getModalidadesViolenciaSexual().isEmpty());
        caso.setTipoViolenciaInstitucional(casoRequest.getModalidadesViolenciaInstitucional() != null
                && !casoRequest.getModalidadesViolenciaInstitucional().isEmpty());
        caso.setTipoViolenciaEconomicaPatrimonial(casoRequest.getModalidadesViolenciaEconomica() != null
                && !casoRequest.getModalidadesViolenciaEconomica().isEmpty());
        caso.setTipoViolenciaSexualInformatica(casoRequest.getModalidadesViolenciaInformatica() != null
                && !casoRequest.getModalidadesViolenciaInformatica().isEmpty());
        caso.setTipoViolenciaPorPrejuicio(casoRequest.getModalidadesViolenciaPrejuicio() != null
                && !casoRequest.getModalidadesViolenciaPrejuicio().isEmpty());

        // Guardar el caso
        caso = casoRepository.save(caso);

        // Agregar modalidades después de guardar (cuando el caso tiene ID)
        for (Integer idModalidad : todosIdsModalidades) {
            modalidadViolenciaRepository.findById(idModalidad)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Modalidad de violencia no encontrada con ID: " + idModalidad));
            ModalidadViolenciaCaso mvc = new ModalidadViolenciaCaso();
            mvc.setIdcaso(caso.getId());
            mvc.setIdmodalidadviolencia(idModalidad);
            caso.getModalidadesViolencia().add(mvc);
        }

        // Guardar caso nuevamente con las modalidades
        caso = casoRepository.save(caso);

        // Asociar el caso a la solicitud
        solicitud.getCasos().add(caso);
        solicitudAtencionRepository.save(solicitud);

        log.info("Caso creado exitosamente con código: {}", caso.getCodigo());
        return caso;
    }

    /**
     * Genera código único para el caso
     * Formato: prefijo+AAAA+SSSSSSSS+MMDD+NNNN
     */
    private String generarCodigoCaso(SolicitudAtencion solicitud) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        String prefijo = obtenerPrefijoActor(solicitud);
        int sequential = obtenerSiguienteSecuencial(year);
        String mmdd = String.format("%02d%02d", now.getMonthValue(), now.getDayOfMonth());
        int randomNum = new java.util.Random().nextInt(10000);
        String randomStr = String.format("%04d", randomNum);
        
        return String.format("%s%d+%08d+%s+%s", prefijo, year, sequential, mmdd, randomStr);
    }

    private String obtenerPrefijoActor(SolicitudAtencion solicitud) {
        if (solicitud == null || solicitud.getRemision() == null || solicitud.getRemision().getDependencia() == null) {
            return "EA";
        }
        String nombreDep = solicitud.getRemision().getDependencia().getNombre().toLowerCase();
        if (nombreDep.contains("disciplinari") || nombreDep.contains("uad")) {
            return "UA";
        } else if (nombreDep.contains("alma")) {
            return "LA";
        } else if (nombreDep.contains("seguridad") || nombreDep.contains("vigilancia") || nombreDep.contains("persona")) {
            return "SP";
        }
        return "EA";
    }

    private int obtenerSiguienteSecuencial(int year) {
        List<String> codigos = casoRepository.findCodigosByYear(String.valueOf(year));
        int minSequential = 99999999;
        boolean hasAny = false;
        for (String codigo : codigos) {
            String[] parts = codigo.split("\\+");
            if (parts.length >= 2) {
                try {
                    int seq = Integer.parseInt(parts[1].trim());
                    if (seq < minSequential) {
                        minSequential = seq;
                    }
                    hasAny = true;
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        }
        if (!hasAny) {
            return 99999999;
        }
        return minSequential - 1;
    }

    /**
     * Resuelve la persona a intervenir en la atención.
     * Para solicitudes sin remisión, usa el solicitante como fallback.
     */
    private Persona resolverPersonaAtencion(SolicitudAtencion solicitud) {
        if (solicitud == null) {
            throw new ResourceNotFoundException("Solicitud de atención no encontrada para la cita");
        }

        if (solicitud.getRemision() != null && solicitud.getRemision().getRemitente() != null) {
            return solicitud.getRemision().getRemitente();
        }

        if (solicitud.getSolicitante() != null) {
            log.warn(
                    "La solicitud ID {} no tiene remisión asociada; se usará el solicitante para registrar la atención",
                    solicitud.getId());
            return solicitud.getSolicitante();
        }

        throw new ResourceNotFoundException("No se encontró persona asociada a la solicitud ID: " + solicitud.getId());
    }

    /**
     * Crea una atención
     */
    private Atencion crearAtencion(Cita cita, RegistroAtencionCompleteRequest request, Usuario usuario) {
        log.info("Creando atención para cita ID: {}", cita.getId());
        AtencionRegistroRequest atencionRequest = request.getAtencion();

        // Resolver maestros por ID
        TipoServicio tipoServicio = atencionRequest.getIdTipoServicio() != null
                ? tipoServicioRepository.findById(atencionRequest.getIdTipoServicio()).orElse(null)
                : null;

        LugarEntrevista lugarEntrevista = atencionRequest.getIdLugarEntrevista() != null
                ? lugarEntrevistaRepository.findById(atencionRequest.getIdLugarEntrevista()).orElse(null)
                : null;

        Regimen regimen = atencionRequest.getIdRegimen() != null
                ? regimenRepository.findById(atencionRequest.getIdRegimen()).orElse(null)
                : null;

        EPS eps = atencionRequest.getIdEps() != null
                ? epsRepository.findById(atencionRequest.getIdEps()).orElse(null)
                : null;

        Integer estadoAtencionId = atencionRequest.getIdEstadoAtencion() != null
                ? atencionRequest.getIdEstadoAtencion()
                : 1;

        EstadoAtencion estadoAtencion = estadoAtencionRepository.findById(estadoAtencionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "EstadoAtencion no encontrado con ID: " + estadoAtencionId));

        // Crear la atención
        Atencion atencion = new Atencion();
        atencion.setFechaCreacion(LocalDateTime.now());
        atencion.setUsuarioCreacion(usuario);
        atencion.setEstadoAtencion(estadoAtencion);
        atencion.setCita(cita);
        atencion.setTipoServicio(tipoServicio);
        atencion.setLugarEntrevista(lugarEntrevista);
        atencion.setRegimen(regimen);
        atencion.setEps(eps);
        atencion.setLogroAcuerdo(atencionRequest.getLogroAcuerdo() != null ? atencionRequest.getLogroAcuerdo() : false);

        atencion = atencionRepository.save(atencion);

        // Persistir archivo de consentimiento si se proporciona
        if (atencionRequest.getArchivoConsentimientoContenido() != null &&
                !atencionRequest.getArchivoConsentimientoContenido().isBlank()) {
            crearArchivoConsentimiento(atencion, request);
        }

        return atencion;
    }

    /**
     * Actualiza los datos de una Persona
     */
    private void actualizarPersona(Persona persona, RegistroAtencionCompleteRequest request) {
        log.info("Actualizando persona ID: {}", persona.getId());
        PersonaAtencionRequest personaRequest = request.getPersona();

        // Resolver maestros por ID
        if (personaRequest.getIdSexo() != null) {
            Sexo sexo = sexoRepository.findById(personaRequest.getIdSexo()).orElse(null);
            persona.setSexo(sexo);
        }

        // Procesar correos
        if (personaRequest.getCorreos() != null && !personaRequest.getCorreos().isEmpty()) {
            log.info("Actualizando correos de persona ID: {}", persona.getId());
            persona.getCorreos().clear(); // Elimina automáticamente por orphanRemoval
            for (CorreoSolicitanteRequest correoReq : personaRequest.getCorreos()) {
                CorreoPersona correo = new CorreoPersona();
                correo.setIdpersona(persona.getId());
                correo.setIdtipo(correoReq.getTipoId());
                correo.setPersona(persona);
                correo.setCorreo(correoReq.getCorreo());
                persona.getCorreos().add(correo);
            }
        }

        // Procesar telefonos
        if (personaRequest.getTelefonos() != null && !personaRequest.getTelefonos().isEmpty()) {
            log.info("Actualizando telefonos de persona ID: {}", persona.getId());
            persona.getTelefonos().clear(); // Elimina automáticamente por orphanRemoval
            for (TelefonoSolicitanteRequest telefonoReq : personaRequest.getTelefonos()) {
                TelefonoPersona telefono = new TelefonoPersona();
                telefono.setIdpersona(persona.getId());
                telefono.setIdtipo(telefonoReq.getTipoId());
                telefono.setPersona(persona);
                telefono.setTelefono(telefonoReq.getTelefono());
                persona.getTelefonos().add(telefono);
            }
        }

        personaRepository.save(persona);
    }

    /**
     * Actualiza los datos de un Caso
     */
    private void actualizarCaso(Caso caso, RegistroAtencionCompleteRequest request) {
        log.info("Actualizando caso ID: {}", caso.getId());
        CasoAtencionRequest casoRequest = request.getCaso();

        caso.setHacecuantooccurrio(casoRequest.getTiempoOcurridoValor());
        if (casoRequest.getIdTiempoOcurridoUnidad() != null) {
            caso.setTiempoOcurridoUnidad(tiempoOcurridoUnidadRepository.findById(casoRequest.getIdTiempoOcurridoUnidad()).orElse(null));
        } else {
            caso.setTiempoOcurridoUnidad(null);
        }

        if (casoRequest.getIdFormaOcurrencia() != null) {
            FormaOcurrencia formaOcurrencia = formaOcurrenciaRepository.findById(casoRequest.getIdFormaOcurrencia()).orElse(null);
            caso.setFormaOcurrencia(formaOcurrencia);
        }

        if (casoRequest.getIdLugarOcurrencia() != null) {
            LugarOcurrencia lugarOcurrencia = lugarOcurrenciaRepository.findById(casoRequest.getIdLugarOcurrencia()).orElse(null);
            caso.setLugarOcurrencia(lugarOcurrencia);
        }

        // Actualizar orientación sexual
        if (casoRequest.getIdOrientacionSexual() != null) {
            OrientacionSexual orientacionSexual = orientacionSexualRepository.findById(casoRequest.getIdOrientacionSexual()).orElse(null);
            caso.setOrientacionSexual(orientacionSexual);
        }

        // Actualizar booleanos de violencia (usar nombres correctos de la entidad)
        caso.setViolenciaBasadaGenero(
                casoRequest.getViolenciaGenero() != null ? casoRequest.getViolenciaGenero() : false);
        caso.setHechoViolenciaOcurrioActividadesMisionales(
                casoRequest.getViolenciaMisional() != null ? casoRequest.getViolenciaMisional() : false);

        if (casoRequest.getIdActividadMisional() != null) {
            ActividadMisional actividadMisional = actividadMisionalRepository.findById(casoRequest.getIdActividadMisional()).orElse(null);
            caso.setActividadMisional(actividadMisional);
        }

        // Actualizar modalidades de violencia (todas las agrupaciones en una sola
        // colección)
        List<Integer> todosIdsModalidades = new ArrayList<>();
        if (casoRequest.getModalidadesViolenciaPsicologica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPsicologica());
        if (casoRequest.getModalidadesViolenciaFisica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaFisica());
        if (casoRequest.getModalidadesViolenciaSexual() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaSexual());
        if (casoRequest.getModalidadesViolenciaInstitucional() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInstitucional());
        if (casoRequest.getModalidadesViolenciaEconomica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaEconomica());
        if (casoRequest.getModalidadesViolenciaInformatica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInformatica());
        if (casoRequest.getModalidadesViolenciaPrejuicio() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPrejuicio());

        caso.getModalidadesViolencia().clear();
        for (Integer idModalidad : todosIdsModalidades) {
            modalidadViolenciaRepository.findById(idModalidad)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Modalidad de violencia no encontrada con ID: " + idModalidad));
            ModalidadViolenciaCaso mvc = new ModalidadViolenciaCaso();
            mvc.setIdcaso(caso.getId());
            mvc.setIdmodalidadviolencia(idModalidad);
            caso.getModalidadesViolencia().add(mvc);
        }

        // Actualizar flags booleanos de tipo de violencia
        caso.setTipoViolenciaPsicologica(casoRequest.getModalidadesViolenciaPsicologica() != null
                && !casoRequest.getModalidadesViolenciaPsicologica().isEmpty());
        caso.setTipoViolenciaFisica(casoRequest.getModalidadesViolenciaFisica() != null
                && !casoRequest.getModalidadesViolenciaFisica().isEmpty());
        caso.setTipoViolenciaSexual(casoRequest.getModalidadesViolenciaSexual() != null
                && !casoRequest.getModalidadesViolenciaSexual().isEmpty());
        caso.setTipoViolenciaInstitucional(casoRequest.getModalidadesViolenciaInstitucional() != null
                && !casoRequest.getModalidadesViolenciaInstitucional().isEmpty());
        caso.setTipoViolenciaEconomicaPatrimonial(casoRequest.getModalidadesViolenciaEconomica() != null
                && !casoRequest.getModalidadesViolenciaEconomica().isEmpty());
        caso.setTipoViolenciaSexualInformatica(casoRequest.getModalidadesViolenciaInformatica() != null
                && !casoRequest.getModalidadesViolenciaInformatica().isEmpty());
        caso.setTipoViolenciaPorPrejuicio(casoRequest.getModalidadesViolenciaPrejuicio() != null
                && !casoRequest.getModalidadesViolenciaPrejuicio().isEmpty());

        // Actualizar programa asignado
        if (casoRequest.getIdPrograma() != null) {
            Programa programa = programaRepository.findById(casoRequest.getIdPrograma()).orElse(null);
            if (programa != null) {
                caso.getProgramas().clear();
                ProgramaCaso programaCaso = new ProgramaCaso();
                programaCaso.setCaso(caso);
                programaCaso.setPrograma(programa);
                caso.getProgramas().add(programaCaso);
                log.info("Programa asignado al caso ID: {} - Programa: {}", caso.getId(), programa.getNombre());
            }
        }

        casoRepository.save(caso);
    }

    /**
     * Crea o actualiza los datos del agresor/víctima asociados al caso.
     */
    private void guardarPresuntosAgresores(Caso caso, List<AgresorVictimaRequest> requestList) {
        presuntoAgresorRepository.deleteByCasoId(caso.getId());

        if (requestList == null || requestList.isEmpty()) {
            return;
        }

        for (AgresorVictimaRequest request : requestList) {
            boolean isEmpty = (request.getPrimerNombre() == null || request.getPrimerNombre().isBlank())
                    && (request.getPrimerApellido() == null || request.getPrimerApellido().isBlank())
                    && request.getIdVinculoUniversidad() == null
                    && request.getIdVinculoVictima() == null;
            if (isEmpty) {
                continue;
            }

            PresuntoAgresor presuntoAgresor = new PresuntoAgresor();
            presuntoAgresor.setCaso(caso);
            presuntoAgresor.setPrimerNombre(request.getPrimerNombre());
            presuntoAgresor.setSegundoNombre(request.getSegundoNombre());
            presuntoAgresor.setPrimerApellido(request.getPrimerApellido());
            presuntoAgresor.setSegundoApellido(request.getSegundoApellido());

            if (request.getIdVinculoUniversidad() != null) {
                presuntoAgresor.setVinculoUdeA(vinculoUdeARepository.findById(request.getIdVinculoUniversidad()).orElse(null));
            }
            if (request.getIdVinculoVictima() != null) {
                presuntoAgresor.setVinculoAgresorVictima(vinculoAgresorVictimaRepository.findById(request.getIdVinculoVictima()).orElse(null));
            }

            presuntoAgresorRepository.save(presuntoAgresor);
        }
    }

    /**
     * Crea o actualiza los hechos asociados al caso.
     */
    private void guardarHechos(Caso caso, List<HechoRequest> hechosRequest) {
        hechoRepository.deleteByCasoId(caso.getId());

        if (hechosRequest == null || hechosRequest.isEmpty()) {
            return;
        }

        for (HechoRequest hechoRequest : hechosRequest) {
            Hecho hecho = new Hecho();
            hecho.setCaso(caso);
            hecho.setFecha(resolverFechaHecho(hechoRequest.getFecha()));
            hecho.setLugar(hechoRequest.getLugar().trim());
            hecho.setDescripcion(hechoRequest.getDescripcion().trim());
            hechoRepository.save(hecho);
        }
    }

    /**
     * Resuelve la fecha del hecho desde texto. Si no puede parsearse, usa la fecha
     * actual.
     */
    private LocalDateTime resolverFechaHecho(String fechaTexto) {
        if (fechaTexto == null || fechaTexto.isBlank()) {
            return LocalDateTime.now();
        }

        try {
            return LocalDateTime.parse(fechaTexto.trim());
        } catch (Exception ex) {
            log.warn("No fue posible parsear la fecha del hecho '{}', se usará fecha actual", fechaTexto);
            return LocalDateTime.now();
        }
    }

    private OtroCasoResponse mapearOtroCaso(Caso caso) {
        List<Hecho> hechos = hechoRepository.findByCasoIdOrderByFechaDesc(caso.getId());
        String descripcion = hechos.isEmpty() ? "" : hechos.get(0).getDescripcion();

        AgresorVictima agresorVictima = agresorVictimaRepository.findByCasoId(caso.getId()).orElse(null);

        List<Integer> modalidadesPsicologicas = filtrarModalidadesPorTipo(caso, 1);
        List<Integer> modalidadesFisicas = filtrarModalidadesPorTipo(caso, 2);
        List<Integer> modalidadesSexuales = filtrarModalidadesPorTipo(caso, 3);
        List<Integer> modalidadesInstitucionales = filtrarModalidadesPorTipo(caso, 4);
        List<Integer> modalidadesEconomicas = filtrarModalidadesPorTipo(caso, 5);
        List<Integer> modalidadesInformaticas = filtrarModalidadesPorTipo(caso, 6);
        List<Integer> modalidadesPrejuicio = filtrarModalidadesPorTipo(caso, 7);

        return OtroCasoResponse.builder()
                .idCaso(caso.getId())
                .id(caso.getCodigo())
                .tiempoHechos(caso.getHacecuantooccurrio() != null && caso.getTiempoOcurridoUnidad() != null
                        ? caso.getHacecuantooccurrio() + " " + caso.getTiempoOcurridoUnidad().getNombre()
                        : "")
                .tiempoOcurridoValor(caso.getHacecuantooccurrio())
                .idTiempoOcurridoUnidad(caso.getTiempoOcurridoUnidad() != null ? caso.getTiempoOcurridoUnidad().getId() : null)
                .tipoViolencia(construirTipoViolencia(caso))
                .subcategoriaViolencia(caso.getModalidadesViolencia().stream()
                        .map(ModalidadViolenciaCaso::getModalidadViolencia)
                        .map(ModalidadViolencia::getNombre)
                        .collect(Collectors.joining(", ")))
                .descripcion(descripcion)

                .idOrientacionSexual(caso.getOrientacionSexual() != null ? caso.getOrientacionSexual().getId() : null)
                .idIdentidadGenero(caso.getIdentidadGenero() != null ? caso.getIdentidadGenero().getId() : null)
                .idFormaOcurrencia(caso.getFormaOcurrencia() != null ? caso.getFormaOcurrencia().getId() : null)
                .idLugarOcurrencia(caso.getLugarOcurrencia() != null ? caso.getLugarOcurrencia().getId() : null)
                .violenciaGenero(Boolean.TRUE.equals(caso.getViolenciaBasadaGenero()))
                .violenciaMisional(Boolean.TRUE.equals(caso.getHechoViolenciaOcurrioActividadesMisionales()))
                .idActividadMisional(caso.getActividadMisional() != null ? caso.getActividadMisional().getId() : null)

                .modalidadesViolenciaPsicologica(modalidadesPsicologicas)
                .modalidadesViolenciaFisica(modalidadesFisicas)
                .modalidadesViolenciaSexual(modalidadesSexuales)
                .modalidadesViolenciaInstitucional(modalidadesInstitucionales)
                .modalidadesViolenciaEconomica(modalidadesEconomicas)
                .modalidadesViolenciaInformatica(modalidadesInformaticas)
                .modalidadesViolenciaPrejuicio(modalidadesPrejuicio)

                .presuntoPrimerNombre(agresorVictima != null ? agresorVictima.getPrimerNombre() : null)
                .presuntoSegundoNombre(agresorVictima != null ? agresorVictima.getSegundoNombre() : null)
                .presuntoPrimerApellido(agresorVictima != null ? agresorVictima.getPrimerApellido() : null)
                .presuntoSegundoApellido(agresorVictima != null ? agresorVictima.getSegundoApellido() : null)
                .idVinculoUniversidad(agresorVictima != null && agresorVictima.getVinculoUdeA() != null
                        ? agresorVictima.getVinculoUdeA().getId()
                        : null)
                .idVinculoVictima(agresorVictima != null && agresorVictima.getVinculoAgresorVictima() != null
                        ? agresorVictima.getVinculoAgresorVictima().getId()
                        : null)
                .hechos(hechos.stream()
                        .map(hecho -> co.edu.udea.casilda.dto.response.HechoOtroCasoResponse.builder()
                                .fecha(hecho.getFecha() != null
                                        ? hecho.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                                        : null)
                                .lugar(hecho.getLugar())
                                .descripcion(hecho.getDescripcion())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private List<Integer> filtrarModalidadesPorTipo(Caso caso, int idTipoViolencia) {
        return caso.getModalidadesViolencia().stream()
                .map(ModalidadViolenciaCaso::getModalidadViolencia)
                .filter(modalidad -> modalidad.getTipoViolencia() != null
                        && modalidad.getTipoViolencia().getId() != null
                        && modalidad.getTipoViolencia().getId() == idTipoViolencia)
                .map(ModalidadViolencia::getId)
                .collect(Collectors.toList());
    }

    private void actualizarDatosCasoOtro(Caso caso, CasoAtencionRequest casoRequest) {
        caso.setHacecuantooccurrio(casoRequest.getTiempoOcurridoValor());
        if (casoRequest.getIdTiempoOcurridoUnidad() != null) {
            caso.setTiempoOcurridoUnidad(tiempoOcurridoUnidadRepository.findById(casoRequest.getIdTiempoOcurridoUnidad())
                .orElseThrow(() -> new ResourceNotFoundException("Unidad de tiempo no encontrada: " + casoRequest.getIdTiempoOcurridoUnidad())));
        } else {
            caso.setTiempoOcurridoUnidad(null);
        }

        if (casoRequest.getIdOrientacionSexual() != null) {
            OrientacionSexual orientacionSexual = orientacionSexualRepository
                    .findById(casoRequest.getIdOrientacionSexual())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Orientación sexual no encontrada con ID: " + casoRequest.getIdOrientacionSexual()));
            caso.setOrientacionSexual(orientacionSexual);
        }

        if (casoRequest.getIdIdentidadGenero() != null) {
            IdentidadGenero identidadGenero = identidadGeneroRepository.findById(casoRequest.getIdIdentidadGenero())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Identidad de género no encontrada con ID: " + casoRequest.getIdIdentidadGenero()));
            caso.setIdentidadGenero(identidadGenero);
        }

        if (casoRequest.getIdFormaOcurrencia() != null) {
            FormaOcurrencia formaOcurrencia = formaOcurrenciaRepository.findById(casoRequest.getIdFormaOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Forma de ocurrencia no encontrada con ID: " + casoRequest.getIdFormaOcurrencia()));
            caso.setFormaOcurrencia(formaOcurrencia);
        }

        if (casoRequest.getIdLugarOcurrencia() != null) {
            LugarOcurrencia lugarOcurrencia = lugarOcurrenciaRepository.findById(casoRequest.getIdLugarOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Lugar de ocurrencia no encontrado con ID: " + casoRequest.getIdLugarOcurrencia()));
            caso.setLugarOcurrencia(lugarOcurrencia);
        }

        caso.setViolenciaBasadaGenero(Boolean.TRUE.equals(casoRequest.getViolenciaGenero()));
        caso.setHechoViolenciaOcurrioActividadesMisionales(Boolean.TRUE.equals(casoRequest.getViolenciaMisional()));

        if (casoRequest.getIdActividadMisional() != null) {
            ActividadMisional actividadMisional = actividadMisionalRepository
                    .findById(casoRequest.getIdActividadMisional())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Actividad misional no encontrada con ID: " + casoRequest.getIdActividadMisional()));
            caso.setActividadMisional(actividadMisional);
        } else {
            caso.setActividadMisional(null);
        }

        List<Integer> todosIdsModalidades = new ArrayList<>();
        if (casoRequest.getModalidadesViolenciaPsicologica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPsicologica());
        if (casoRequest.getModalidadesViolenciaFisica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaFisica());
        if (casoRequest.getModalidadesViolenciaSexual() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaSexual());
        if (casoRequest.getModalidadesViolenciaInstitucional() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInstitucional());
        if (casoRequest.getModalidadesViolenciaEconomica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaEconomica());
        if (casoRequest.getModalidadesViolenciaInformatica() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInformatica());
        if (casoRequest.getModalidadesViolenciaPrejuicio() != null)
            todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPrejuicio());

        caso.getModalidadesViolencia().clear();
        for (Integer idModalidad : todosIdsModalidades) {
            modalidadViolenciaRepository.findById(idModalidad)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Modalidad de violencia no encontrada con ID: " + idModalidad));
            ModalidadViolenciaCaso mvc = new ModalidadViolenciaCaso();
            mvc.setIdcaso(caso.getId());
            mvc.setIdmodalidadviolencia(idModalidad);
            caso.getModalidadesViolencia().add(mvc);
        }

        caso.setTipoViolenciaPsicologica(casoRequest.getModalidadesViolenciaPsicologica() != null
                && !casoRequest.getModalidadesViolenciaPsicologica().isEmpty());
        caso.setTipoViolenciaFisica(casoRequest.getModalidadesViolenciaFisica() != null
                && !casoRequest.getModalidadesViolenciaFisica().isEmpty());
        caso.setTipoViolenciaSexual(casoRequest.getModalidadesViolenciaSexual() != null
                && !casoRequest.getModalidadesViolenciaSexual().isEmpty());
        caso.setTipoViolenciaInstitucional(casoRequest.getModalidadesViolenciaInstitucional() != null
                && !casoRequest.getModalidadesViolenciaInstitucional().isEmpty());
        caso.setTipoViolenciaEconomicaPatrimonial(casoRequest.getModalidadesViolenciaEconomica() != null
                && !casoRequest.getModalidadesViolenciaEconomica().isEmpty());
        caso.setTipoViolenciaSexualInformatica(casoRequest.getModalidadesViolenciaInformatica() != null
                && !casoRequest.getModalidadesViolenciaInformatica().isEmpty());
        caso.setTipoViolenciaPorPrejuicio(casoRequest.getModalidadesViolenciaPrejuicio() != null
                && !casoRequest.getModalidadesViolenciaPrejuicio().isEmpty());

        casoRepository.save(caso);
    }

    private String construirTipoViolencia(Caso caso) {
        List<String> tipos = new ArrayList<>();

        if (Boolean.TRUE.equals(caso.getTipoViolenciaPsicologica())) {
            tipos.add("Psicologica");
        }
        if (Boolean.TRUE.equals(caso.getTipoViolenciaFisica())) {
            tipos.add("Fisica");
        }
        if (Boolean.TRUE.equals(caso.getTipoViolenciaSexual())) {
            tipos.add("Sexual");
        }
        if (Boolean.TRUE.equals(caso.getTipoViolenciaInstitucional())) {
            tipos.add("Institucional");
        }
        if (Boolean.TRUE.equals(caso.getTipoViolenciaEconomicaPatrimonial())) {
            tipos.add("Economica/Patrimonial");
        }
        if (Boolean.TRUE.equals(caso.getTipoViolenciaSexualInformatica())) {
            tipos.add("Informatica");
        }
        if (Boolean.TRUE.equals(caso.getTipoViolenciaPorPrejuicio())) {
            tipos.add("Por prejuicio");
        }

        return String.join(", ", tipos);
    }

    /**
     * Crea un seguimiento de atención
     */
    private SeguimientoAtencion crearSeguimiento(Atencion atencion, SeguimientoAtencionRequest request) {
        log.info("Creando seguimiento para atención ID: {}", atencion.getId());

        // Resolver maestros
        TipoSeguimiento tipoSeguimiento = tipoSeguimientoRepository.findById(request.getIdTipoSeguimiento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TipoSeguimiento no encontrado con ID: " + request.getIdTipoSeguimiento()));

        Accion accion = accionRepository.findById(request.getIdAccion())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Acción no encontrada con ID: " + request.getIdAccion()));

        Actividad actividad = actividadRepository.findById(request.getIdActividad())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Actividad no encontrada con ID: " + request.getIdActividad()));

        EstadoSeguimiento estadoSeguimiento = estadoSeguimientoRepository.findById(request.getIdEstadoSeguimiento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "EstadoSeguimiento no encontrado con ID: " + request.getIdEstadoSeguimiento()));

        MotivoEstadoSeguimiento motivoEstadoSeguimiento = motivoEstadoSeguimientoRepository
                .findById(request.getIdMotivoEstadoSeguimiento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "MotivoEstadoSeguimiento no encontrado con ID: " + request.getIdMotivoEstadoSeguimiento()));

        // Crear el seguimiento
        SeguimientoAtencion seguimiento = new SeguimientoAtencion();
        seguimiento.setAtencion(atencion);
        seguimiento.setTipoSeguimiento(tipoSeguimiento);
        seguimiento.setFecha(request.getFecha());
        seguimiento.setAccion(accion);
        seguimiento.setActividad(actividad);
        seguimiento.setDescripcion(request.getDescripcion());
        seguimiento.setEstadoSeguimiento(estadoSeguimiento);
        seguimiento.setMotivoEstadoSeguimiento(motivoEstadoSeguimiento);

        seguimiento = seguimientoAtencionRepository.save(seguimiento);

        // Persistir archivo de seguimiento si se proporciona
        if (request.getArchivoContenido() != null && !request.getArchivoContenido().isBlank()) {
            crearArchivoSeguimiento(seguimiento, request);
        }

        return seguimiento;
    }

    /**
     * Actualiza los datos contextuales de una Atención (dependencia, programa,
     * ubicación, etc.)
     */
    private void actualizarAtencion(Atencion atencion, RegistroAtencionCompleteRequest request) {
        log.info("Actualizando contexto de atención ID: {}", atencion.getId());
        AtencionContextoRequest contextoRequest = request.getAtencionContexto();
        PersonaAtencionRequest personaRequest = request.getPersona();

        // Actualizar etnia (del contexto si está disponible, o del persona)
        if (contextoRequest != null && contextoRequest.getIdEtnia() != null) {
            Etnia etnia = etniaRepository.findById(contextoRequest.getIdEtnia())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Etnia no encontrada con ID: " + contextoRequest.getIdEtnia()));
            atencion.setEtnia(etnia);
        } else if (personaRequest != null && personaRequest.getIdEtnia() != null) {
            Etnia etnia = etniaRepository.findById(personaRequest.getIdEtnia())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Etnia no encontrada con ID: " + personaRequest.getIdEtnia()));
            atencion.setEtnia(etnia);
        }

        // Actualizar ciudad de residencia (del contexto si está disponible, o del
        // persona)
        if (contextoRequest != null && contextoRequest.getIdCiudadResidencia() != null) {
            Municipio ciudadResidencia = municipioRepository.findById(contextoRequest.getIdCiudadResidencia())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Ciudad de residencia no encontrada con ID: " + contextoRequest.getIdCiudadResidencia()));
            atencion.setCiudadResidencia(ciudadResidencia);
        } else if (personaRequest != null && personaRequest.getIdCiudadResidencia() != null) {
            Municipio ciudadResidencia = municipioRepository.findById(personaRequest.getIdCiudadResidencia())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Ciudad de residencia no encontrada con ID: " + personaRequest.getIdCiudadResidencia()));
            atencion.setCiudadResidencia(ciudadResidencia);
        }

        // Actualizar dirección de residencia (del contexto si está disponible, o del
        // persona)
        if (contextoRequest != null && contextoRequest.getDireccionResidencia() != null && !contextoRequest.getDireccionResidencia().isBlank()) {
            atencion.setDireccionResidencia(contextoRequest.getDireccionResidencia().trim());
        } else if (personaRequest != null && personaRequest.getDireccionResidencia() != null
                && !personaRequest.getDireccionResidencia().isBlank()) {
            atencion.setDireccionResidencia(personaRequest.getDireccionResidencia().trim());
        }

        // Actualizar dependencia
        if (contextoRequest != null && contextoRequest.getIdDependencia() != null) {
            Dependencia dependencia = dependenciaRepository.findById(contextoRequest.getIdDependencia())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Dependencia no encontrada con ID: " + contextoRequest.getIdDependencia()));
            atencion.setDependencia(dependencia);
        }

        // Actualizar facultad
        if (contextoRequest != null && contextoRequest.getIdFacultad() != null) {
            FacultadEscuelaInstituto facultad = facultadRepository.findById(contextoRequest.getIdFacultad())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Facultad no encontrada con ID: " + contextoRequest.getIdFacultad()));
            atencion.setFacultad(facultad);
        }

        // Actualizar campus
        if (contextoRequest != null && contextoRequest.getIdCampus() != null) {
            Campus campus = campusRepository.findById(contextoRequest.getIdCampus())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Campus no encontrado con ID: " + contextoRequest.getIdCampus()));
            atencion.setCampus(campus);
        }

        // Actualizar vínculo con la universidad
        if (contextoRequest != null && contextoRequest.getIdVinculoUniversidad() != null) {
            VinculoUdeA vinculo = vinculoUdeARepository.findById(contextoRequest.getIdVinculoUniversidad())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Vínculo Universidad no encontrado con ID: " + contextoRequest.getIdVinculoUniversidad()));
            atencion.setVinculoUdeA(vinculo);
        }
        if (contextoRequest != null) {
            atencion.setOtroVinculo(contextoRequest.getOtroVinculo());
        }

        // Actualizar programa
        if (contextoRequest != null && contextoRequest.getIdPrograma() != null) {
            Programa programa = programaRepository.findById(contextoRequest.getIdPrograma())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Programa no encontrado con ID: " + contextoRequest.getIdPrograma()));
            atencion.setPrograma(programa);
        }

        atencionRepository.save(atencion);
        log.info("Atención contextualizada exitosamente con ID: {}", atencion.getId());
    }

    /**
     * Mapea una Atencion a AtencionResponse
     */
    private AtencionResponse mapToResponse(Atencion atencion) {
        return AtencionResponse.builder()
                .id(atencion.getId())
                .citaId(atencion.getCita() != null ? atencion.getCita().getId() : null)
                .estadoAtencionId(atencion.getEstadoAtencion() != null ? atencion.getEstadoAtencion().getId() : null)
                .estadoAtencion(atencion.getEstadoAtencion() != null ? atencion.getEstadoAtencion().getNombre() : null)
                .tipoServicioId(atencion.getTipoServicio() != null ? atencion.getTipoServicio().getId() : null)
                .tipoServicio(atencion.getTipoServicio() != null ? atencion.getTipoServicio().getNombre() : null)
                .lugarEntrevistaId(atencion.getLugarEntrevista() != null ? atencion.getLugarEntrevista().getId() : null)
                .lugarEntrevista(atencion.getLugarEntrevista() != null ? atencion.getLugarEntrevista().getNombre() : null)
                .regimenId(atencion.getRegimen() != null ? atencion.getRegimen().getId() : null)
                .regimen(atencion.getRegimen() != null ? atencion.getRegimen().getNombre() : null)
                .epsId(atencion.getEps() != null ? atencion.getEps().getId() : null)
                .eps(atencion.getEps() != null ? atencion.getEps().getNombre() : null)
                .logroAcuerdo(atencion.isLogroAcuerdo())
                .build();
    }

    /**
     * Crea un archivo de consentimiento desde el contenido base64
     */
    private void crearArchivoConsentimiento(Atencion atencion, RegistroAtencionCompleteRequest request) {
        try {
            log.info("Creando archivo de consentimiento para atención ID: {}", atencion.getId());
            AtencionRegistroRequest atencionRequest = request.getAtencion();

            ArchivoConsentimiento archivo = new ArchivoConsentimiento();
            archivo.setAtencion(atencion);
            archivo.setNombre(atencionRequest.getArchivoConsentimientoNombre() != null
                    ? atencionRequest.getArchivoConsentimientoNombre()
                    : "consentimiento.pdf");
            archivo.setTipoContenido(atencionRequest.getArchivoConsentimientoTipo() != null
                    ? atencionRequest.getArchivoConsentimientoTipo()
                    : "application/pdf");
            archivo.setContenido(
                    java.util.Base64.getDecoder().decode(atencionRequest.getArchivoConsentimientoContenido()));

            archivoConsentimientoRepository.save(archivo);
        } catch (IllegalArgumentException e) {
            log.warn("Base64 inválido para archivo de consentimiento: {}", e.getMessage());
        }
    }

    /**
     * Crea un archivo de seguimiento desde el contenido base64
     */
    private void crearArchivoSeguimiento(SeguimientoAtencion seguimiento, SeguimientoAtencionRequest request) {
        try {
            log.info("Creando archivo de seguimiento para seguimiento ID: {}", seguimiento.getId());

            ArchivoSeguimientoAtencion archivo = new ArchivoSeguimientoAtencion();
            archivo.setSeguimientoAtencion(seguimiento);
            archivo.setNombre(request.getArchivoNombre() != null ? request.getArchivoNombre() : "seguimiento.pdf");
            archivo.setTipoContenido(request.getArchivoTipo() != null ? request.getArchivoTipo() : "application/pdf");
            archivo.setContenido(java.util.Base64.getDecoder().decode(request.getArchivoContenido()));

            archivoSeguimientoAtencionRepository.save(archivo);
        } catch (IllegalArgumentException e) {
            log.warn("Base64 inválido para archivo de seguimiento: {}", e.getMessage());
        }
    }

    /**
     * Crea los compromisos de persona y profesional asociados a una atención
     */
    private void crearCompromisosAtencion(Atencion atencion, CompromisosAtencionRequest compromisos) {
        log.info("Creando compromisos para atención ID: {}", atencion.getId());

        // Crear compromisos de persona atendida
        if (compromisos.getPersona() != null && !compromisos.getPersona().isEmpty()) {
            for (CompromisoPersonaAtendidaRequest comproPersona : compromisos.getPersona()) {
                comproPersona.setIdatencion(atencion.getId());
                compromisoService.crearCompromisoPersona(comproPersona);
                log.debug("Compromiso de persona creado para atención ID: {}", atencion.getId());
            }
        }

        // Crear compromisos profesionales
        if (compromisos.getProfesional() != null && !compromisos.getProfesional().isEmpty()) {
            for (CompromisoProfesionalRequest comproProfesional : compromisos.getProfesional()) {
                comproProfesional.setIdatencion(atencion.getId());
                compromisoService.crearCompromisoProfesional(comproProfesional);
                log.debug("Compromiso profesional creado para atención ID: {}", atencion.getId());
            }
        }
    }

    private Usuario obtenerUsuarioAutenticadoRequerido() {
        Usuario usuario = obtenerUsuarioAutenticado();
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }
        return usuario;
    }

    private Atencion obtenerAtencionRequerida(Long idAtencion, Long citaId) {
        if (idAtencion != null) {
            return atencionRepository.findById(idAtencion)
                    .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada con ID: " + idAtencion));
        }
        if (citaId != null) {
            return atencionRepository.findByCitaId(citaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada para cita ID: " + citaId));
        }
        throw new IllegalArgumentException("Debe proporcionar idAtencion o citaId");
    }

    private Caso obtenerOCrearCasoPrincipal(SolicitudAtencion solicitud, CasoAtencionRequest casoRequest, Usuario usuario) {
        List<Caso> casosExistentes = casoRepository.findBySolicitudAtencionIdOrderByFechaCreacionDesc(solicitud.getId());
        if (!casosExistentes.isEmpty()) {
            Caso caso = casosExistentes.get(0);
            caso.setUsuarioActualizacion(usuario);
            caso.setFechaActualizacion(LocalDateTime.now());
            return caso;
        } else {
            return crearCaso(solicitud, casoRequest, usuario);
        }
    }

    /**
     * Obtiene el usuario autenticado desde el contexto de seguridad
     */
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String email = authentication.getName();
        if (email == null || email.isBlank()) {
            return null;
        }

        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public AtencionResponse obtenerAtencionPorCita(Long citaId) {
        Atencion atencion = atencionRepository.findByCitaId(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada para la cita ID: " + citaId));
        return mapToResponse(atencion);
    }
}
