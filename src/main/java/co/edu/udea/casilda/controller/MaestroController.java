package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.request.MaestroRequest;
import co.edu.udea.casilda.dto.response.MaestroDTO;
import co.edu.udea.casilda.service.MaestroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para maestros
 * Proporciona endpoints para obtener datos de maestros del sistema
 */
@RestController
@RequestMapping("/maestros")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Maestros", description = "Endpoints para obtener datos de maestros")
public class MaestroController {

    private final MaestroService catalogoService;

    @GetMapping("/paises")
    @Operation(summary = "Obtener lista de países")
    public ResponseEntity<List<MaestroDTO>> obtenerPaises() {
        return ResponseEntity.ok(catalogoService.obtenerPaises());
    }

    @GetMapping("/sexos")
    @Operation(summary = "Obtener lista de sexos")
    public ResponseEntity<List<MaestroDTO>> obtenerSexos() {
        return ResponseEntity.ok(catalogoService.obtenerSexos());
    }

    @GetMapping("/tipos-identificacion")
    @Operation(summary = "Obtener lista de tipos de identificación")
    public ResponseEntity<List<MaestroDTO>> obtenerTiposIdentificacion() {
        return ResponseEntity.ok(catalogoService.obtenerTiposIdentificacion());
    }

    @GetMapping("/etnias")
    @Operation(summary = "Obtener lista de etnias")
    public ResponseEntity<List<MaestroDTO>> obtenerEtnias() {
        return ResponseEntity.ok(catalogoService.obtenerEtnias());
    }

    @GetMapping("/identidades-genero")
    @Operation(summary = "Obtener lista de identidades de género")
    public ResponseEntity<List<MaestroDTO>> obtenerIdentidadesGenero() {
        return ResponseEntity.ok(catalogoService.obtenerIdentidadesGenero());
    }

    @GetMapping("/orientaciones-sexuales")
    @Operation(summary = "Obtener lista de orientaciones sexuales")
    public ResponseEntity<List<MaestroDTO>> obtenerOrientacionesSexuales() {
        return ResponseEntity.ok(catalogoService.obtenerOrientacionesSexuales());
    }

    @GetMapping("/discapacidades")
    @Operation(summary = "Obtener lista de discapacidades")
    public ResponseEntity<List<MaestroDTO>> obtenerDiscapacidades() {
        return ResponseEntity.ok(catalogoService.obtenerDiscapacidades());
    }

    @GetMapping("/departamentos")
    @Operation(summary = "Obtener lista de departamentos")
    public ResponseEntity<List<MaestroDTO>> obtenerDepartamentos() {
        return ResponseEntity.ok(catalogoService.obtenerDepartamentos());
    }

    @GetMapping("/municipios")
    @Operation(summary = "Obtener lista de municipios")
    public ResponseEntity<List<MaestroDTO>> obtenerMunicipios() {
        return ResponseEntity.ok(catalogoService.obtenerMunicipios());
    }

    @GetMapping("/campus")
    @Operation(summary = "Obtener lista de campus")
    public ResponseEntity<List<MaestroDTO>> obtenerCampus() {
        return ResponseEntity.ok(catalogoService.obtenerCampus());
    }

    @GetMapping("/dependencias")
    @Operation(summary = "Obtener lista de dependencias")
    public ResponseEntity<List<MaestroDTO>> obtenerDependencias() {
        return ResponseEntity.ok(catalogoService.obtenerDependencias());
    }

    @GetMapping("/facultades")
    @Operation(summary = "Obtener lista de facultades/escuelas/institutos")
    public ResponseEntity<List<MaestroDTO>> obtenerFacultades() {
        return ResponseEntity.ok(catalogoService.obtenerFacultades());
    }

    @GetMapping("/roles")
    @Operation(summary = "Obtener lista de roles")
    public ResponseEntity<List<MaestroDTO>> obtenerRoles() {
        return ResponseEntity.ok(catalogoService.obtenerRoles());
    }

    @GetMapping("/vinculos-agresor-victima")
    @Operation(summary = "Obtener lista de vínculos agresor-víctima")
    public ResponseEntity<List<MaestroDTO>> obtenerVinculosAgresorVictima() {
        return ResponseEntity.ok(catalogoService.obtenerVinculosAgresorVictima());
    }

