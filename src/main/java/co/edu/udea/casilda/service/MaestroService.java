package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.response.MaestroDTO;
import co.edu.udea.casilda.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar maestros del sistema.
 * Proporciona métodos para obtener datos de maestros desde la base de datos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MaestroService {

    // Repositories de maestros
    private final PaisRepository paisRepository;
    private final SexoRepository sexoRepository;
    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final EtniaRepository etniaRepository;
    private final IdentidadGeneroRepository identidadGeneroRepository;
    private final OrientacionSexualRepository orientacionSexualRepository;
    private final DiscapacidadRepository discapacidadRepository;
    private final DepartamentoRepository departamentoRepository;
    private final MunicipioRepository municipioRepository;
    private final CampusRepository campusRepository;
    private final DependenciaRepository dependenciaRepository;
    private final FacultadEscuelaInstitutoRepository facultadRepository;
    private final VinculoAgresorVictimaRepository vinculoAgresorRepository;
    private final VinculoUdeARepository vinculoUdeARepository;
    private final ModalidadViolenciaRepository modalidadViolenciaRepository;
    private final ModalidadViolenciaSexualRepository modalidadViolenciaSexualRepository;
    private final CargoRepository cargoRepository;
    private final TipoSolicitudRepository tipoSolicitudRepository;
    private final ProgramaRepository programaRepository;
    private final RoleRepository roleRepository;
    private final JornadaRepository jornadaRepository;
    private final ResultadoContactoTelefonicoRepository resultadoContactoRepository;
    private final RegimenRepository regimenRepository;
    private final EPSRepository epsRepository;
    private final TipoCorreoRepository tipoCorreoRepository;
    private final TipoTelefonoRepository tipoTelefonoRepository;

    /**
     * Obtiene lista de países
     */
    public List<MaestroDTO> obtenerPaises() {
        log.info("Obteniendo países desde la base de datos");
        return paisRepository.findAll().stream()
            .map(p -> new MaestroDTO(p.getId().longValue(), p.getCodigo(), p.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de sexos
     */
    public List<MaestroDTO> obtenerSexos() {
        log.info("Obteniendo sexos desde la base de datos");
        return sexoRepository.findAll().stream()
            .map(s -> new MaestroDTO(s.getId().longValue(), s.getCodigo(), s.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de tipos de identificación
     */
    public List<MaestroDTO> obtenerTiposIdentificacion() {
        log.info("Obteniendo tipos de identificación desde la base de datos");
        return tipoIdentificacionRepository.findAll().stream()
            .map(t -> new MaestroDTO(t.getId().longValue(), t.getCodigo(), t.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de etnias
     */
    public List<MaestroDTO> obtenerEtnias() {
        log.info("Obteniendo etnias desde la base de datos");
        return etniaRepository.findAll().stream()
            .map(e -> new MaestroDTO(e.getId().longValue(), null, e.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de identidades de género
     */
    public List<MaestroDTO> obtenerIdentidadesGenero() {
        log.info("Obteniendo identidades de género desde la base de datos");
        return identidadGeneroRepository.findAll().stream()
            .map(i -> new MaestroDTO(i.getId().longValue(), null, i.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de orientaciones sexuales
     */
    public List<MaestroDTO> obtenerOrientacionesSexuales() {
        log.info("Obteniendo orientaciones sexuales desde la base de datos");
        return orientacionSexualRepository.findAll().stream()
            .map(o -> new MaestroDTO(o.getId().longValue(), null, o.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de discapacidades
     */
    public List<MaestroDTO> obtenerDiscapacidades() {
        log.info("Obteniendo discapacidades desde la base de datos");
        return discapacidadRepository.findAll().stream()
            .map(d -> new MaestroDTO(d.getId().longValue(), null, d.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de departamentos
     */
    public List<MaestroDTO> obtenerDepartamentos() {
        log.info("Obteniendo departamentos desde la base de datos");
        return departamentoRepository.findAll().stream()
            .map(d -> new MaestroDTO(d.getId().longValue(), d.getCodigo(), d.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de municipios
     */
    public List<MaestroDTO> obtenerMunicipios() {
        log.info("Obteniendo municipios desde la base de datos");
        return municipioRepository.findAll().stream()
            .map(m -> new MaestroDTO(m.getId().longValue(), m.getCodigo(), m.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de campus
     */
    public List<MaestroDTO> obtenerCampus() {
        log.info("Obteniendo campus desde la base de datos");
        return campusRepository.findAll().stream()
            .map(c -> new MaestroDTO(c.getId().longValue(), null, c.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de dependencias
     */
    public List<MaestroDTO> obtenerDependencias() {
        log.info("Obteniendo dependencias desde la base de datos");
        return dependenciaRepository.findAll().stream()
            .map(d -> new MaestroDTO(d.getId().longValue(), null, d.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de facultades/escuelas/institutos
     */
    public List<MaestroDTO> obtenerFacultades() {
        log.info("Obteniendo facultades desde la base de datos");
        return facultadRepository.findAll().stream()
            .map(f -> new MaestroDTO(f.getId().longValue(), null, f.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de vínculos agresor-víctima
     */
    public List<MaestroDTO> obtenerVinculosAgresorVictima() {
        log.info("Obteniendo vínculos agresor-víctima desde la base de datos");
        return vinculoAgresorRepository.findAll().stream()
            .map(v -> new MaestroDTO(v.getId().longValue(), null, v.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de vínculos Universidad
     */
    public List<MaestroDTO> obtenerVinculosUdeA() {
        log.info("Obteniendo vínculos Universidad desde la base de datos");
        return vinculoUdeARepository.findAll().stream()
            .map(v -> new MaestroDTO(v.getId().longValue(), null, v.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de modalidades de violencia
     */
    public List<MaestroDTO> obtenerModalidadesViolencia() {
        log.info("Obteniendo modalidades de violencia desde la base de datos");
        return modalidadViolenciaRepository.findAll().stream()
            .map(m -> new MaestroDTO(m.getId().longValue(), null, m.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de modalidades de violencia sexual
     */
    public List<MaestroDTO> obtenerModalidadesViolenciaSexual() {
        log.info("Obteniendo modalidades de violencia sexual desde la base de datos");
        return modalidadViolenciaSexualRepository.findAll().stream()
            .map(m -> new MaestroDTO(m.getId().longValue(), null, m.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de cargos
     */
    public List<MaestroDTO> obtenerCargos() {
        log.info("Obteniendo cargos desde la base de datos");
        return cargoRepository.findAll().stream()
            .map(c -> new MaestroDTO(c.getId().longValue(), null, c.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de tipos de solicitud
     */
    public List<MaestroDTO> obtenerTiposSolicitud() {
        log.info("Obteniendo tipos de solicitud desde la base de datos");
        return tipoSolicitudRepository.findAll().stream()
            .map(t -> new MaestroDTO(t.getId().longValue(), null, t.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de programas académicos
     */
    public List<MaestroDTO> obtenerProgramas() {
        log.info("Obteniendo programas desde la base de datos");
        return programaRepository.findAll().stream()
            .map(p -> new MaestroDTO(p.getId().longValue(), p.getCodigo(), p.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de roles
     */
    public List<MaestroDTO> obtenerRoles() {
        log.info("Obteniendo roles desde la base de datos");
        return roleRepository.findAll().stream()
            .map(r -> new MaestroDTO(r.getId().longValue(), null, r.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de jornadas
     */
    public List<MaestroDTO> obtenerJornadas() {
        log.info("Obteniendo jornadas desde la base de datos");
        return jornadaRepository.findAll().stream()
            .map(j -> new MaestroDTO(j.getId().longValue(), null, j.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de resultados de contacto telefónico
     */
    public List<MaestroDTO> obtenerResultadosContactoTelefonico() {
        log.info("Obteniendo resultados de contacto telefónico desde la base de datos");
        return resultadoContactoRepository.findAll().stream()
            .map(r -> new MaestroDTO(r.getId().longValue(), null, r.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de regímenes de salud
     */
    public List<MaestroDTO> obtenerRegimenes() {
        log.info("Obteniendo regímenes desde la base de datos");
        return regimenRepository.findAll().stream()
            .map(r -> new MaestroDTO(r.getId().longValue(), null, r.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de EPS
     */
    public List<MaestroDTO> obtenerEPS() {
        log.info("Obteniendo EPS desde la base de datos");
        return epsRepository.findAll().stream()
            .map(e -> new MaestroDTO(e.getId().longValue(), null, e.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de tipos de correo
     */
    public List<MaestroDTO> obtenerTiposCorreo() {
        log.info("Obteniendo tipos de correo desde la base de datos");
        return tipoCorreoRepository.findAll().stream()
            .map(tc -> new MaestroDTO(tc.getId().longValue(), null, tc.getNombre()))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene lista de tipos de teléfono
     */
    public List<MaestroDTO> obtenerTiposTelefono() {
        log.info("Obteniendo tipos de teléfono desde la base de datos");
        return tipoTelefonoRepository.findAll().stream()
            .map(tt -> new MaestroDTO(tt.getId().longValue(), null, tt.getNombre()))
            .collect(Collectors.toList());
    }
}
