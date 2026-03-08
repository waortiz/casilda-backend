package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.CorreoSolicitanteRequest;
import co.edu.udea.casilda.dto.request.DatosRemitenteRequest;
import co.edu.udea.casilda.dto.request.DatosSolicitanteRequest;
import co.edu.udea.casilda.dto.request.SolicitudAcompanamientoRequest;
import co.edu.udea.casilda.dto.request.TelefonoSolicitanteRequest;
import co.edu.udea.casilda.dto.response.SolicitudAcompanamientoResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.*;
import co.edu.udea.casilda.model.enums.EstadoSolicitud;
import co.edu.udea.casilda.model.enums.SexoEnum;
import co.edu.udea.casilda.model.enums.TipoSolicitud;
import co.edu.udea.casilda.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de solicitudes de acompañamiento
 * Usa arquitectura relacional: Persona → Caso → SolicitudAtencion
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudAcompanamientoService {

    // Repositorios principales
    private final PersonaRepository personaRepository;
    private final CasoRepository casoRepository;
    private final RemisionRepository remisionRepository;
    private final SolicitudAtencionRepository solicitudAtencionRepository;
    
    // Repositorios auxiliares
    private final CorreoPersonaRepository correoPersonaRepository;
    private final TelefonoPersonaRepository telefonoPersonaRepository;
    
    // Repositorios de maestros
    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final SexoRepository sexoRepository;
    private final IdentidadGeneroRepository identidadGeneroRepository;
    private final CampusRepository campusRepository;
    private final DependenciaRepository dependenciaRepository;
    private final FacultadEscuelaInstitutoRepository facultadRepository;
    private final TipoSolicitudRepository tipoSolicitudRepository;
    private final EstadoSolicitudRepository estadoSolicitudRepository;
    private final CargoRepository cargoRepository;
    private final TipoCorreoRepository tipoCorreoRepository;
    private final TipoTelefonoRepository tipoTelefonoRepository;

    /**
     * Crea una nueva solicitud de acompañamiento usando arquitectura relacional
     */
    @Transactional
    public SolicitudAcompanamientoResponse crearSolicitud(SolicitudAcompanamientoRequest request) {
        log.info("Creando solicitud de acompañamiento tipo ID: {}", request.getTipoSolicitudId());

        // Paso 1: Buscar o crear Persona (solicitante)
        Persona solicitante = buscarOCrearPersona(request.getDatosSolicitante());

        // Validar tipo de solicitud
        TipoSolicitud.fromId(request.getTipoSolicitudId());

        // Paso 2: Crear Caso
        Caso caso = crearCaso(solicitante, request);

        // Paso 3: Crear Remisión si es reporte indirecto
        Remision remision = null;
        if (TipoSolicitud.esIndirecta(request.getTipoSolicitudId()) && request.getDatosRemitente() != null) {
            remision = crearRemision(request.getDatosRemitente());
        }

        // Paso 4: Crear SolicitudAtencion
        SolicitudAtencion solicitud = crearSolicitudAtencion(caso, remision, request);

        log.info("Solicitud creada exitosamente con código: {}", caso.getCodigo());

        return buildResponse(caso, solicitud, remision);
    }

    /**
     * Obtiene una solicitud por ID
     */
    @Transactional(readOnly = true)
    public SolicitudAcompanamientoResponse obtenerPorId(Long id) {
        log.info("Obteniendo solicitud con ID: {}", id);
        SolicitudAtencion solicitud = solicitudAtencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con ID: " + id));
        
        Caso caso = solicitud.getCaso();
        return buildResponse(caso, solicitud, solicitud.getRemision());
    }

    /**
     * Obtiene una solicitud por código del caso
     */
    @Transactional(readOnly = true)
    public SolicitudAcompanamientoResponse obtenerPorCodigo(String codigo) {
        log.info("Obteniendo solicitud con código: {}", codigo);
        Caso caso = casoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Caso no encontrado con código: " + codigo));
        
        List<SolicitudAtencion> solicitudes = solicitudAtencionRepository.findByCasoId(caso.getId());
        if (solicitudes.isEmpty()) {
            throw new ResourceNotFoundException("No hay solicitudes para el caso: " + codigo);
        }
        
        SolicitudAtencion solicitud = solicitudes.get(0);
        return buildResponse(caso, solicitud, solicitud.getRemision());
    }

    /**
     * Lista todas las solicitudes
     */
    @Transactional(readOnly = true)
    public List<SolicitudAcompanamientoResponse> listarTodas() {
        log.info("Listando todas las solicitudes de acompañamiento");
        return solicitudAtencionRepository.findAll().stream()
                .map(solicitud -> buildResponse(solicitud.getCaso(), solicitud, solicitud.getRemision()))
                .collect(Collectors.toList());
    }

    // ===========================
    // Métodos auxiliares privados
    // ===========================

    /**
     * Busca una persona existente por número de documento o crea una nueva
     */
    private Persona buscarOCrearPersona(DatosSolicitanteRequest datos) {
        // Buscar persona existente
        Optional<Persona> existente = personaRepository.findByNumeroDocumento(datos.getNumeroDocumento());
        if (existente.isPresent()) {
            log.info("Persona encontrada con documento: {}", datos.getNumeroDocumento());
            return existente.get();
        }

        log.info("Creando nueva persona con documento: {}", datos.getNumeroDocumento());

        // Crear nueva persona
        Persona persona = new Persona();
        persona.setPrimerNombre(datos.getPrimerNombre());
        persona.setSegundoNombre(datos.getSegundoNombre());
        persona.setPrimerApellido(datos.getPrimerApellido());
        persona.setSegundoApellido(datos.getSegundoApellido());
        persona.setNumeroDocumento(datos.getNumeroDocumento());
        
        // Usar la fecha de nacimiento proporcionada
        persona.setFechaNacimiento(datos.getFechaNacimiento());

        // Lookup FK a tablas maestras usando IDs
        persona.setTipoIdentificacion(tipoIdentificacionRepository.findById(datos.getTipoDocumentoId())
                .orElseThrow(() -> new ResourceNotFoundException("TipoIdentificacion no encontrado con ID: " + datos.getTipoDocumentoId())));
        
        Persona personaGuardada = personaRepository.save(persona);

        // Crear correos
        crearCorreos(personaGuardada, datos);

        // Crear teléfonos
        crearTelefonos(personaGuardada, datos);

        return personaGuardada;
    }

    /**
     * Crea registros de correos para la persona
     * Si la lista correos no está vacía, la usa; si no, cae a los campos individuales.
     */
    private void crearCorreos(Persona persona, DatosSolicitanteRequest datos) {
        List<TipoCorreo> tiposCorreo = tipoCorreoRepository.findAll();

        if (datos.getCorreos() != null && !datos.getCorreos().isEmpty()) {
            for (CorreoSolicitanteRequest req : datos.getCorreos()) {
                if (req.getCorreo() == null || req.getCorreo().isBlank()) continue;
                TipoCorreo tipo = tiposCorreo.stream()
                        .filter(t -> t.getNombre().equalsIgnoreCase(req.getTipo()))
                        .findFirst()
                        .orElse(tiposCorreo.stream().findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException("No hay tipos de correo en la base de datos")));
                CorreoPersona correo = new CorreoPersona();
                correo.setIdpersona(persona.getId());
                correo.setIdtipo(tipo.getId());
                correo.setPersona(persona);
                correo.setTipoCorreo(tipo);
                correo.setCorreo(req.getCorreo());
                correo.setDescripcion(req.getDescripcion());
                correoPersonaRepository.save(correo);
            }
            return;
        }

    }

    /**
     * Crea registros de teléfonos para la persona
     * Si la lista telefonos no está vacía, la usa; si no, cae a los campos individuales.
     */
    private void crearTelefonos(Persona persona, DatosSolicitanteRequest datos) {
        List<TipoTelefono> tiposTelefono = tipoTelefonoRepository.findAll();

        if (datos.getTelefonos() != null && !datos.getTelefonos().isEmpty()) {
            for (TelefonoSolicitanteRequest req : datos.getTelefonos()) {
                if (req.getTelefono() == null || req.getTelefono().isBlank()) continue;
                TipoTelefono tipo = tiposTelefono.stream()
                        .filter(t -> t.getNombre().equalsIgnoreCase(req.getTipo()))
                        .findFirst()
                        .orElse(tiposTelefono.stream().findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException("No hay tipos de teléfono en la base de datos")));
                TelefonoPersona telefono = new TelefonoPersona();
                telefono.setIdpersona(persona.getId());
                telefono.setIdtipo(tipo.getId());
                telefono.setPersona(persona);
                telefono.setTipoTelefono(tipo);
                telefono.setTelefono(req.getTelefono());
                telefono.setDescripcion(req.getDescripcion());
                telefonoPersonaRepository.save(telefono);
            }
            return;
        }

    }

    /**
     * Crea un caso con código único
     */
    private Caso crearCaso(Persona persona, SolicitudAcompanamientoRequest request) {
        Caso caso = new Caso();
        caso.setPersona(persona);
        caso.setCodigo(generarCodigoCaso());

        // Lookup FK a tablas maestras usando IDs
        DatosSolicitanteRequest datos = request.getDatosSolicitante();
        
        caso.setIdentidadGenero(identidadGeneroRepository.findById(datos.getIdentidadGeneroId())
                .orElseThrow(() -> new ResourceNotFoundException("IdentidadGenero no encontrada con ID: " + datos.getIdentidadGeneroId())));
        
        if (datos.getCampusId() != null) {
            caso.setCampus(campusRepository.findById(datos.getCampusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Campus no encontrado con ID: " + datos.getCampusId())));
        }
        
        if (datos.getDependenciaId() != null) {
            caso.setDependencia(dependenciaRepository.findById(datos.getDependenciaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dependencia no encontrada con ID: " + datos.getDependenciaId())));
        }
        
        if (datos.getFacultadId() != null) {
            caso.setFacultad(facultadRepository.findById(datos.getFacultadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Facultad no encontrada con ID: " + datos.getFacultadId())));
        }

        return casoRepository.save(caso);
    }

    /**
     * Crea una remisión para reportes indirectos
     */
    private Remision crearRemision(DatosRemitenteRequest datos) {
        // Crear persona simple para remitente (sin validación de documento)
        Persona remitente = new Persona();
        remitente.setPrimerNombre(datos.getPrimerNombre());
        remitente.setSegundoNombre(datos.getSegundoNombre());
        remitente.setPrimerApellido(datos.getPrimerApellido());
        remitente.setSegundoApellido(datos.getSegundoApellido());
        remitente.setNumeroDocumento(
                datos.getNumeroDocumento() != null && !datos.getNumeroDocumento().isBlank()
                        ? datos.getNumeroDocumento()
                        : "REM-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        remitente.setFechaNacimiento(datos.getFechaNacimiento());

        // Tipo de identificación: usa el ID proporcionado o fallback a "OT"
        if (datos.getTipoDocumentoId() != null) {
            remitente.setTipoIdentificacion(tipoIdentificacionRepository.findById(datos.getTipoDocumentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("TipoIdentificacion no encontrado con ID: " + datos.getTipoDocumentoId())));
        } else {
            remitente.setTipoIdentificacion(tipoIdentificacionRepository.findAll().stream()
                    .filter(t -> "OT".equalsIgnoreCase(t.getCodigo()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("TipoIdentificacion 'OT' no encontrado")));
        }

        Persona remitenteGuardado = personaRepository.save(remitente);

        // Crear remisión
        Remision remision = new Remision();
        remision.setRemitente(remitenteGuardado);
        remision.setFecha(LocalDateTime.now());
        
        remision.setCargo(cargoRepository.findById(datos.getCargoId())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo no encontrado con ID: " + datos.getCargoId())));
        
        if (datos.getDependenciaId() != null) {
            remision.setDependencia(dependenciaRepository.findById(datos.getDependenciaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dependencia no encontrada con ID: " + datos.getDependenciaId())));
        }
        
        if (datos.getFacultadId() != null) {
            remision.setFacultad(facultadRepository.findById(datos.getFacultadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Facultad no encontrada con ID: " + datos.getFacultadId())));
        }
        
        if (datos.getCampusId() != null) {
            remision.setCampus(campusRepository.findById(datos.getCampusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Campus no encontrado con ID: " + datos.getCampusId())));
        }

        return remisionRepository.save(remision);
    }

    /**
     * Crea la solicitud de atención
     */
    private SolicitudAtencion crearSolicitudAtencion(Caso caso, Remision remision, SolicitudAcompanamientoRequest request) {
        SolicitudAtencion solicitud = new SolicitudAtencion();
        solicitud.setCaso(caso);
        solicitud.setRemision(remision);
        solicitud.setFecha(LocalDateTime.now());
        
        solicitud.setTipoSolicitud(tipoSolicitudRepository.findById(request.getTipoSolicitudId())
                .orElseThrow(() -> new ResourceNotFoundException("TipoSolicitud no encontrado con ID: " + request.getTipoSolicitudId())));
        
        solicitud.setEstadoSolicitud(estadoSolicitudRepository.findById(EstadoSolicitud.SIN_ASIGNAR.getId())
                .orElseThrow(() -> new ResourceNotFoundException("EstadoSolicitud '" + EstadoSolicitud.SIN_ASIGNAR.getNombre() + "' no encontrado")));

        return solicitudAtencionRepository.save(solicitud);
    }

    /**
     * Construye el response DTO
     */
    private SolicitudAcompanamientoResponse buildResponse(Caso caso, SolicitudAtencion solicitud, Remision remision) {
        Persona solicitante = caso.getPersona();
        
        return SolicitudAcompanamientoResponse.builder()
                .id(solicitud.getId())
                .codigo(caso.getCodigo())
                .tipoSolicitud(solicitud.getTipoSolicitud().getNombre())
                .tipoReporte(remision != null ? "indirecta" : "directa")
                .estado(solicitud.getEstadoSolicitud().getNombre())
                .fechaCreacion(solicitud.getFecha())
                .nombreSolicitante(solicitante.getNombreCompleto())
                .documentoSolicitante(solicitante.getNumeroDocumento())
                .nombreRemitente(remision != null ? remision.getRemitente().getNombreCompleto() : null)
                .mensaje("Solicitud procesada exitosamente")
                .build();
    }

    /**
     * Genera código único para el caso
     * Formato: ACO-YYYY-NNNN
     */
    private String generarCodigoCaso() {
        int anioActual = Year.now().getValue();
        long cantidadDelAnio = casoRepository.countByYear(anioActual);
        int numeroConsecutivo = (int) (cantidadDelAnio + 1);
        return String.format("ACO-%d-%04d", anioActual, numeroConsecutivo);
    }
}