    @GetMapping("/vinculos-udea")
    @Operation(summary = "Obtener lista de vínculos Universidad")
    public ResponseEntity<List<MaestroDTO>> obtenerVinculosUdeA() {
        return ResponseEntity.ok(catalogoService.obtenerVinculosUdeA());
    }

    @GetMapping("/modalidades-violencia")
    @Operation(summary = "Obtener lista de modalidades de violencia")
    public ResponseEntity<List<MaestroDTO>> obtenerModalidadesViolencia() {
        return ResponseEntity.ok(catalogoService.obtenerModalidadesViolencia());
    }

    @GetMapping("/modalidades-violencia-sexual")
    @Operation(summary = "Obtener lista de modalidades de violencia sexual")
    public ResponseEntity<List<MaestroDTO>> obtenerModalidadesViolenciaSexual() {
        return ResponseEntity.ok(catalogoService.obtenerModalidadesViolenciaSexual());
    }

    @GetMapping("/cargos")
    @Operation(summary = "Obtener lista de cargos")
    public ResponseEntity<List<MaestroDTO>> obtenerCargos() {
        return ResponseEntity.ok(catalogoService.obtenerCargos());
    }

    @GetMapping("/tipos-solicitud")
    @Operation(summary = "Obtener lista de tipos de solicitud")
    public ResponseEntity<List<MaestroDTO>> obtenerTiposSolicitud() {
        return ResponseEntity.ok(catalogoService.obtenerTiposSolicitud());
    }

    @GetMapping("/programas")
    @Operation(summary = "Obtener lista de programas académicos")
    public ResponseEntity<List<MaestroDTO>> obtenerProgramas() {
        return ResponseEntity.ok(catalogoService.obtenerProgramas());
    }

    @GetMapping("/resultados-contacto-telefonico")
    @Operation(summary = "Obtener lista de resultados de contacto telefónico")
    public ResponseEntity<List<MaestroDTO>> obtenerResultadosContactoTelefonico() {
        return ResponseEntity.ok(catalogoService.obtenerResultadosContactoTelefonico());
    }

    @GetMapping("/regimenes")
    @Operation(summary = "Obtener lista de regímenes de salud")
    public ResponseEntity<List<MaestroDTO>> obtenerRegimenes() {
        return ResponseEntity.ok(catalogoService.obtenerRegimenes());
    }

    @GetMapping("/eps")
    @Operation(summary = "Obtener lista de EPS")
    public ResponseEntity<List<MaestroDTO>> obtenerEPS() {
        return ResponseEntity.ok(catalogoService.obtenerEPS());
    }

    @GetMapping("/tipos-correo")
    @Operation(summary = "Obtener lista de tipos de correo")
    public ResponseEntity<List<MaestroDTO>> obtenerTiposCorreo() {
        return ResponseEntity.ok(catalogoService.obtenerTiposCorreo());
    }

    @GetMapping("/tipos-telefono")
    @Operation(summary = "Obtener lista de tipos de teléfono")
    public ResponseEntity<List<MaestroDTO>> obtenerTiposTelefono() {
        return ResponseEntity.ok(catalogoService.obtenerTiposTelefono());
    }

    @GetMapping("/tipos-asignacion")
    @Operation(summary = "Obtener lista de tipos de asignación")
    public ResponseEntity<List<MaestroDTO>> obtenerTiposAsignacion() {
        return ResponseEntity.ok(catalogoService.obtenerTiposAsignacion());
    }

    @GetMapping("/tipos-servicio")
    @Operation(summary = "Obtener lista de tipos de servicio")
    public ResponseEntity<List<MaestroDTO>> obtenerTiposServicio() {
        return ResponseEntity.ok(catalogoService.obtenerTiposServicio());
    }

    // ==================== ENDPOINTS CRUD PARA MAESTROS ====================

