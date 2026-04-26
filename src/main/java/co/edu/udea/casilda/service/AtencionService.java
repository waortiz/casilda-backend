package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.AgresorVictimaRequest;
import co.edu.udea.casilda.dto.request.AtencionContextoRequest;
import co.edu.udea.casilda.dto.request.AtencionRegistroRequest;
import co.edu.udea.casilda.dto.request.CasoAtencionRequest;
import co.edu.udea.casilda.dto.request.CompromisoPersonaAtendidaRequest;
import co.edu.udea.casilda.dto.request.CompromisoProfesionalRequest;
import co.edu.udea.casilda.dto.request.CompromisosAtencionRequest;
import co.edu.udea.casilda.dto.request.CorreoSolicitanteRequest;
import co.edu.udea.casilda.dto.request.HechoRequest;
import co.edu.udea.casilda.dto.request.PersonaAtencionRequest;
import co.edu.udea.casilda.dto.request.ActualizarOtroCasoRequest;
import co.edu.udea.casilda.dto.request.RegistroOtroCasoRequest;
import co.edu.udea.casilda.dto.request.RegistroAtencionCompleteRequest;
import co.edu.udea.casilda.dto.request.SeguimientoAtencionRequest;
import co.edu.udea.casilda.dto.request.TelefonoSolicitanteRequest;
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

    /**
     * Registra una atención completa: crea atención, actualiza persona/caso, crea seguimientos
     */
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
            .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + request.getAtencion().getCitaId()));
        
        // Paso 2: Obtener la Persona desde la cita
        SolicitudAtencion solicitud = cita.getSolicitudAtencion();
        Persona persona = resolverPersonaAtencion(solicitud);
        
        // Paso 2.1: Crear un nuevo Caso con los datos del DTO
        Caso caso = crearCaso(solicitud, request.getCaso(), usuario);
        
        // Paso 3: Actualizar datos de la Persona
        actualizarPersona(persona, request);
        
        // Paso 4: Actualizar datos del Caso
        actualizarCaso(caso, request);

        // Paso 4.1: Guardar/actualizar datos de agresor-víctima
        guardarAgresorVictima(caso, request.getCaso().getAgresorVictima());

        // Paso 4.2: Guardar/actualizar hechos asociados al caso
        guardarHechos(caso, request.getHechos());
        
        // Paso 5: Crear la Atención
        Atencion atencion = crearAtencion(cita, request, usuario);
        
        // Paso 5.1: Actualizar campos contextuales de la Atención (dependencia, programa, etc.)
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
        guardarAgresorVictima(caso, request.getCaso().getAgresorVictima());
        guardarHechos(caso, request.getHechos());

        return mapearOtroCaso(caso);
    }

    @Transactional
    public OtroCasoResponse actualizarOtroCaso(Long casoId, RegistroOtroCasoRequest request) {
        log.info("Actualizando otro caso ID: {}", casoId);

        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con ID: " + casoId));

        actualizarDatosCasoOtro(caso, request.getCaso());
        guardarAgresorVictima(caso, request.getCaso().getAgresorVictima());
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
        caso.setCodigo(generarCodigoCaso());
        caso.setUsuarioCreacion(usuario);
        caso.setUsuarioActualizacion(usuario);
        
        // Establecer datos iniciales del caso desde el DTO
        caso.setHaceCuantoOccurrio(casoRequest.getTiempoOcurrido());
        
        // Orientación sexual
        if (casoRequest.getIdOrientacionSexual() != null) {
            OrientacionSexual orientacionSexual = orientacionSexualRepository.findById(casoRequest.getIdOrientacionSexual())
                    .orElseThrow(() -> new ResourceNotFoundException("Orientación sexual no encontrada con ID: " + casoRequest.getIdOrientacionSexual()));
            caso.setOrientacionSexual(orientacionSexual);
        }
        
        // Identidad de género (obligatoria)
        if (casoRequest.getIdIdentidadGenero() != null) {
            IdentidadGenero identidadGenero = identidadGeneroRepository.findById(casoRequest.getIdIdentidadGenero())
                .orElseThrow(() -> new ResourceNotFoundException("Identidad de género no encontrada con ID: " + casoRequest.getIdIdentidadGenero()));
            caso.setIdentidadGenero(identidadGenero);
        }
        
        // Forma de ocurrencia
        if (casoRequest.getIdFormaOcurrencia() != null) {
            FormaOcurrencia formaOcurrencia = formaOcurrenciaRepository.findById(casoRequest.getIdFormaOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Forma de ocurrencia no encontrada con ID: " + casoRequest.getIdFormaOcurrencia()));
            caso.setFormaOcurrencia(formaOcurrencia);
        }
        
        // Lugar de ocurrencia
        if (casoRequest.getIdLugarOcurrencia() != null) {
            LugarOcurrencia lugarOcurrencia = lugarOcurrenciaRepository.findById(casoRequest.getIdLugarOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar de ocurrencia no encontrado con ID: " + casoRequest.getIdLugarOcurrencia()));
            caso.setLugarOcurrencia(lugarOcurrencia);
        }
        
        // Booleanos de violencia
        caso.setViolenciaBasadaGenero(casoRequest.getViolenciaGenero() != null ? casoRequest.getViolenciaGenero() : false);
        caso.setHechoViolenciaOcurrioActividadesMisionales(casoRequest.getViolenciaMisional() != null ? casoRequest.getViolenciaMisional() : false);
        
        // Actividad misional
        if (casoRequest.getIdActividadMisional() != null) {
            ActividadMisional actividadMisional = actividadMisionalRepository.findById(casoRequest.getIdActividadMisional())
                    .orElseThrow(() -> new ResourceNotFoundException("Actividad misional no encontrada con ID: " + casoRequest.getIdActividadMisional()));
            caso.setActividadMisional(actividadMisional);
        }
        
        // Modalidades de violencia
        List<Integer> todosIdsModalidades = new ArrayList<>();
        if (casoRequest.getModalidadesViolenciaPsicologica() != null)    todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPsicologica());
        if (casoRequest.getModalidadesViolenciaFisica() != null)          todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaFisica());
        if (casoRequest.getModalidadesViolenciaSexual() != null)          todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaSexual());
        if (casoRequest.getModalidadesViolenciaInstitucional() != null)  todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInstitucional());
        if (casoRequest.getModalidadesViolenciaEconomica() != null)      todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaEconomica());
        if (casoRequest.getModalidadesViolenciaInformatica() != null)    todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInformatica());
        if (casoRequest.getModalidadesViolenciaPrejuicio() != null)      todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPrejuicio());
        
        // Flags de tipo de violencia
        caso.setTipoViolenciaPsicologica(casoRequest.getModalidadesViolenciaPsicologica() != null && !casoRequest.getModalidadesViolenciaPsicologica().isEmpty());
        caso.setTipoViolenciaFisica(casoRequest.getModalidadesViolenciaFisica() != null && !casoRequest.getModalidadesViolenciaFisica().isEmpty());
        caso.setTipoViolenciaSexual(casoRequest.getModalidadesViolenciaSexual() != null && !casoRequest.getModalidadesViolenciaSexual().isEmpty());
        caso.setTipoViolenciaInstitucional(casoRequest.getModalidadesViolenciaInstitucional() != null && !casoRequest.getModalidadesViolenciaInstitucional().isEmpty());
        caso.setTipoViolenciaEconomicaPatrimonial(casoRequest.getModalidadesViolenciaEconomica() != null && !casoRequest.getModalidadesViolenciaEconomica().isEmpty());
        caso.setTipoViolenciaSexualInformatica(casoRequest.getModalidadesViolenciaInformatica() != null && !casoRequest.getModalidadesViolenciaInformatica().isEmpty());
        caso.setTipoViolenciaPorPrejuicio(casoRequest.getModalidadesViolenciaPrejuicio() != null && !casoRequest.getModalidadesViolenciaPrejuicio().isEmpty());
        
        // Guardar el caso
        caso = casoRepository.save(caso);
        
        // Agregar modalidades después de guardar (cuando el caso tiene ID)
        for (Integer idModalidad : todosIdsModalidades) {
            modalidadViolenciaRepository.findById(idModalidad)
                    .orElseThrow(() -> new ResourceNotFoundException("Modalidad de violencia no encontrada con ID: " + idModalidad));
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
     * Formato: ACO-YYYY-NNNN
     * Usa MAX del consecutivo existente para evitar colisiones cuando hay huecos en la secuencia.
     */
    private String generarCodigoCaso() {
        int anioActual = Year.now().getValue();
        int maxExistente = casoRepository.findMaxSequentialNumberByYear(anioActual);
        int numeroConsecutivo = maxExistente + 1;
        return String.format("ACO-%d-%04d", anioActual, numeroConsecutivo);
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
            log.warn("La solicitud ID {} no tiene remisión asociada; se usará el solicitante para registrar la atención", solicitud.getId());
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
        TipoServicio tipoServicio = tipoServicioRepository.findById(atencionRequest.getIdTipoServicio())
            .orElseThrow(() -> new ResourceNotFoundException("TipoServicio no encontrado con ID: " + atencionRequest.getIdTipoServicio()));
        
        Municipio lugarEntrevista = municipioRepository.findById(atencionRequest.getIdMunicipioEntrevista())
            .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + atencionRequest.getIdMunicipioEntrevista()));
        
        Regimen regimen = regimenRepository.findById(atencionRequest.getIdRegimen())
            .orElseThrow(() -> new ResourceNotFoundException("Régimen no encontrado con ID: " + atencionRequest.getIdRegimen()));
        
        EPS eps = epsRepository.findById(atencionRequest.getIdEps())
            .orElseThrow(() -> new ResourceNotFoundException("EPS no encontrada con ID: " + atencionRequest.getIdEps()));

        Integer estadoAtencionId = atencionRequest.getIdEstadoAtencion() != null
            ? atencionRequest.getIdEstadoAtencion()
            : 1;

        EstadoAtencion estadoAtencion = estadoAtencionRepository.findById(estadoAtencionId)
            .orElseThrow(() -> new ResourceNotFoundException("EstadoAtencion no encontrado con ID: " + estadoAtencionId));
        
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
            Sexo sexo = sexoRepository.findById(personaRequest.getIdSexo())
                    .orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado con ID: " + personaRequest.getIdSexo()));
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
                correo.setDescripcion(correoReq.getDescripcion());
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
                telefono.setDescripcion(telefonoReq.getDescripcion());
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

        caso.setHaceCuantoOccurrio(casoRequest.getTiempoOcurrido());
        
        if (casoRequest.getIdFormaOcurrencia() != null) {
            FormaOcurrencia formaOcurrencia = formaOcurrenciaRepository.findById(casoRequest.getIdFormaOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Forma de ocurrencia no encontrada con ID: " + casoRequest.getIdFormaOcurrencia()));
            caso.setFormaOcurrencia(formaOcurrencia);
        }
        
        if (casoRequest.getIdLugarOcurrencia() != null) {
            LugarOcurrencia lugarOcurrencia = lugarOcurrenciaRepository.findById(casoRequest.getIdLugarOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar de ocurrencia no encontrado con ID: " + casoRequest.getIdLugarOcurrencia()));
            caso.setLugarOcurrencia(lugarOcurrencia);
        }
        
        // Actualizar orientación sexual
        if (casoRequest.getIdOrientacionSexual() != null) {
            OrientacionSexual orientacionSexual = orientacionSexualRepository.findById(casoRequest.getIdOrientacionSexual())
                    .orElseThrow(() -> new ResourceNotFoundException("Orientación sexual no encontrada con ID: " + casoRequest.getIdOrientacionSexual()));
            caso.setOrientacionSexual(orientacionSexual);
        }
        
        // Actualizar booleanos de violencia (usar nombres correctos de la entidad)
        caso.setViolenciaBasadaGenero(casoRequest.getViolenciaGenero() != null ? casoRequest.getViolenciaGenero() : false);
        caso.setHechoViolenciaOcurrioActividadesMisionales(casoRequest.getViolenciaMisional() != null ? casoRequest.getViolenciaMisional() : false);
        
        if (casoRequest.getIdActividadMisional() != null) {
            ActividadMisional actividadMisional = actividadMisionalRepository.findById(casoRequest.getIdActividadMisional())
                    .orElseThrow(() -> new ResourceNotFoundException("Actividad misional no encontrada con ID: " + casoRequest.getIdActividadMisional()));
            caso.setActividadMisional(actividadMisional);
        }

        // Actualizar modalidades de violencia (todas las agrupaciones en una sola colección)
        List<Integer> todosIdsModalidades = new ArrayList<>();
        if (casoRequest.getModalidadesViolenciaPsicologica() != null)    todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPsicologica());
        if (casoRequest.getModalidadesViolenciaFisica() != null)          todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaFisica());
        if (casoRequest.getModalidadesViolenciaSexual() != null)          todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaSexual());
        if (casoRequest.getModalidadesViolenciaInstitucional() != null)  todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInstitucional());
        if (casoRequest.getModalidadesViolenciaEconomica() != null)      todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaEconomica());
        if (casoRequest.getModalidadesViolenciaInformatica() != null)    todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInformatica());
        if (casoRequest.getModalidadesViolenciaPrejuicio() != null)      todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPrejuicio());

        caso.getModalidadesViolencia().clear();
        for (Integer idModalidad : todosIdsModalidades) {
            modalidadViolenciaRepository.findById(idModalidad)
                    .orElseThrow(() -> new ResourceNotFoundException("Modalidad de violencia no encontrada con ID: " + idModalidad));
            ModalidadViolenciaCaso mvc = new ModalidadViolenciaCaso();
            mvc.setIdcaso(caso.getId());
            mvc.setIdmodalidadviolencia(idModalidad);
            caso.getModalidadesViolencia().add(mvc);
        }

        // Actualizar flags booleanos de tipo de violencia
        caso.setTipoViolenciaPsicologica(casoRequest.getModalidadesViolenciaPsicologica() != null && !casoRequest.getModalidadesViolenciaPsicologica().isEmpty());
        caso.setTipoViolenciaFisica(casoRequest.getModalidadesViolenciaFisica() != null && !casoRequest.getModalidadesViolenciaFisica().isEmpty());
        caso.setTipoViolenciaSexual(casoRequest.getModalidadesViolenciaSexual() != null && !casoRequest.getModalidadesViolenciaSexual().isEmpty());
        caso.setTipoViolenciaInstitucional(casoRequest.getModalidadesViolenciaInstitucional() != null && !casoRequest.getModalidadesViolenciaInstitucional().isEmpty());
        caso.setTipoViolenciaEconomicaPatrimonial(casoRequest.getModalidadesViolenciaEconomica() != null && !casoRequest.getModalidadesViolenciaEconomica().isEmpty());
        caso.setTipoViolenciaSexualInformatica(casoRequest.getModalidadesViolenciaInformatica() != null && !casoRequest.getModalidadesViolenciaInformatica().isEmpty());
        caso.setTipoViolenciaPorPrejuicio(casoRequest.getModalidadesViolenciaPrejuicio() != null && !casoRequest.getModalidadesViolenciaPrejuicio().isEmpty());

        // Actualizar programa asignado
        if (casoRequest.getIdPrograma() != null) {
            Programa programa = programaRepository.findById(casoRequest.getIdPrograma())
                    .orElseThrow(() -> new ResourceNotFoundException("Programa no encontrado con ID: " + casoRequest.getIdPrograma()));
            
            caso.getProgramas().clear();
            ProgramaCaso programaCaso = new ProgramaCaso();
            programaCaso.setCaso(caso);
            programaCaso.setPrograma(programa);
            caso.getProgramas().add(programaCaso);
            
            log.info("Programa asignado al caso ID: {} - Programa: {}", caso.getId(), programa.getNombre());
        }

        casoRepository.save(caso);
    }

    /**
     * Crea o actualiza los datos del agresor/víctima asociados al caso.
     */
    private void guardarAgresorVictima(Caso caso, AgresorVictimaRequest request) {
        if (request == null) {
            return;
        }

        VinculoAgresorVictima vinculoAgresorVictima = vinculoAgresorVictimaRepository.findById(request.getIdVinculoVictima())
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo agresor-víctima no encontrado con ID: " + request.getIdVinculoVictima()));

        AgresorVictima agresorVictima = agresorVictimaRepository.findByCasoId(caso.getId())
                .orElseGet(AgresorVictima::new);

        agresorVictima.setCaso(caso);
        agresorVictima.setPrimerNombre(request.getPrimerNombre());
        agresorVictima.setSegundoNombre(request.getSegundoNombre());
        agresorVictima.setPrimerApellido(request.getPrimerApellido());
        agresorVictima.setSegundoApellido(request.getSegundoApellido());
        agresorVictima.setVinculoAgresorVictima(vinculoAgresorVictima);

        VinculoUdeA vinculoUdeA = vinculoUdeARepository.findById(request.getIdVinculoUniversidad())
            .orElseThrow(() -> new ResourceNotFoundException("Vínculo UdeA no encontrado con ID: " + request.getIdVinculoUniversidad()));
        agresorVictima.setVinculoUdeA(vinculoUdeA);

        agresorVictimaRepository.save(agresorVictima);
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
     * Resuelve la fecha del hecho desde texto. Si no puede parsearse, usa la fecha actual.
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
                .tiempoHechos(caso.getHaceCuantoOccurrio())
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
            caso.setHaceCuantoOccurrio(casoRequest.getTiempoOcurrido());

            if (casoRequest.getIdOrientacionSexual() != null) {
                OrientacionSexual orientacionSexual = orientacionSexualRepository.findById(casoRequest.getIdOrientacionSexual())
                    .orElseThrow(() -> new ResourceNotFoundException("Orientación sexual no encontrada con ID: " + casoRequest.getIdOrientacionSexual()));
                caso.setOrientacionSexual(orientacionSexual);
            }

            if (casoRequest.getIdIdentidadGenero() != null) {
                IdentidadGenero identidadGenero = identidadGeneroRepository.findById(casoRequest.getIdIdentidadGenero())
                    .orElseThrow(() -> new ResourceNotFoundException("Identidad de género no encontrada con ID: " + casoRequest.getIdIdentidadGenero()));
                caso.setIdentidadGenero(identidadGenero);
            }

            if (casoRequest.getIdFormaOcurrencia() != null) {
                FormaOcurrencia formaOcurrencia = formaOcurrenciaRepository.findById(casoRequest.getIdFormaOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Forma de ocurrencia no encontrada con ID: " + casoRequest.getIdFormaOcurrencia()));
                caso.setFormaOcurrencia(formaOcurrencia);
            }

            if (casoRequest.getIdLugarOcurrencia() != null) {
                LugarOcurrencia lugarOcurrencia = lugarOcurrenciaRepository.findById(casoRequest.getIdLugarOcurrencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar de ocurrencia no encontrado con ID: " + casoRequest.getIdLugarOcurrencia()));
                caso.setLugarOcurrencia(lugarOcurrencia);
            }

            caso.setViolenciaBasadaGenero(Boolean.TRUE.equals(casoRequest.getViolenciaGenero()));
            caso.setHechoViolenciaOcurrioActividadesMisionales(Boolean.TRUE.equals(casoRequest.getViolenciaMisional()));

            if (casoRequest.getIdActividadMisional() != null) {
                ActividadMisional actividadMisional = actividadMisionalRepository.findById(casoRequest.getIdActividadMisional())
                    .orElseThrow(() -> new ResourceNotFoundException("Actividad misional no encontrada con ID: " + casoRequest.getIdActividadMisional()));
                caso.setActividadMisional(actividadMisional);
            } else {
                caso.setActividadMisional(null);
            }

            List<Integer> todosIdsModalidades = new ArrayList<>();
            if (casoRequest.getModalidadesViolenciaPsicologica() != null)    todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPsicologica());
            if (casoRequest.getModalidadesViolenciaFisica() != null)          todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaFisica());
            if (casoRequest.getModalidadesViolenciaSexual() != null)          todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaSexual());
            if (casoRequest.getModalidadesViolenciaInstitucional() != null)   todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInstitucional());
            if (casoRequest.getModalidadesViolenciaEconomica() != null)       todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaEconomica());
            if (casoRequest.getModalidadesViolenciaInformatica() != null)     todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaInformatica());
            if (casoRequest.getModalidadesViolenciaPrejuicio() != null)       todosIdsModalidades.addAll(casoRequest.getModalidadesViolenciaPrejuicio());

            caso.getModalidadesViolencia().clear();
            for (Integer idModalidad : todosIdsModalidades) {
                modalidadViolenciaRepository.findById(idModalidad)
                    .orElseThrow(() -> new ResourceNotFoundException("Modalidad de violencia no encontrada con ID: " + idModalidad));
                ModalidadViolenciaCaso mvc = new ModalidadViolenciaCaso();
                mvc.setIdcaso(caso.getId());
                mvc.setIdmodalidadviolencia(idModalidad);
                caso.getModalidadesViolencia().add(mvc);
            }

            caso.setTipoViolenciaPsicologica(casoRequest.getModalidadesViolenciaPsicologica() != null && !casoRequest.getModalidadesViolenciaPsicologica().isEmpty());
            caso.setTipoViolenciaFisica(casoRequest.getModalidadesViolenciaFisica() != null && !casoRequest.getModalidadesViolenciaFisica().isEmpty());
            caso.setTipoViolenciaSexual(casoRequest.getModalidadesViolenciaSexual() != null && !casoRequest.getModalidadesViolenciaSexual().isEmpty());
            caso.setTipoViolenciaInstitucional(casoRequest.getModalidadesViolenciaInstitucional() != null && !casoRequest.getModalidadesViolenciaInstitucional().isEmpty());
            caso.setTipoViolenciaEconomicaPatrimonial(casoRequest.getModalidadesViolenciaEconomica() != null && !casoRequest.getModalidadesViolenciaEconomica().isEmpty());
            caso.setTipoViolenciaSexualInformatica(casoRequest.getModalidadesViolenciaInformatica() != null && !casoRequest.getModalidadesViolenciaInformatica().isEmpty());
            caso.setTipoViolenciaPorPrejuicio(casoRequest.getModalidadesViolenciaPrejuicio() != null && !casoRequest.getModalidadesViolenciaPrejuicio().isEmpty());

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
                .orElseThrow(() -> new ResourceNotFoundException("TipoSeguimiento no encontrado con ID: " + request.getIdTipoSeguimiento()));
        
        Accion accion = accionRepository.findById(request.getIdAccion())
                .orElseThrow(() -> new ResourceNotFoundException("Acción no encontrada con ID: " + request.getIdAccion()));
        
        Actividad actividad = actividadRepository.findById(request.getIdActividad())
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada con ID: " + request.getIdActividad()));
        
        EstadoSeguimiento estadoSeguimiento = estadoSeguimientoRepository.findById(request.getIdEstadoSeguimiento())
                .orElseThrow(() -> new ResourceNotFoundException("EstadoSeguimiento no encontrado con ID: " + request.getIdEstadoSeguimiento()));
        
        MotivoEstadoSeguimiento motivoEstadoSeguimiento = motivoEstadoSeguimientoRepository.findById(request.getIdMotivoEstadoSeguimiento())
                .orElseThrow(() -> new ResourceNotFoundException("MotivoEstadoSeguimiento no encontrado con ID: " + request.getIdMotivoEstadoSeguimiento()));
        
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
     * Actualiza los datos contextuales de una Atención (dependencia, programa, ubicación, etc.)
     */
    private void actualizarAtencion(Atencion atencion, RegistroAtencionCompleteRequest request) {
        log.info("Actualizando contexto de atención ID: {}", atencion.getId());
        AtencionContextoRequest contextoRequest = request.getAtencionContexto();
        PersonaAtencionRequest personaRequest = request.getPersona();
        
        // Actualizar etnia (del contexto si está disponible, o del persona)
        if (contextoRequest.getIdEtnia() != null) {
            Etnia etnia = etniaRepository.findById(contextoRequest.getIdEtnia())
                    .orElseThrow(() -> new ResourceNotFoundException("Etnia no encontrada con ID: " + contextoRequest.getIdEtnia()));
            atencion.setEtnia(etnia);
        } else if (personaRequest.getIdEtnia() != null) {
            Etnia etnia = etniaRepository.findById(personaRequest.getIdEtnia())
                    .orElseThrow(() -> new ResourceNotFoundException("Etnia no encontrada con ID: " + personaRequest.getIdEtnia()));
            atencion.setEtnia(etnia);
        }
        
        // Actualizar ciudad de residencia (del contexto si está disponible, o del persona)
        if (contextoRequest.getIdCiudadResidencia() != null) {
            Municipio ciudadResidencia = municipioRepository.findById(contextoRequest.getIdCiudadResidencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad de residencia no encontrada con ID: " + contextoRequest.getIdCiudadResidencia()));
            atencion.setCiudadResidencia(ciudadResidencia);
        } else if (personaRequest.getIdCiudadResidencia() != null) {
            Municipio ciudadResidencia = municipioRepository.findById(personaRequest.getIdCiudadResidencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad de residencia no encontrada con ID: " + personaRequest.getIdCiudadResidencia()));
            atencion.setCiudadResidencia(ciudadResidencia);
        }
        
        // Actualizar dirección de residencia (del contexto si está disponible, o del persona)
        if (contextoRequest.getDireccionResidencia() != null && !contextoRequest.getDireccionResidencia().isBlank()) {
            atencion.setDireccionResidencia(contextoRequest.getDireccionResidencia().trim());
        } else if (personaRequest.getDireccionResidencia() != null && !personaRequest.getDireccionResidencia().isBlank()) {
            atencion.setDireccionResidencia(personaRequest.getDireccionResidencia().trim());
        }
        
        // Actualizar dependencia
        if (contextoRequest.getIdDependencia() != null) {
            Dependencia dependencia = dependenciaRepository.findById(contextoRequest.getIdDependencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Dependencia no encontrada con ID: " + contextoRequest.getIdDependencia()));
            atencion.setDependencia(dependencia);
        }
        
        // Actualizar facultad
        if (contextoRequest.getIdFacultad() != null) {
            FacultadEscuelaInstituto facultad = facultadRepository.findById(contextoRequest.getIdFacultad())
                    .orElseThrow(() -> new ResourceNotFoundException("Facultad no encontrada con ID: " + contextoRequest.getIdFacultad()));
            atencion.setFacultad(facultad);
        }
        
        // Actualizar campus
        if (contextoRequest.getIdCampus() != null) {
            Campus campus = campusRepository.findById(contextoRequest.getIdCampus())
                    .orElseThrow(() -> new ResourceNotFoundException("Campus no encontrado con ID: " + contextoRequest.getIdCampus()));
            atencion.setCampus(campus);
        }
        
        // Actualizar vínculo con la universidad
        if (contextoRequest.getIdVinculoUniversidad() != null) {
            VinculoUdeA vinculo = vinculoUdeARepository.findById(contextoRequest.getIdVinculoUniversidad())
                    .orElseThrow(() -> new ResourceNotFoundException("Vínculo Universidad no encontrado con ID: " + contextoRequest.getIdVinculoUniversidad()));
            atencion.setVinculoUdeA(vinculo);
        }
        
        // Actualizar subvínculo con la universidad
        if (contextoRequest.getIdSubVinculoUniversidad() != null) {
            SubVinculoUdeA subVinculo = subVinculoUdeARepository.findById(contextoRequest.getIdSubVinculoUniversidad())
                    .orElseThrow(() -> new ResourceNotFoundException("SubVínculo Universidad no encontrado con ID: " + contextoRequest.getIdSubVinculoUniversidad()));
            atencion.setSubVinculoUdeA(subVinculo);
        }
        
        // Actualizar programa
        if (contextoRequest.getIdPrograma() != null) {
            Programa programa = programaRepository.findById(contextoRequest.getIdPrograma())
                    .orElseThrow(() -> new ResourceNotFoundException("Programa no encontrado con ID: " + contextoRequest.getIdPrograma()));
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
                .citaId(atencion.getCita().getId())
            .estadoAtencionId(atencion.getEstadoAtencion().getId())
            .estadoAtencion(atencion.getEstadoAtencion().getNombre())
                .tipoServicioId(atencion.getTipoServicio().getId())
                .tipoServicio(atencion.getTipoServicio().getNombre())
                .lugarEntrevistaId(atencion.getLugarEntrevista().getId())
                .lugarEntrevista(atencion.getLugarEntrevista().getNombre())
                .regimenId(atencion.getRegimen().getId())
                .regimen(atencion.getRegimen().getNombre())
                .epsId(atencion.getEps().getId())
                .eps(atencion.getEps().getNombre())
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
            archivo.setNombre(atencionRequest.getArchivoConsentimientoNombre() != null ? 
                atencionRequest.getArchivoConsentimientoNombre() : "consentimiento.pdf");
            archivo.setTipoContenido(atencionRequest.getArchivoConsentimientoTipo() != null ? 
                atencionRequest.getArchivoConsentimientoTipo() : "application/pdf");
            archivo.setContenido(java.util.Base64.getDecoder().decode(atencionRequest.getArchivoConsentimientoContenido()));
            
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
            archivo.setNombre(request.getArchivoNombre() != null ? 
                    request.getArchivoNombre() : "seguimiento.pdf");
            archivo.setTipoContenido(request.getArchivoTipo() != null ? 
                    request.getArchivoTipo() : "application/pdf");
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
}
