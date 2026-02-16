package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.*;
import co.edu.udea.casilda.dto.response.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class FormularioWorkflowService {

    private final Map<Long, UsuarioResponse> usuarios = new ConcurrentHashMap<>();
    private final Map<String, List<String>> listas = new ConcurrentHashMap<>();
    private final Map<String, CasoResumenResponse> casos = new ConcurrentHashMap<>();
    private final Map<String, List<ContactoTelefonicoResponse>> contactosPorCaso = new ConcurrentHashMap<>();
    private final Map<String, AuthMockUser> credenciales = new ConcurrentHashMap<>();

    private final AtomicLong usuarioSeq = new AtomicLong(100);
    private final AtomicLong quejaSeq = new AtomicLong(2000);
    private final AtomicLong acompanamientoSeq = new AtomicLong(2000);

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PostConstruct
    void init() {
        credenciales.put("admin@gmail.com", new AuthMockUser("123", "Super Administrador", "Admin", "https://i.pravatar.cc/150?u=1"));
        credenciales.put("revisor@gmail.com", new AuthMockUser("123", "Carlos Revisor", "Revisor", "https://i.pravatar.cc/150?u=2"));
        credenciales.put("user@gmail.com", new AuthMockUser("123", "Juan Ciudadano", "Usuario", "https://i.pravatar.cc/150?u=3"));

        usuarios.put(1L, UsuarioResponse.builder().id(1L).nombre("Admin Sistema").email("admin@fnsp.gov").rol("Admin").estado("Activo").build());
        usuarios.put(2L, UsuarioResponse.builder().id(2L).nombre("Carlos Pérez").email("c.perez@juridico.com").rol("Revisor").estado("Activo").build());
        usuarios.put(3L, UsuarioResponse.builder().id(3L).nombre("Ana García").email("ana.garcia@gmail.com").rol("Consulta").estado("Inactivo").build());
        usuarioSeq.set(3L);

        listas.put("tiposSolicitud", new ArrayList<>(List.of("Psicológico", "Jurídico", "Académico", "Social")));
        listas.put("campus", new ArrayList<>(List.of("Central", "Robledo", "Oriente", "Apartadó")));
        listas.put("dependencias", new ArrayList<>(List.of("Bienestar Universitario", "Talento Humano", "Rectoría")));
        listas.put("facultades", new ArrayList<>(List.of("Medicina", "Salud Pública", "Enfermería", "Artes")));
        listas.put("tiposDocumento", new ArrayList<>(List.of("Cédula de Ciudadanía", "Tarjeta de Identidad", "Pasaporte")));

        addCaso(CasoResumenResponse.builder()
                .id("CAS-2001").nombre("Laura Restrepo").documento("10359874").fecha("2026-02-01")
                .dependencia("Bienestar").profesional("Sin asignar").estado("Abierto activo")
                .tipoSolicitud("Psicosocial").facultad("Artes").campus("Norte").genero("Femenino")
                .edad(20).celular("3109988776").cargo("Estudiante").telefono("6012233")
                .correoInst("l.restrepo@U.edu.co").correoPers("laura.res@gmail.com")
                .remitentePrimerNombre("Laura").remitentePrimerApellido("Restrepo")
                .pacientePrimerNombre("Laura").pacientePrimerApellido("Restrepo")
                .observaciones("Solicita primera cita por ansiedad ante exámenes.")
                .prioridad("Alta").ultimaAccion("Caso recibido").build());

        addCaso(CasoResumenResponse.builder()
                .id("ACO-2002").nombre("Miguel Cano").documento("71234456").fecha("2026-02-03")
                .dependencia("Jurídica").profesional("Sin asignar").estado("Abierto activo")
                .tipoSolicitud("Asesoría").facultad("Derecho").campus("Principal").genero("Masculino")
                .edad(24).celular("3154433221").cargo("Egresado").telefono("6014455")
                .correoInst("m.cano@U.edu.co").correoPers("miguel.c@outlook.com")
                .remitentePrimerNombre("Miguel").remitentePrimerApellido("Cano")
                .pacientePrimerNombre("Miguel").pacientePrimerApellido("Cano")
                .observaciones("Consulta sobre trámites de grado y judicatura.")
                .prioridad("Media").ultimaAccion("Caso recibido").build());

        addCaso(CasoResumenResponse.builder()
                .id("CAS-2003").nombre("Elena Vasquez").documento("43567812").fecha("2026-02-05")
                .dependencia("Salud").profesional("Sin asignar").estado("Abierto activo")
                .tipoSolicitud("Médica").facultad("Ingeniería").campus("Sur").genero("Femenino")
                .edad(21).celular("3201122334").cargo("Estudiante").telefono("6019900")
                .correoInst("e.vasquez@U.edu.co").correoPers("elena.v@gmail.com")
                .remitentePrimerNombre("Elena").remitentePrimerApellido("Vasquez")
                .pacientePrimerNombre("Elena").pacientePrimerApellido("Vasquez")
                .observaciones("Reporte de accidente menor en laboratorios.")
                .prioridad("Alta").ultimaAccion("Caso recibido").build());

        contactosPorCaso.put("CAS-2001", new ArrayList<>(List.of(
                ContactoTelefonicoResponse.builder()
                        .fecha("2026-02-10")
                        .hora("10:00 AM")
                        .jornada("Mañana")
                        .resultado("No contesta")
                        .observacion("Se intentó contacto sin éxito.")
                        .build()
        )));
    }

    public AuthLoginResponse login(AuthLoginRequest request) {
        AuthMockUser authMockUser = credenciales.get(request.getEmail());
        if (authMockUser == null || !authMockUser.password().equals(request.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        return AuthLoginResponse.builder()
                .nombre(authMockUser.nombre())
                .email(request.getEmail())
                .rol(authMockUser.rol())
                .foto(authMockUser.foto())
                .token("mock-jwt-" + UUID.randomUUID())
                .build();
    }

    public List<UsuarioResponse> listarUsuarios() {
        return usuarios.values().stream()
                .sorted(Comparator.comparing(UsuarioResponse::getId))
                .collect(Collectors.toList());
    }

    public UsuarioResponse crearUsuario(UsuarioUpsertRequest request) {
        Long id = usuarioSeq.incrementAndGet();
        UsuarioResponse usuario = UsuarioResponse.builder()
                .id(id)
                .nombre(request.getNombre())
                .email(request.getEmail())
                .rol(request.getRol())
                .estado(request.getEstado() == null || request.getEstado().isBlank() ? "Activo" : request.getEstado())
                .build();
        usuarios.put(id, usuario);
        return usuario;
    }

    public UsuarioResponse actualizarUsuario(Long id, UsuarioUpsertRequest request) {
        UsuarioResponse existente = getUsuario(id);
        existente.setNombre(request.getNombre());
        existente.setEmail(request.getEmail());
        existente.setRol(request.getRol());
        if (request.getEstado() != null && !request.getEstado().isBlank()) {
            existente.setEstado(request.getEstado());
        }
        usuarios.put(id, existente);
        return existente;
    }

    public UsuarioResponse toggleEstadoUsuario(Long id) {
        UsuarioResponse existente = getUsuario(id);
        existente.setEstado("Activo".equalsIgnoreCase(existente.getEstado()) ? "Inactivo" : "Activo");
        usuarios.put(id, existente);
        return existente;
    }

    public void eliminarUsuario(Long id) {
        if (usuarios.remove(id) == null) {
            throw new NoSuchElementException("Usuario no encontrado");
        }
    }

    public ListasResponse obtenerListas() {
        return ListasResponse.builder().listas(listas).build();
    }

    public List<String> agregarItemLista(String nombreLista, ListaItemRequest request) {
        List<String> lista = getLista(nombreLista);
        if (lista.stream().noneMatch(v -> v.equalsIgnoreCase(request.getValor().trim()))) {
            lista.add(request.getValor().trim());
        }
        return lista;
    }

    public List<String> editarItemLista(String nombreLista, ListaEditItemRequest request) {
        List<String> lista = getLista(nombreLista);
        int index = lista.indexOf(request.getOriginal());
        if (index < 0) {
            throw new NoSuchElementException("Ítem no encontrado en la lista");
        }
        lista.set(index, request.getNuevo().trim());
        return lista;
    }

    public List<String> eliminarItemLista(String nombreLista, String item) {
        List<String> lista = getLista(nombreLista);
        lista.removeIf(v -> v.equalsIgnoreCase(item));
        return lista;
    }

    public RegistroResponse registrarQueja(QuejaRequest request) {
        String codigo = "CAS-" + quejaSeq.incrementAndGet();
        String nombre = Optional.ofNullable(request.getNombreVictima()).filter(s -> !s.isBlank()).orElse("Anónimo");
        String apellidos = Optional.ofNullable(request.getApellidosVictima()).orElse("");

        CasoResumenResponse caso = CasoResumenResponse.builder()
                .id(codigo)
                .nombre((nombre + " " + apellidos).trim())
                .documento("S/N")
                .fecha(LocalDate.now().format(FECHA))
                .dependencia("Bienestar")
                .profesional("Sin asignar")
                .estado("Abierto activo")
                .tipoSolicitud("Queja / Reclamo")
                .facultad("No definida")
                .campus("No definido")
                .genero(request.getGeneroVictima())
                .edad(0)
                .celular(Optional.ofNullable(request.getWhatsappContacto()).orElse(""))
                .cargo(Optional.ofNullable(request.getCargoVictima()).orElse(""))
                .correoInst(Optional.ofNullable(request.getCorreoVictima()).orElse(""))
                .correoPers(Optional.ofNullable(request.getCorreoContacto()).orElse(""))
                .remitentePrimerNombre(nombre)
                .remitentePrimerApellido(apellidos)
                .pacientePrimerNombre(nombre)
                .pacientePrimerApellido(apellidos)
                .observaciones(request.getDescripcion())
                .prioridad("Alta")
                .ultimaAccion("Queja registrada")
                .build();
        addCaso(caso);

        return RegistroResponse.builder()
                .codigo(codigo)
                .tipo("QUEJA")
                .mensaje("Queja registrada correctamente")
                .fecha(LocalDate.now().toString())
                .build();
    }

    public RegistroResponse registrarAcompanamiento(AcompanamientoRequest request) {
        String codigo = "ACO-" + acompanamientoSeq.incrementAndGet();
        String nombreCompleto = (request.getPrimerNombre() + " " + request.getPrimerApellido()).trim();

        CasoResumenResponse caso = CasoResumenResponse.builder()
                .id(codigo)
                .nombre(nombreCompleto)
                .documento(request.getNumeroDocumento())
                .fecha(LocalDate.now().format(FECHA))
                .dependencia(Optional.ofNullable(request.getDependencia()).orElse("No definida"))
                .profesional("Sin asignar")
                .estado("Abierto activo")
                .tipoSolicitud(Optional.ofNullable(request.getTipoSolicitud()).orElse("Acompañamiento"))
                .facultad(request.getFacultad())
                .campus(Optional.ofNullable(request.getCampus()).orElse("No definido"))
                .genero(request.getIdentidadGenero())
                .edad(0)
                .celular(request.getCelular())
                .cargo(Optional.ofNullable(request.getCargo()).orElse(""))
                .correoInst(request.getCorreoInstitucional())
                .correoPers(request.getCorreoPersonal())
                .remitentePrimerNombre(Optional.ofNullable(request.getRemitentePrimerNombre()).orElse(request.getPrimerNombre()))
                .remitentePrimerApellido(Optional.ofNullable(request.getRemitentePrimerApellido()).orElse(request.getPrimerApellido()))
                .pacientePrimerNombre(request.getPrimerNombre())
                .pacientePrimerApellido(request.getPrimerApellido())
                .observaciones("Solicitud de acompañamiento registrada")
                .prioridad("Media")
                .ultimaAccion("Solicitud registrada")
                .build();
        addCaso(caso);

        return RegistroResponse.builder()
                .codigo(codigo)
                .tipo("ACOMPANAMIENTO")
                .mensaje("Solicitud de acompañamiento registrada correctamente")
                .fecha(LocalDate.now().toString())
                .build();
    }

    public List<CasoResumenResponse> listarCasos() {
        return casos.values().stream()
                .sorted(Comparator.comparing(CasoResumenResponse::getFecha).reversed())
                .collect(Collectors.toList());
    }

    public List<CasoResumenResponse> listarCasosSinAsignar() {
        return listarCasos().stream()
                .filter(c -> "Sin asignar".equalsIgnoreCase(c.getProfesional()))
                .collect(Collectors.toList());
    }

    public CasoResumenResponse actualizarCaso(String codigo, CasoDetalleUpdateRequest request) {
        CasoResumenResponse caso = getCaso(codigo);
        if (request.getRemitentePrimerNombre() != null) caso.setRemitentePrimerNombre(request.getRemitentePrimerNombre());
        if (request.getRemitenteSegundoNombre() != null) caso.setRemitenteSegundoNombre(request.getRemitenteSegundoNombre());
        if (request.getRemitentePrimerApellido() != null) caso.setRemitentePrimerApellido(request.getRemitentePrimerApellido());
        if (request.getRemitenteSegundoApellido() != null) caso.setRemitenteSegundoApellido(request.getRemitenteSegundoApellido());
        if (request.getCargo() != null) caso.setCargo(request.getCargo());
        if (request.getDependencia() != null) caso.setDependencia(request.getDependencia());
        if (request.getFacultad() != null) caso.setFacultad(request.getFacultad());
        if (request.getCampus() != null) caso.setCampus(request.getCampus());
        if (request.getPacientePrimerNombre() != null) caso.setPacientePrimerNombre(request.getPacientePrimerNombre());
        if (request.getPacienteSegundoNombre() != null) caso.setPacienteSegundoNombre(request.getPacienteSegundoNombre());
        if (request.getPacientePrimerApellido() != null) caso.setPacientePrimerApellido(request.getPacientePrimerApellido());
        if (request.getPacienteSegundoApellido() != null) caso.setPacienteSegundoApellido(request.getPacienteSegundoApellido());
        if (request.getDocumento() != null) caso.setDocumento(request.getDocumento());
        if (request.getEdad() != null) caso.setEdad(request.getEdad());
        if (request.getCelular() != null) caso.setCelular(request.getCelular());
        if (request.getCorreoInst() != null) caso.setCorreoInst(request.getCorreoInst());
        casos.put(codigo, caso);
        return caso;
    }

    public RegistroResponse asignarCaso(RepartoRequest request) {
        CasoResumenResponse caso = getCaso(request.getCodigoCaso());
        caso.setProfesional(request.getAsignadoA());
        caso.setEstado("Abierto activo");
        caso.setObservaciones(request.getObservaciones());
        caso.setUltimaAccion("Asignado a " + request.getAsignadoA() + " - " + request.getServicio());
        casos.put(caso.getId(), caso);

        return RegistroResponse.builder()
                .codigo(caso.getId())
                .tipo("REPARTO")
                .mensaje("Caso asignado correctamente")
                .fecha(LocalDate.now().toString())
                .build();
    }

    public List<CasoResumenResponse> listarMisAsignaciones(String profesional) {
        return listarCasos().stream()
                .filter(c -> c.getProfesional() != null && c.getProfesional().equalsIgnoreCase(profesional))
                .collect(Collectors.toList());
    }

    public List<ContactoTelefonicoResponse> listarContactos(String codigoCaso) {
        getCaso(codigoCaso);
        return contactosPorCaso.getOrDefault(codigoCaso, new ArrayList<>());
    }

    public List<ContactoTelefonicoResponse> registrarContacto(String codigoCaso, ContactoTelefonicoRequest request) {
        getCaso(codigoCaso);
        ContactoTelefonicoResponse nuevo = ContactoTelefonicoResponse.builder()
                .fecha(request.getFecha())
                .hora(request.getHora())
                .jornada(request.getJornada())
                .resultado(request.getResultado())
                .observacion(request.getObservacion())
                .build();

        List<ContactoTelefonicoResponse> contactos = contactosPorCaso.computeIfAbsent(codigoCaso, key -> new ArrayList<>());
        contactos.add(0, nuevo);
        return contactos;
    }

    public SeguimientoResponse buscarSeguimiento(String codigoCaso) {
        CasoResumenResponse caso = getCaso(codigoCaso);

        List<SeguimientoResponse.EtapaResponse> etapas;
        String tipo;
        int estadoActual;
        String detalle;

        if (codigoCaso.startsWith("ACO-")) {
            tipo = "Acompañamiento";
            etapas = List.of(
                    etapa(1, "Solicitud Recibida", "pending_actions"),
                    etapa(2, "Asignación de Profesional", "person_add"),
                    etapa(3, "En Intervención/Citas", "record_voice_over"),
                    etapa(4, "Caso Cerrado", "verified")
            );
            estadoActual = "Sin asignar".equalsIgnoreCase(caso.getProfesional()) ? 1 : 2;
            detalle = "Sin asignar".equalsIgnoreCase(caso.getProfesional())
                    ? "Solicitud recibida y pendiente de asignación."
                    : "Se asignó profesional para iniciar intervención.";
        } else {
            tipo = "Queja / Reclamo";
            etapas = List.of(
                    etapa(1, "Recibido", "assignment"),
                    etapa(2, "En Revisión Jurídica", "gavel"),
                    etapa(3, "Investigación", "search"),
                    etapa(4, "Respuesta Emitida", "mark_email_read")
            );
            estadoActual = "Cerrado".equalsIgnoreCase(caso.getEstado()) ? 4 : 1;
            detalle = "Cerrado".equalsIgnoreCase(caso.getEstado())
                    ? "La respuesta final fue emitida al solicitante."
                    : "Su reporte fue recibido y está en revisión.";
        }

        return SeguimientoResponse.builder()
                .codigo(caso.getId())
                .tipo(tipo)
                .estadoActual(estadoActual)
                .diasDesdeRecepcion(2)
                .fechaActualizacion(LocalDate.now().toString())
                .detalle(detalle)
                .etapas(etapas)
                .build();
    }

    public RegistroResponse registrarAtencion(AtencionRegistroRequest request) {
        return RegistroResponse.builder()
                .codigo("ATN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .tipo("ATENCION")
                .mensaje("Atención registrada exitosamente para " + request.getPrimerNombre() + " " + request.getPrimerApellido())
                .fecha(LocalDate.now().toString())
                .build();
    }

    public DashboardRevisorResponse obtenerDashboard() {
        List<CasoResumenResponse> all = listarCasos();

        int mujeres = (int) all.stream().filter(c -> c.getGenero() != null && c.getGenero().toLowerCase().contains("fem")).count();
        int hombres = (int) all.stream().filter(c -> c.getGenero() != null && c.getGenero().toLowerCase().contains("masc")).count();
        int estudiantes = (int) all.stream().filter(c -> c.getCargo() != null && c.getCargo().toLowerCase().contains("estudiante")).count();
        int docentes = (int) all.stream().filter(c -> c.getCargo() != null && c.getCargo().toLowerCase().contains("docente")).count();
        int administrativos = Math.max(0, all.size() - estudiantes - docentes);

        List<DashboardRevisorResponse.CasoTarjeta> quejas = all.stream()
                .filter(c -> c.getId().startsWith("CAS-"))
                .limit(5)
                .map(c -> DashboardRevisorResponse.CasoTarjeta.builder()
                        .id(c.getId())
                        .fecha(c.getFecha())
                        .victima(c.getCargo())
                        .estado(c.getEstado())
                        .build())
                .collect(Collectors.toList());

        List<DashboardRevisorResponse.CasoTarjeta> acompanamientos = all.stream()
                .filter(c -> c.getId().startsWith("ACO-"))
                .limit(5)
                .map(c -> DashboardRevisorResponse.CasoTarjeta.builder()
                        .id(c.getId())
                        .fecha(c.getFecha())
                        .victima(c.getNombre())
                        .estado("Sin asignar".equalsIgnoreCase(c.getProfesional()) ? "Sin agendar" : "En curso")
                        .build())
                .collect(Collectors.toList());

        return DashboardRevisorResponse.builder()
                .stats(DashboardRevisorResponse.Stats.builder()
                        .total(all.size())
                        .mujeres(mujeres)
                        .hombres(hombres)
                        .estudiantes(estudiantes)
                        .docentes(docentes)
                        .administrativos(administrativos)
                        .build())
                .quejas(quejas)
                .acompanamientos(acompanamientos)
                .build();
    }

    private UsuarioResponse getUsuario(Long id) {
        UsuarioResponse usuario = usuarios.get(id);
        if (usuario == null) {
            throw new NoSuchElementException("Usuario no encontrado");
        }
        return usuario;
    }

    private List<String> getLista(String nombreLista) {
        List<String> lista = listas.get(nombreLista);
        if (lista == null) {
            throw new NoSuchElementException("Lista no encontrada: " + nombreLista);
        }
        return lista;
    }

    private CasoResumenResponse getCaso(String codigoCaso) {
        CasoResumenResponse caso = casos.get(codigoCaso);
        if (caso == null) {
            throw new NoSuchElementException("Caso no encontrado: " + codigoCaso);
        }
        return caso;
    }

    private SeguimientoResponse.EtapaResponse etapa(int id, String nombre, String icon) {
        return SeguimientoResponse.EtapaResponse.builder().id(id).nombre(nombre).icon(icon).build();
    }

    private void addCaso(CasoResumenResponse caso) {
        casos.put(caso.getId(), caso);
    }

    private record AuthMockUser(String password, String nombre, String rol, String foto) {
    }
}