    // TIPOS DE SOLICITUD
    @PostMapping("/tipos-solicitud")
    @Operation(summary = "Crear un nuevo tipo de solicitud")
    public ResponseEntity<MaestroDTO> crearTipoSolicitud(@Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.crearTipoSolicitud(request));
    }

    @PutMapping("/tipos-solicitud/{id}")
    @Operation(summary = "Actualizar un tipo de solicitud existente")
    public ResponseEntity<MaestroDTO> actualizarTipoSolicitud(
            @PathVariable Integer id, 
            @Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.ok(catalogoService.actualizarTipoSolicitud(id, request));
    }

    @DeleteMapping("/tipos-solicitud/{id}")
    @Operation(summary = "Eliminar un tipo de solicitud")
    public ResponseEntity<Void> eliminarTipoSolicitud(@PathVariable Integer id) {
        catalogoService.eliminarTipoSolicitud(id);
        return ResponseEntity.noContent().build();
    }

    // CAMPUS
    @PostMapping("/campus")
    @Operation(summary = "Crear un nuevo campus")
    public ResponseEntity<MaestroDTO> crearCampus(@Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.crearCampus(request));
    }

    @PutMapping("/campus/{id}")
    @Operation(summary = "Actualizar un campus existente")
    public ResponseEntity<MaestroDTO> actualizarCampus(
            @PathVariable Integer id, 
            @Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.ok(catalogoService.actualizarCampus(id, request));
    }

    @DeleteMapping("/campus/{id}")
    @Operation(summary = "Eliminar un campus")
    public ResponseEntity<Void> eliminarCampus(@PathVariable Integer id) {
        catalogoService.eliminarCampus(id);
        return ResponseEntity.noContent().build();
    }

    // DEPENDENCIAS
    @PostMapping("/dependencias")
    @Operation(summary = "Crear una nueva dependencia")
    public ResponseEntity<MaestroDTO> crearDependencia(@Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.crearDependencia(request));
    }

    @PutMapping("/dependencias/{id}")
    @Operation(summary = "Actualizar una dependencia existente")
    public ResponseEntity<MaestroDTO> actualizarDependencia(
            @PathVariable Integer id, 
            @Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.ok(catalogoService.actualizarDependencia(id, request));
    }

    @DeleteMapping("/dependencias/{id}")
    @Operation(summary = "Eliminar una dependencia")
    public ResponseEntity<Void> eliminarDependencia(@PathVariable Integer id) {
        catalogoService.eliminarDependencia(id);
        return ResponseEntity.noContent().build();
    }

    // FACULTADES
    @PostMapping("/facultades")
    @Operation(summary = "Crear una nueva facultad/escuela/instituto")
    public ResponseEntity<MaestroDTO> crearFacultad(@Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.crearFacultad(request));
    }

    @PutMapping("/facultades/{id}")
    @Operation(summary = "Actualizar una facultad existente")
    public ResponseEntity<MaestroDTO> actualizarFacultad(
            @PathVariable Integer id, 
            @Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.ok(catalogoService.actualizarFacultad(id, request));
    }

    @DeleteMapping("/facultades/{id}")
    @Operation(summary = "Eliminar una facultad")
    public ResponseEntity<Void> eliminarFacultad(@PathVariable Integer id) {
        catalogoService.eliminarFacultad(id);
        return ResponseEntity.noContent().build();
    }

    // TIPOS DE IDENTIFICACIÓN
    @PostMapping("/tipos-identificacion")
    @Operation(summary = "Crear un nuevo tipo de identificación")
    public ResponseEntity<MaestroDTO> crearTipoIdentificacion(@Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.crearTipoIdentificacion(request));
    }

    @PutMapping("/tipos-identificacion/{id}")
    @Operation(summary = "Actualizar un tipo de identificación existente")
    public ResponseEntity<MaestroDTO> actualizarTipoIdentificacion(
            @PathVariable Integer id, 
            @Valid @RequestBody MaestroRequest request) {
        return ResponseEntity.ok(catalogoService.actualizarTipoIdentificacion(id, request));
    }

    @DeleteMapping("/tipos-identificacion/{id}")
    @Operation(summary = "Eliminar un tipo de identificación")
    public ResponseEntity<Void> eliminarTipoIdentificacion(@PathVariable Integer id) {
        catalogoService.eliminarTipoIdentificacion(id);
        return ResponseEntity.noContent().build();
    }
}
