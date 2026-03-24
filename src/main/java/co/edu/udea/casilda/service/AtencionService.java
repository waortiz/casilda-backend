package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.AgresorVictimaRequest;
import co.edu.udea.casilda.dto.request.AtencionRegistroRequest;
import co.edu.udea.casilda.dto.request.CasoAtencionRequest;
import co.edu.udea.casilda.dto.request.CompromisoPersonaAtendidaRequest;
import co.edu.udea.casilda.dto.request.CompromisoProfesionalRequest;
import co.edu.udea.casilda.dto.request.CompromisosAtencionRequest;
import co.edu.udea.casilda.dto.request.PersonaAtencionRequest;
import co.edu.udea.casilda.dto.request.RegistroAtencionCompleteRequest;
import co.edu.udea.casilda.dto.request.SeguimientoAtencionRequest;
import co.edu.udea.casilda.dto.response.AtencionResponse;
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
import java.util.ArrayList;
import java.util.List;

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
    private final IdentidadGeneroRepository identidadGeneroRepository;
    private final OrientacionSexualRepository orientacionSexualRepository;
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
        
        // Paso 2: Obtener la Persona y Caso desde la cita
        Persona persona = cita.getSolicitudAtencion().getCaso().getPersona();
        Caso caso = cita.getSolicitudAtencion().getCaso();
        
        // Paso 3: Actualizar datos de la Persona
        actualizarPersona(persona, request);
        
        // Paso 4: Actualizar datos del Caso
        actualizarCaso(caso, request);

        // Paso 4.1: Guardar/actualizar datos de agresor-víctima
        guardarAgresorVictima(caso, request.getCaso().getAgresorVictima());
        
        // Paso 5: Crear la Atención
        Atencion atencion = crearAtencion(cita, request, usuario);
        
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
        
        log.info("Atención registrada exitosamente con ID: {}", atencion.getId());
        
        return mapToResponse(atencion);
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
        
        // Crear la atención
        Atencion atencion = new Atencion();
        atencion.setFecha(LocalDateTime.now());
        atencion.setFechaCreacion(LocalDateTime.now());
        atencion.setUsuarioCreacion(usuario);
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
        
        if (personaRequest.getIdEtnia() != null) {
            Etnia etnia = etniaRepository.findById(personaRequest.getIdEtnia())
                    .orElseThrow(() -> new ResourceNotFoundException("Etnia no encontrada con ID: " + personaRequest.getIdEtnia()));
            persona.setEtnia(etnia);
        }

        if (personaRequest.getIdCiudadResidencia() != null) {
            Municipio ciudadResidencia = municipioRepository.findById(personaRequest.getIdCiudadResidencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad de residencia no encontrada con ID: " + personaRequest.getIdCiudadResidencia()));
            persona.setCiudadResidencia(ciudadResidencia);
        }

        if (personaRequest.getDireccionResidencia() != null) {
            String direccionResidencia = personaRequest.getDireccionResidencia().trim();
            persona.setDireccionResidencia(direccionResidencia.isEmpty() ? null : direccionResidencia);
        }
        
        // Si no se proporcionó direccionResidencia pero sí direccionLugar (del caso), usar esa
        String direccionLugar = request.getCaso().getDireccionLugar();
        if ((personaRequest.getDireccionResidencia() == null || personaRequest.getDireccionResidencia().isBlank()) &&
            direccionLugar != null && !direccionLugar.isBlank()) {
            persona.setDireccionResidencia(direccionLugar.trim());
            log.info("Dirección de residencia actualizada con direccionLugar del caso");
        }
        
        personaRepository.save(persona);
    }

    /**
     * Actualiza los datos de un Caso
     */
    private void actualizarCaso(Caso caso, RegistroAtencionCompleteRequest request) {
        log.info("Actualizando caso ID: {}", caso.getId());
        CasoAtencionRequest casoRequest = request.getCaso();
        
        // Resolver maestros por ID
        if (casoRequest.getIdDependencia() != null) {
            Dependencia dependencia = dependenciaRepository.findById(casoRequest.getIdDependencia())
                    .orElseThrow(() -> new ResourceNotFoundException("Dependencia no encontrada con ID: " + casoRequest.getIdDependencia()));
            caso.setDependencia(dependencia);
        }
        
        if (casoRequest.getIdCampus() != null) {
            Campus campus = campusRepository.findById(casoRequest.getIdCampus())
                    .orElseThrow(() -> new ResourceNotFoundException("Campus no encontrado con ID: " + casoRequest.getIdCampus()));
            caso.setCampus(campus);
        }
        
        if (casoRequest.getIdFacultad() != null) {
            FacultadEscuelaInstituto facultad = facultadRepository.findById(casoRequest.getIdFacultad())
                    .orElseThrow(() -> new ResourceNotFoundException("Facultad no encontrada con ID: " + casoRequest.getIdFacultad()));
            caso.setFacultad(facultad);
        }
        
        if (casoRequest.getIdVinculoUniversidad() != null) {
            VinculoUdeA vinculo = vinculoUdeARepository.findById(casoRequest.getIdVinculoUniversidad())
                    .orElseThrow(() -> new ResourceNotFoundException("Vínculo Universidad no encontrado con ID: " + casoRequest.getIdVinculoUniversidad()));
            caso.setVinculoUdeA(vinculo);
        }
        
        if (casoRequest.getIdSubVinculoUniversidad() != null) {
            SubVinculoUdeA subVinculo = subVinculoUdeARepository.findById(casoRequest.getIdSubVinculoUniversidad())
                    .orElseThrow(() -> new ResourceNotFoundException("SubVínculo Universidad no encontrado con ID: " + casoRequest.getIdSubVinculoUniversidad()));
            caso.setSubVinculoUdeA(subVinculo);
        }

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
        
        // Actualizar identidad de género y orientación sexual
        if (casoRequest.getIdIdentidadGenero() != null) {
            IdentidadGenero identidadGenero = identidadGeneroRepository.findById(casoRequest.getIdIdentidadGenero())
                    .orElseThrow(() -> new ResourceNotFoundException("Identidad de género no encontrada con ID: " + casoRequest.getIdIdentidadGenero()));
            caso.setIdentidadGenero(identidadGenero);
        }

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
     * Mapea una Atencion a AtencionResponse
     */
    private AtencionResponse mapToResponse(Atencion atencion) {
        return AtencionResponse.builder()
                .id(atencion.getId())
                .fecha(atencion.getFecha())
                .citaId(atencion.getCita().getId())
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
