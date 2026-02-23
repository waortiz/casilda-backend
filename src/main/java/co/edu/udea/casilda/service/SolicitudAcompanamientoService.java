package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.DatosRemitenteRequest;
import co.edu.udea.casilda.dto.request.DatosSolicitanteRequest;
import co.edu.udea.casilda.dto.request.SolicitudAcompanamientoRequest;
import co.edu.udea.casilda.dto.response.SolicitudAcompanamientoResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.*;
import co.edu.udea.casilda.model.enums.EstadoSolicitud;
import co.edu.udea.casilda.model.enums.TipoSolicitud;
import co.edu.udea.casilda.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final EtniaRepository etniaRepository;
    private final PaisRepository paisRepository;
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
            remision = crearRemision(request.getDatosRemitente(), request.getDatosSolicitante());
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
        
        // Calcular fecha de nacimiento aproximada desde edad
        persona.setFechaNacimiento(calcularFechaNacimiento(datos.getEdad()));

        // Lookup FK a tablas maestras usando IDs
        persona.setTipoIdentificacion(tipoIdentificacionRepository.findById(datos.getTipoDocumentoId())
                .orElseThrow(() -> new ResourceNotFoundException("TipoIdentificacion no encontrado con ID: " + datos.getTipoDocumentoId())));
        
        IdentidadGenero identidadGenero = identidadGeneroRepository.findById(datos.getIdentidadGeneroId())
                .orElseThrow(() -> new ResourceNotFoundException("IdentidadGenero no encontrada con ID: " + datos.getIdentidadGeneroId()));
        
        persona.setSexo(inferirSexoDesdeIdentidadGenero(identidadGenero.getNombre()));
        
        persona.setEtnia(etniaRepository.findAll().stream()
                .filter(e -> e.getNombre().contains("Ningún grupo"))
                .findFirst()
                .orElse(etniaRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("No hay etnias en la base de datos"))));
        
        persona.setPaisNacimiento(paisRepository.findAll().stream()
                .filter(p -> "CO".equalsIgnoreCase(p.getCodigo()))
                .findFirst()
                .orElse(paisRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("No hay países en la base de datos"))));

        Persona personaGuardada = personaRepository.save(persona);

        // Crear correos
        crearCorreos(personaGuardada, datos);

        // Crear teléfonos
        crearTelefonos(personaGuardada, datos);

        return personaGuardada;
    }

    /**
     * Crea registros de correos para la persona
     */
    private void crearCorreos(Persona persona, DatosSolicitanteRequest datos) {
        // Correo institucional
        if (datos.getCorreoInstitucional() != null && !datos.getCorreoInstitucional().isBlank()) {
            TipoCorreo tipoInstitucional = tipoCorreoRepository.findAll().stream()
                    .filter(t -> t.getNombre().contains("Institucional"))
                    .findFirst()
                    .orElse(tipoCorreoRepository.findAll().stream().findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("No hay tipos de correo en la base de datos")));
            
            CorreoPersona correoInst = new CorreoPersona();
            correoInst.setIdpersona(persona.getId());
            correoInst.setIdtipo(tipoInstitucional.getId());
            correoInst.setPersona(persona);
            correoInst.setTipoCorreo(tipoInstitucional);
            correoInst.setCorreo(datos.getCorreoInstitucional());
            correoInst.setDescripcion("Correo institucional");
            correoPersonaRepository.save(correoInst);
        }

        // Correo personal
        if (datos.getCorreoPersonal() != null && !datos.getCorreoPersonal().isBlank()) {
            TipoCorreo tipoPersonal = tipoCorreoRepository.findAll().stream()
                    .filter(t -> t.getNombre().contains("Personal"))
                    .findFirst()
                    .orElse(tipoCorreoRepository.findAll().stream().findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("No hay tipos de correo en la base de datos")));
            
            CorreoPersona correoPersonal = new CorreoPersona();
            correoPersonal.setIdpersona(persona.getId());
            correoPersonal.setIdtipo(tipoPersonal.getId());
            correoPersonal.setPersona(persona);
            correoPersonal.setTipoCorreo(tipoPersonal);
            correoPersonal.setCorreo(datos.getCorreoPersonal());
            correoPersonal.setDescripcion("Correo personal");
            correoPersonaRepository.save(correoPersonal);
        }
    }

    /**
     * Crea registros de teléfonos para la persona
     */
    private void crearTelefonos(Persona persona, DatosSolicitanteRequest datos) {
        // Teléfono principal
        if (datos.getCelular() != null && !datos.getCelular().isBlank()) {
            TipoTelefono tipoCelular = tipoTelefonoRepository.findAll().stream()
                    .filter(t -> t.getNombre().contains("Celular"))
                    .findFirst()
                    .orElse(tipoTelefonoRepository.findAll().stream().findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("No hay tipos de teléfono en la base de datos")));
            
            TelefonoPersona celular = new TelefonoPersona();
            celular.setIdpersona(persona.getId());
            celular.setIdtipo(tipoCelular.getId());
            celular.setPersona(persona);
            celular.setTipoTelefono(tipoCelular);
            celular.setTelefono(datos.getCelular());
            celular.setDescripcion("Celular principal");
            telefonoPersonaRepository.save(celular);
        }

        // Teléfono alterno - usar WhatsApp como tipo diferente
        if (datos.getCelularAlterno() != null && !datos.getCelularAlterno().isBlank()) {
            TipoTelefono tipoAlterno = tipoTelefonoRepository.findAll().stream()
                    .filter(t -> t.getNombre().contains("WhatsApp"))
                    .findFirst()
                    .orElse(tipoTelefonoRepository.findAll().stream()
                            .filter(t -> !t.getNombre().contains("Celular"))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("No hay tipos de teléfono alternativos en la base de datos")));
            
            TelefonoPersona alterno = new TelefonoPersona();
            alterno.setIdpersona(persona.getId());
            alterno.setIdtipo(tipoAlterno.getId());
            alterno.setPersona(persona);
            alterno.setTipoTelefono(tipoAlterno);
            alterno.setTelefono(datos.getCelularAlterno());
            alterno.setDescripcion("Celular alterno");
            telefonoPersonaRepository.save(alterno);
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
        
        caso.setCampus(campusRepository.findById(datos.getCampusId())
                .orElseThrow(() -> new ResourceNotFoundException("Campus no encontrado con ID: " + datos.getCampusId())));
        
        caso.setDependencia(dependenciaRepository.findById(datos.getDependenciaId())
                .orElseThrow(() -> new ResourceNotFoundException("Dependencia no encontrada con ID: " + datos.getDependenciaId())));
        
        caso.setFacultad(facultadRepository.findById(datos.getFacultadId())
                .orElseThrow(() -> new ResourceNotFoundException("Facultad no encontrada con ID: " + datos.getFacultadId())));

        return casoRepository.save(caso);
    }

    /**
     * Crea una remisión para reportes indirectos
     */
    private Remision crearRemision(DatosRemitenteRequest datos, DatosSolicitanteRequest solDatos) {
        // Crear persona simple para remitente (sin validación de documento)
        Persona remitente = new Persona();
        remitente.setPrimerNombre(datos.getPrimerNombre());
        remitente.setSegundoNombre(datos.getSegundoNombre());
        remitente.setPrimerApellido(datos.getPrimerApellido());
        remitente.setSegundoApellido(datos.getSegundoApellido());
        remitente.setNumeroDocumento("REMI-" + System.currentTimeMillis());
        remitente.setFechaNacimiento(LocalDate.of(1980, 1, 1));
        
        // Valores por defecto para FK requeridos
        remitente.setTipoIdentificacion(tipoIdentificacionRepository.findAll().stream()
                .filter(t -> "OT".equalsIgnoreCase(t.getCodigo()))
                .findFirst()
                .orElse(tipoIdentificacionRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("No hay tipos de identificación"))));
        
        remitente.setSexo(sexoRepository.findAll().stream()
                .filter(s -> "NB".equalsIgnoreCase(s.getCodigo()))
                .findFirst()
                .orElse(sexoRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("No hay sexos"))));
        
        remitente.setEtnia(etniaRepository.findAll().stream()
                .filter(e -> e.getNombre().contains("Ningún grupo"))
                .findFirst()
                .orElse(etniaRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("No hay etnias"))));
        
        remitente.setPaisNacimiento(paisRepository.findAll().stream()
                .filter(p -> "CO".equalsIgnoreCase(p.getCodigo()))
                .findFirst()
                .orElse(paisRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("No hay países"))));

        Persona remitenteGuardado = personaRepository.save(remitente);

        // Crear remisión
        Remision remision = new Remision();
        remision.setRemitente(remitenteGuardado);
        remision.setFecha(LocalDateTime.now());
        
        remision.setCargo(cargoRepository.findById(datos.getCargoId())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo no encontrado con ID: " + datos.getCargoId())));
        
        remision.setDependencia(dependenciaRepository.findById(solDatos.getDependenciaId())
                .orElseThrow(() -> new ResourceNotFoundException("Dependencia no encontrada con ID: " + solDatos.getDependenciaId())));
        
        remision.setFacultad(facultadRepository.findById(solDatos.getFacultadId())
                .orElseThrow(() -> new ResourceNotFoundException("Facultad no encontrada con ID: " + solDatos.getFacultadId())));
        
        remision.setCampus(campusRepository.findById(solDatos.getCampusId())
                .orElseThrow(() -> new ResourceNotFoundException("Campus no encontrado con ID: " + solDatos.getCampusId())));

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
                .correoSolicitante(obtenerCorreoInstitucional(solicitante))
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

    /**
     * Calcula fecha de nacimiento aproximada desde edad
     */
    private LocalDate calcularFechaNacimiento(Integer edad) {
        if (edad == null || edad <= 0) {
            return LocalDate.of(2000, 1, 1);
        }
        return LocalDate.now().minusYears(edad);
    }

    /**
     * Infiere el sexo desde la identidad de género
     */
    private Sexo inferirSexoDesdeIdentidadGenero(String identidadGenero) {
        if (identidadGenero == null) {
            return sexoRepository.findAll().stream()
                    .filter(s -> "NB".equalsIgnoreCase(s.getCodigo()))
                    .findFirst()
                    .orElse(sexoRepository.findAll().stream().findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("No hay sexos en la base de datos")));
        }

        String idLower = identidadGenero.toLowerCase();
        if (idLower.contains("mujer") || idLower.contains("femenin")) {
            return sexoRepository.findAll().stream()
                    .filter(s -> "M".equalsIgnoreCase(s.getCodigo()))
                    .findFirst()
                    .orElse(sexoRepository.findAll().stream()
                            .filter(s -> "H".equalsIgnoreCase(s.getCodigo()))
                            .findFirst().orElse(null));
        } else if (idLower.contains("hombre") || idLower.contains("masculin")) {
            return sexoRepository.findAll().stream()
                    .filter(s -> "H".equalsIgnoreCase(s.getCodigo()))
                    .findFirst()
                    .orElse(sexoRepository.findAll().stream()
                            .filter(s -> "M".equalsIgnoreCase(s.getCodigo()))
                            .findFirst().orElse(null));
        }
        
        return sexoRepository.findAll().stream()
                .filter(s -> "NB".equalsIgnoreCase(s.getCodigo()))
                .findFirst()
                .orElse(sexoRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("No hay sexos en la base de datos")));
    }

    /**
     * Obtiene el correo institucional de una persona
     */
    private String obtenerCorreoInstitucional(Persona persona) {
        return persona.getCorreos().stream()
                .filter(correo -> correo.getTipoCorreo().getNombre().contains("Institucional"))
                .map(CorreoPersona::getCorreo)
                .findFirst()
                .orElse(persona.getCorreos().stream()
                        .map(CorreoPersona::getCorreo)
                        .findFirst()
                        .orElse(null));
    }
}
