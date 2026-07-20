package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.*;
import co.edu.udea.casilda.dto.request.PestanaDatosPersonaRequest;
import co.edu.udea.casilda.dto.request.PestanaDatosComplementariosRequest;
import co.edu.udea.casilda.dto.request.PestanaDocumentacionRequest;
import co.edu.udea.casilda.dto.request.PestanaVBGRequest;
import co.edu.udea.casilda.dto.request.PestanaPresuntoAgresorRequest;
import co.edu.udea.casilda.dto.response.AtencionResponse;
import co.edu.udea.casilda.dto.response.CasoResponse;
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
    private final CorreoPersonaRepository correoPersonaRepository;
    private final TelefonoPersonaRepository telefonoPersonaRepository;

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
    private final UnidadAdministrativaRepository unidadAdministrativaRepository;
    private final CampusRepository campusRepository;
    private final UnidadAcademicaRepository unidadAcademicaRepository;
    private final VinculoUdeARepository vinculoUdeARepository;
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
    private final SubTipoDiscapacidadRepository subTipoDiscapacidadRepository;
    private final ApreciacionAtencionRepository apreciacionAtencionRepository;
    private final TipoApreciacionRepository tipoApreciacionRepository;
    private final CompromisoPersonaAtendidaRepository compromisoPersonaAtendidaRepository;
    private final CompromisoProfesionalRepository compromisoProfesionalRepository;
    private final EstadoCasoRepository estadoCasoRepository;

    /**
     * Pestaña 0 - Datos de la persona: actualiza persona (sexo, correos, teléfonos)
     * + régimen/eps/etnia/residencia en el Caso.
     */
    @Transactional
    public CasoResponse registrarPestanaDatosPersona(PestanaDatosPersonaRequest request) {
        log.info("Pestaña 0: Actualizando datos de persona para cita ID: {}", request.getCitaId());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Caso caso;
        if (request.getIdCaso() != null) {
            caso = casoRepository.findById(request.getIdCaso())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Caso no encontrado con ID: " + request.getIdCaso()));
        } else {
            // Buscar si ya existe un caso para esta cita
            List<Caso> casosExistentes = casoRepository.findByCitaIdOrderByFechaCreacionDesc(request.getCitaId());
            if (!casosExistentes.isEmpty()) {
                caso = casosExistentes.get(0);
            } else {
                // Crear un nuevo caso
                Cita cita = citaRepository.findById(request.getCitaId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Cita no encontrada con ID: " + request.getCitaId()));
                caso = new Caso();
                caso.setCita(cita);
                caso.setCodigo(generarCodigoCaso(cita.getSolicitudAtencion()));
                caso.setUsuarioCreacion(usuario);
                caso.setUsuarioActualizacion(usuario);

                IdentidadGenero identidadGenero = cita.getSolicitudAtencion().getIdentidadGenero();
                if (identidadGenero == null) {
                    identidadGenero = identidadGeneroRepository.findById(8).orElse(null);
                    if (identidadGenero == null) {
                        identidadGenero = identidadGeneroRepository.findById(1).orElse(null);
                    }
                }
                caso.setIdentidadGenero(identidadGenero);

                Regimen defaultRegimen = regimenRepository.findById(1)
                        .orElseThrow(() -> new ResourceNotFoundException("Regimen por defecto no encontrado (ID 1)"));
                EPS defaultEps = epsRepository.findById(1)
                        .orElseThrow(() -> new ResourceNotFoundException("EPS por defecto no encontrado (ID 1)"));
                caso.setRegimen(defaultRegimen);
                caso.setEps(defaultEps);
            }
        }

        caso.setUsuarioActualizacion(usuario);
        caso.setFechaActualizacion(LocalDateTime.now());

        // Actualizar régimen y EPS
        if (request.getIdRegimen() != null) {
            caso.setRegimen(regimenRepository.findById(request.getIdRegimen()).orElse(null));
        }
        if (request.getIdEps() != null) {
            caso.setEps(epsRepository.findById(request.getIdEps()).orElse(null));
        }

        // Actualizar datos demográficos en Caso
        if (request.getPersona() != null) {
            if (request.getPersona().getIdEtnia() != null) {
                caso.setEtnia(etniaRepository.findById(request.getPersona().getIdEtnia()).orElse(null));
            }
            if (request.getPersona().getIdCiudadResidencia() != null) {
                caso.setCiudadResidencia(
                        municipioRepository.findById(request.getPersona().getIdCiudadResidencia()).orElse(null));
            }
            if (request.getPersona().getDireccionResidencia() != null) {
                caso.setDireccionResidencia(request.getPersona().getDireccionResidencia());
            }

            // Actualizar persona (sexo, correos, teléfonos)
            SolicitudAtencion solicitud = caso.getCita().getSolicitudAtencion();
            Persona persona = resolverPersonaAtencion(solicitud);
            actualizarPersona(persona, request.getPersona());
        }

        // Actualizar discapacidades de la persona
        if (request.getDiscapacidades() != null) {
            SolicitudAtencion solicitud = caso.getCita().getSolicitudAtencion();
            Persona persona = resolverPersonaAtencion(solicitud);
            persona.getDiscapacidades().clear();
            personaRepository.saveAndFlush(persona);
            for (DiscapacidadPersonaRequest req : request.getDiscapacidades()) {
                if (req.getIdSubTipoDiscapacidad() == null)
                    continue;
                SubTipoDiscapacidad subTipo = subTipoDiscapacidadRepository.findById(req.getIdSubTipoDiscapacidad())
                        .orElse(null);
                if (subTipo == null)
                    continue;
                DiscapacidadPersona dp = new DiscapacidadPersona();
                dp.setIdpersona(persona.getId());
                dp.setIdsubtipodiscapacidad(subTipo.getId());
                dp.setPersona(persona);
                dp.setSubTipoDiscapacidad(subTipo);
                dp.setDescripcion(req.getDescripcion() != null ? req.getDescripcion().trim() : "");
                persona.getDiscapacidades().add(dp);
            }
            personaRepository.save(persona);
        }

        caso = casoRepository.save(caso);

        log.info("Pestaña 0: Datos de persona y caso actualizados. Caso ID: {}", caso.getId());

        return CasoResponse.builder()
                .id(caso.getId())
                .codigo(caso.getCodigo())
                .build();
    }

    /**
     * Pestaña 1 - Datos complementarios: actualiza contexto (dependencia, campus,
     * etc.) en el caso + observaciones de correo y teléfono en la solicitud.
     */
    @Transactional
    public void registrarPestanaDatosComplementarios(PestanaDatosComplementariosRequest request) {
        log.info("Pestaña 1: Actualizando datos complementarios para el caso ID: {}", request.getIdCaso());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Caso caso = casoRepository.findById(request.getIdCaso())
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + request.getIdCaso()));

        caso.setUsuarioActualizacion(usuario);
        caso.setFechaActualizacion(LocalDateTime.now());

        if (request.getIdvinculoudea() != null) {
            caso.setVinculoUdeA(vinculoUdeARepository.findById(request.getIdvinculoudea()).orElse(null));
        } else {
            caso.setVinculoUdeA(null);
        }
        caso.setOtroVinculo(request.getOtrovinculo());

        if (request.getIdprograma() != null) {
            caso.setPrograma(programaRepository.findById(request.getIdprograma()).orElse(null));
        } else {
            caso.setPrograma(null);
        }

        if (request.getIdunidadacademica() != null) {
            caso.setUnidadAcademica(unidadAcademicaRepository.findById(request.getIdunidadacademica()).orElse(null));
        } else {
            caso.setUnidadAcademica(null);
        }

        if (request.getIdunidadadministrativa() != null) {
            caso.setUnidadAdministrativa(
                    unidadAdministrativaRepository.findById(request.getIdunidadadministrativa()).orElse(null));
        } else {
            caso.setUnidadAdministrativa(null);
        }

        if (request.getIdcampus() != null) {
            caso.setCampus(campusRepository.findById(request.getIdcampus()).orElse(null));
        } else {
            caso.setCampus(null);
        }

        // Actualizar observaciones en la solicitud
        SolicitudAtencion solicitud = caso.getCita().getSolicitudAtencion();
        solicitud.setObservacionesTelefono(request.getObservacionesTelefono());
        solicitud.setObservacionesCorreo(request.getObservacionesCorreo());

        // Actualizar correos de la persona
        Persona persona = resolverPersonaAtencion(solicitud);
        if (request.getCorreos() != null) {
            List<CorreoPersona> existentes = correoPersonaRepository.findByIdpersona(persona.getId());
            correoPersonaRepository.deleteAll(existentes);
            persona.getCorreos().clear();
            personaRepository.saveAndFlush(persona);
            for (CorreoSolicitanteRequest correoReq : request.getCorreos()) {
                CorreoPersona correo = new CorreoPersona();
                correo.setIdpersona(persona.getId());
                correo.setIdtipo(correoReq.getTipoId());
                correo.setPersona(persona);
                correo.setCorreo(correoReq.getCorreo());
                persona.getCorreos().add(correo);
            }
        }

        // Actualizar teléfonos de la persona
        if (request.getTelefonos() != null) {
            List<TelefonoPersona> existentes = telefonoPersonaRepository.findByIdpersona(persona.getId());
            telefonoPersonaRepository.deleteAll(existentes);
            persona.getTelefonos().clear();
            personaRepository.saveAndFlush(persona);
            for (TelefonoSolicitanteRequest telefonoReq : request.getTelefonos()) {
                TelefonoPersona telefono = new TelefonoPersona();
                telefono.setIdpersona(persona.getId());
                telefono.setIdtipo(telefonoReq.getTipoId());
                telefono.setPersona(persona);
                telefono.setTelefono(telefonoReq.getTelefono());
                persona.getTelefonos().add(telefono);
            }
        }

        solicitudAtencionRepository.save(solicitud);
        personaRepository.save(persona);
        casoRepository.save(caso);

        log.info("Pestaña 1: Datos complementarios actualizados para caso ID: {}", caso.getId());
    }

    /* Pestaña 2 - Documentación: actualiza caso principal + hechos. */
    @Transactional
    public void registrarPestanaDocumentacion(PestanaDocumentacionRequest request) {
        log.info("Pestaña 2: Actualizando documentación para caso ID: {}", request.getIdCaso());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Caso caso = casoRepository.findById(request.getIdCaso())
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + request.getIdCaso()));

        caso.setUsuarioActualizacion(usuario);
        caso.setFechaActualizacion(LocalDateTime.now());

        // Mapear campos planos de documentación
        caso.setHacecuantooccurrio(request.getHacecuantooccurrio());

        if (request.getIdtiempoocurridounidad() != null) {
            caso.setTiempoOcurridoUnidad(
                    tiempoOcurridoUnidadRepository.findById(request.getIdtiempoocurridounidad()).orElse(null));
        } else {
            caso.setTiempoOcurridoUnidad(null);
        }

        if (request.getIdformaocurrencia() != null) {
            caso.setFormaOcurrencia(formaOcurrenciaRepository.findById(request.getIdformaocurrencia()).orElse(null));
        } else {
            caso.setFormaOcurrencia(null);
        }

        if (request.getIdciudadhechos() != null) {
            caso.setCiudadHechos(municipioRepository.findById(request.getIdciudadhechos()).orElse(null));
        } else {
            caso.setCiudadHechos(null);
        }

        if (request.getIdlugarocurrencia() != null) {
            caso.setLugarOcurrencia(lugarOcurrenciaRepository.findById(request.getIdlugarocurrencia()).orElse(null));
        } else {
            caso.setLugarOcurrencia(null);
        }

        caso.setViolenciaBasadaGenero(request.getViolenciabasadagenero());
        caso.setHechoViolenciaOcurrioActividadesMisionales(request.getHechoviolenciaocurrioactividadesmisionales());

        if (request.getIdactivadmisional() != null) {
            caso.setActividadMisional(
                    actividadMisionalRepository.findById(request.getIdactivadmisional()).orElse(null));
        } else {
            caso.setActividadMisional(null);
        }

        Caso casoGuardado = casoRepository.save(caso);

        // Guardar hechos
        if (request.getHechos() != null) {
            guardarHechos(casoGuardado, request.getHechos());
        }

        log.info("Pestaña 2: Documentación actualizada para caso ID: {}", request.getIdCaso());
    }

    /**
     * Pestaña 3 - Tipo de violencia: actualiza modalidades de violencia del caso
     * principal.
     */
    @Transactional
    public void registrarPestanaVBG(PestanaVBGRequest request) {
        log.info("Pestaña 3: Actualizando tipos de violencia para caso ID: {}", request.getIdCaso());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Caso caso = casoRepository.findById(request.getIdCaso())
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + request.getIdCaso()));

        caso.setUsuarioActualizacion(usuario);
        caso.setFechaActualizacion(LocalDateTime.now());

        // Recolectar todas las modalidades y asociarlas al caso
        List<Integer> todosIdsModalidades = new ArrayList<>();
        if (request.getModalidadesViolenciaPsicologica() != null)
            todosIdsModalidades.addAll(request.getModalidadesViolenciaPsicologica());
        if (request.getModalidadesViolenciaFisica() != null)
            todosIdsModalidades.addAll(request.getModalidadesViolenciaFisica());
        if (request.getModalidadesViolenciaSexual() != null)
            todosIdsModalidades.addAll(request.getModalidadesViolenciaSexual());
        if (request.getModalidadesViolenciaInstitucional() != null)
            todosIdsModalidades.addAll(request.getModalidadesViolenciaInstitucional());
        if (request.getModalidadesViolenciaEconomica() != null)
            todosIdsModalidades.addAll(request.getModalidadesViolenciaEconomica());
        if (request.getModalidadesViolenciaInformatica() != null)
            todosIdsModalidades.addAll(request.getModalidadesViolenciaInformatica());
        if (request.getModalidadesViolenciaPrejuicio() != null)
            todosIdsModalidades.addAll(request.getModalidadesViolenciaPrejuicio());

        caso.getModalidadesViolencia().clear();
        casoRepository.saveAndFlush(caso);

        for (Integer idModalidad : todosIdsModalidades) {
            ModalidadViolencia mv = modalidadViolenciaRepository.findById(idModalidad)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Modalidad de violencia no encontrada con ID: " + idModalidad));
            ModalidadViolenciaCaso mvc = new ModalidadViolenciaCaso();
            mvc.setIdcaso(caso.getId());
            mvc.setIdmodalidadviolencia(idModalidad);
            mvc.setCaso(caso);
            mvc.setModalidadViolencia(mv);
            caso.getModalidadesViolencia().add(mvc);
        }

        caso.setTipoViolenciaPsicologica(request.getModalidadesViolenciaPsicologica() != null
                && !request.getModalidadesViolenciaPsicologica().isEmpty());
        caso.setTipoViolenciaFisica(request.getModalidadesViolenciaFisica() != null
                && !request.getModalidadesViolenciaFisica().isEmpty());
        caso.setTipoViolenciaSexual(request.getModalidadesViolenciaSexual() != null
                && !request.getModalidadesViolenciaSexual().isEmpty());
        caso.setTipoViolenciaInstitucional(request.getModalidadesViolenciaInstitucional() != null
                && !request.getModalidadesViolenciaInstitucional().isEmpty());
        caso.setTipoViolenciaEconomicaPatrimonial(request.getModalidadesViolenciaEconomica() != null
                && !request.getModalidadesViolenciaEconomica().isEmpty());
        caso.setTipoViolenciaSexualInformatica(request.getModalidadesViolenciaInformatica() != null
                && !request.getModalidadesViolenciaInformatica().isEmpty());
        caso.setTipoViolenciaPorPrejuicio(request.getModalidadesViolenciaPrejuicio() != null
                && !request.getModalidadesViolenciaPrejuicio().isEmpty());

        casoRepository.save(caso);
        log.info("Pestaña 3: Tipos de violencia actualizados para caso ID: {}", caso.getId());
    }

    /**
     * Pestaña 4 - Presunto agresor: crea/actualiza datos del agresor/víctima.
     */
    @Transactional
    public void registrarPestanaPresuntoAgresor(PestanaPresuntoAgresorRequest request) {
        log.info("Pestaña 4: Actualizando datos de presunto agresor para caso ID: {}", request.getIdCaso());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Caso caso = casoRepository.findById(request.getIdCaso())
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + request.getIdCaso()));

        caso.setUsuarioActualizacion(usuario);
        caso.setFechaActualizacion(LocalDateTime.now());

        if (request.getAgresores() != null) {
            guardarPresuntosAgresores(caso, request.getAgresores());
        }

        casoRepository.save(caso);
        log.info("Pestaña 4: Datos de presunto agresor actualizados para caso ID: {}", caso.getId());
    }

    /**
     * Pestaña 5 - Registro de atención: crea/actualiza la atención con
     * tipoServicio, lugarEntrevista, consentimiento.
     */
    @Transactional
    public AtencionResponse registrarPestanaAtencion(PestanaRegistroAtencionRequest request) {
        log.info("Pestaña 5: Registrando atención para caso ID: {}", request.getCasoId());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Caso caso = casoRepository.findById(request.getCasoId())
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + request.getCasoId()));

        AtencionRegistroRequest atencionRequest = new AtencionRegistroRequest();
        atencionRequest.setCasoId(request.getCasoId());
        atencionRequest.setIdAtencion(request.getIdAtencion());
        atencionRequest.setIdTipoServicio(request.getIdTipoServicio());
        atencionRequest.setIdLugarEntrevista(request.getIdLugarEntrevista());
        atencionRequest.setArchivoConsentimientoNombre(request.getArchivoConsentimientoNombre());
        atencionRequest.setArchivoConsentimientoTipo(request.getArchivoConsentimientoTipo());
        atencionRequest.setArchivoConsentimientoContenido(request.getArchivoConsentimientoContenido());

        Atencion atencion;
        if (request.getIdAtencion() != null) {
            atencion = atencionRepository.findById(request.getIdAtencion()).orElse(null);
        } else {
            atencion = new Atencion();
            atencion.setCaso(caso);
            atencion.setUsuarioCreacion(usuario);
            atencion.setFechaCreacion(LocalDateTime.now());
            atencion.setEstadoAtencion(estadoAtencionRepository.findById(1).orElse(null));
        }

        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());

        if (request.getIdTipoServicio() != null) {
            atencion.setTipoServicio(tipoServicioRepository.findById(request.getIdTipoServicio()).orElse(null));
        }
        if (request.getIdLugarEntrevista() != null) {
            atencion.setLugarEntrevista(
                    lugarEntrevistaRepository.findById(request.getIdLugarEntrevista()).orElse(null));
        }
        atencionRepository.save(atencion);

        if (request.getArchivoConsentimientoContenido() != null &&
                !request.getArchivoConsentimientoContenido().isBlank()) {
            RegistroAtencionCompleteRequest wrapper = new RegistroAtencionCompleteRequest();
            wrapper.setAtencion(atencionRequest);
            crearArchivoConsentimiento(atencion, wrapper);
        }

        log.info("Pestaña 5: Atención guardada con ID: {}", atencion.getId());

        return mapToResponse(atencion);

    }

    @Transactional
    public void registrarPestanaApreciaciones(PestanaApreciacionesRequest request) {
        log.info("Pestaña 6: Guardando apreciaciones para atención ID: {}", request.getIdAtencion());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion());
        atencion.setUsuarioActualizacion(usuario);
        atencion.setFechaActualizacion(LocalDateTime.now());
        atencionRepository.save(atencion);

        // Delete existing ones
        List<ApreciacionAtencion> existentes = apreciacionAtencionRepository.findByIdIdAtencion(atencion.getId());
        apreciacionAtencionRepository.deleteAll(existentes);

        // Save new ones
        if (request.getApreciaciones() != null) {
            for (PestanaApreciacionesRequest.ApreciacionRequest r : request.getApreciaciones()) {
                if (r.getIdTipoApreciacion() != null) {
                    TipoApreciacion tipo = tipoApreciacionRepository.findById(r.getIdTipoApreciacion())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Tipo de apreciación no encontrado con ID: " + r.getIdTipoApreciacion()));

                    ApreciacionAtencionId id = new ApreciacionAtencionId(atencion.getId(), r.getIdTipoApreciacion());
                    ApreciacionAtencion aa = ApreciacionAtencion.builder()
                            .id(id)
                            .atencion(atencion)
                            .tipoApreciacion(tipo)
                            .descripcion(r.getDescripcion() != null ? r.getDescripcion() : "")
                            .build();
                    apreciacionAtencionRepository.save(aa);
                }
            }
        }
    }

    /**
     * Pestaña 7 - Acuerdos y compromisos: actualiza logroAcuerdo,
     * Activación de ruta, y Remisiones.
     */
    @Transactional
    public void registrarPestanaAcuerdos(PestanaAcuerdosRequest request) {
        log.info("Pestaña 7: Guardando acuerdos, rutas y remisiones para atención ID: {}", request.getIdAtencion());
        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion());
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
                    RutaAtencion ra = new RutaAtencion(atencion.getId(), r.getIdTipoRutaActivacion(),
                            r.getIdRutaActivacion());
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
                            r.getFecha() != null ? r.getFecha() : LocalDateTime.now());
                    remisionAtencionRepository.save(ra);
                }
            }
        }
    }

    /**
     * Pestaña 8 - Medidas de protección.
     */
    @Transactional
    public void registrarPestanaMedidasProteccion(PestanaMedidasProteccionRequest request) {
        log.info("Pestaña 8: Guardando medidas de protección para atención ID: {}", request.getIdAtencion());
        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion());
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
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Tipo de medida no encontrado: " + m.getTipoMedidaId()));
                    mp.setTipoMedida(tm);

                    SubTipoMedida stm = subTipoMedidaRepository.findById(m.getSubtipoMedidaId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Subtipo de medida no encontrado: " + m.getSubtipoMedidaId()));
                    mp.setSubtipoMedida(stm);

                    ResponsableMedidaProteccion resp = responsableMedidaProteccionRepository
                            .findById(m.getResponsableId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Responsable de medida no encontrado: " + m.getResponsableId()));
                    mp.setResponsable(resp);

                    mp.setFechaRegistro(m.getFechaRegistro() != null ? m.getFechaRegistro() : LocalDateTime.now());
                    mp.setDescripcion(m.getDescripcion() != null ? m.getDescripcion() : "");
                    medidaProteccionRepository.save(mp);
                }
            }
        }
    }

    /**
     * Pestaña 9 - Otros compromisos: crear compromisos de persona y profesional.
     */
    @Transactional
    public AtencionResponse registrarPestanaOtrosCompromisos(PestanaOtrosCompromisosRequest request) {
        log.info("Pestaña 9: Creando compromisos para atención ID: {}", request.getIdAtencion());

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion());

        // Limpiar compromisos de persona
        List<CompromisoPersonaAtendida> existentesPersona = compromisoPersonaAtendidaRepository
                .findByIdatencion(atencion.getId());
        compromisoPersonaAtendidaRepository.deleteAll(existentesPersona);

        // Crear compromisos de persona atendida
        if (request.getPersona() != null && !request.getPersona().isEmpty()) {
            for (CompromisoPersonaAtendidaRequest comproPersona : request.getPersona()) {
                comproPersona.setIdatencion(atencion.getId());
                compromisoService.crearCompromisoPersona(comproPersona);
                log.debug("Compromiso de persona creado para atención ID: {}", atencion.getId());
            }
        }

        // Limpiar compromisos profesionales
        List<CompromisoProfesional> existentesProfesional = compromisoProfesionalRepository
                .findByIdatencion(atencion.getId());
        compromisoProfesionalRepository.deleteAll(existentesProfesional);

        // Crear compromisos profesionales
        if (request.getProfesional() != null && !request.getProfesional().isEmpty()) {
            for (CompromisoProfesionalRequest comproProfesional : request.getProfesional()) {
                comproProfesional.setIdatencion(atencion.getId());
                compromisoService.crearCompromisoProfesional(comproProfesional);
                log.debug("Compromiso profesional creado para atención ID: {}", atencion.getId());
            }
        }

        log.info("Pestaña 9: Compromisos creados para atención ID: {}", atencion.getId());
        return mapToResponse(atencion);
    }

    /**
     * Pestaña 10 - Seguimientos
     */
    @Transactional
    public void registrarPestanaSeguimientos(PestanaSeguimientosRequest request) {
        log.info("Pestaña 10: Creando seguimientos para atención ID: {}", request.getIdAtencion());

        Atencion atencion = obtenerAtencionRequerida(request.getIdAtencion());

        // Limpiar seguimientos existentes
        List<SeguimientoAtencion> existentes = seguimientoAtencionRepository
                .findByAtencionIdOrderByFechaDesc(atencion.getId());
        seguimientoAtencionRepository.deleteAll(existentes);

        if (request.getSeguimientos() != null && !request.getSeguimientos().isEmpty()) {
            for (SeguimientoAtencionRequest segRequest : request.getSeguimientos()) {
                crearSeguimiento(atencion, segRequest);
            }
        }

        log.info("Pestaña 10: Seguimientos creados para atención ID: {}", atencion.getId());
    }

    @Transactional
    public void registrarPestanaEstadoCaso(PestanaEstadoCasoRequest request) {
        log.info("Pestaña 11: Actualizando estado de caso para caso ID: {}", request.getIdCaso());

        Usuario usuario = obtenerUsuarioAutenticadoRequerido();

        Caso caso = casoRepository.findById(request.getIdCaso())
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + request.getIdCaso()));

        if (request.getIdEstadoCaso() != null) {
            caso.setEstadoCaso(estadoCasoRepository.findById(request.getIdEstadoCaso()).orElse(null));
            caso.setUsuarioActualizacion(usuario);
            caso.setFechaActualizacion(LocalDateTime.now());
            casoRepository.save(caso);
        }

        Atencion atencion = atencionRepository.findByCasoId(caso.getId()).orElse(null);
        if (atencion != null) {
            atencion.setUsuarioActualizacion(usuario);
            atencion.setFechaActualizacion(LocalDateTime.now());
            atencionRepository.save(atencion);
        }

        log.info("Pestaña 11: Estado de caso actualizado para caso ID: {}", caso.getId());
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
        if (solicitud == null || solicitud.getRemision() == null
                || solicitud.getRemision().getUnidadAdministrativa() == null) {
            return "EA";
        }
        String nombreDep = solicitud.getRemision().getUnidadAdministrativa().getNombre().toLowerCase();
        if (nombreDep.contains("disciplinari") || nombreDep.contains("uad")) {
            return "UA";
        } else if (nombreDep.contains("alma")) {
            return "LA";
        } else if (nombreDep.contains("seguridad") || nombreDep.contains("vigilancia")
                || nombreDep.contains("persona")) {
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
     * Actualiza los datos de una Persona
     */
    private void actualizarPersona(Persona persona, PersonaAtencionRequest personaRequest) {
        log.info("Actualizando persona ID: {}", persona.getId());

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
                presuntoAgresor
                        .setVinculoUdeA(vinculoUdeARepository.findById(request.getIdVinculoUniversidad()).orElse(null));
            }
            if (request.getIdVinculoVictima() != null) {
                presuntoAgresor.setVinculoAgresorVictima(
                        vinculoAgresorVictimaRepository.findById(request.getIdVinculoVictima()).orElse(null));
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
     * Mapea una Atencion a AtencionResponse
     */
    private AtencionResponse mapToResponse(Atencion atencion) {
        return AtencionResponse.builder()
                .id(atencion.getId())
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

    private Usuario obtenerUsuarioAutenticadoRequerido() {
        Usuario usuario = obtenerUsuarioAutenticado();
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no autenticado");
        }
        return usuario;
    }

    private Atencion obtenerAtencionRequerida(Long idAtencion) {
        if (idAtencion != null) {
            return atencionRepository.findById(idAtencion)
                    .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada con ID: " + idAtencion));
        }

        throw new IllegalArgumentException("Debe proporcionar idAtencion o citaId");
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
        Atencion atencion = atencionRepository.findByCasoCitaId(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atención no encontrada para la cita ID: " + citaId));
        return mapToResponse(atencion);
    }
}
