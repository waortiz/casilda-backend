package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * DTO con los datos específicos del caso durante el registro de atención.
 * Contiene únicamente los campos que pertenecen a la entidad Caso.
 */
@Data
public class CasoAtencionRequest {

    private Integer idOrientacionSexual;

    private Integer idIdentidadGenero;

    private Integer tiempoOcurridoValor;

    private Integer idTiempoOcurridoUnidad;

    private Integer idFormaOcurrencia;

    private Integer idLugarOcurrencia;

    private Boolean violenciaGenero;

    private Boolean violenciaMisional;

    private Integer idActividadMisional;

    private Integer idPrograma;

    private List<Integer> modalidadesViolenciaPsicologica;
    private List<Integer> modalidadesViolenciaFisica;
    private List<Integer> modalidadesViolenciaSexual;
    private List<Integer> modalidadesViolenciaInstitucional;
    private List<Integer> modalidadesViolenciaEconomica;
    private List<Integer> modalidadesViolenciaInformatica;
    private List<Integer> modalidadesViolenciaPrejuicio;

    @Valid
    private List<AgresorVictimaRequest> agresores;
}