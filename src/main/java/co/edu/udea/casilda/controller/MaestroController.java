package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.response.MaestroDTO;
import co.edu.udea.casilda.service.MaestroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador REST para maestros
 * Proporciona endpoints para obtener datos de maestros del sistema
 */
@RestController
@RequestMapping("/maestros")
@RequiredArgsConstructor
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

    @GetMapping("/roles")
    @Operation(summary = "Obtener lista de roles")
    public ResponseEntity<List<MaestroDTO>> obtenerRoles() {
        return ResponseEntity.ok(catalogoService.obtenerRoles());
    }

    @GetMapping("/jornadas")
    @Operation(summary = "Obtener lista de jornadas")
    public ResponseEntity<List<MaestroDTO>> obtenerJornadas() {
        return ResponseEntity.ok(catalogoService.obtenerJornadas());
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
}
