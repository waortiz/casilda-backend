package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * DTO con los datos del caso actualizados durante el registro de atencion.
 */
@Data
public class CasoAtencionRequest {

    @NotNull
    private Integer idDependencia;

    @NotNull
    private Integer idCampus;

    @NotNull
    private Integer idFacultad;

    @NotNull
    private Integer idVinculoUniversidad;

    private Integer idSubVinculoUniversidad;

    @NotNull
    private Integer idPrograma;

    @NotNull
    private Integer idIdentidadGenero;

    @NotNull
    private Integer idOrientacionSexual;

    @NotBlank
    private String tiempoOcurrido;

    @NotNull
    private Integer idFormaOcurrencia;

    @NotNull
    private Integer idLugarOcurrencia;

    @NotNull
    private Boolean violenciaGenero;

    @NotNull
    private Boolean violenciaMisional;

    private Integer idActividadMisional;

    private List<Integer> modalidadesViolenciaPsicologica;
    private List<Integer> modalidadesViolenciaFisica;
    private List<Integer> modalidadesViolenciaSexual;
    private List<Integer> modalidadesViolenciaInstitucional;
    private List<Integer> modalidadesViolenciaEconomica;
    private List<Integer> modalidadesViolenciaInformatica;
    private List<Integer> modalidadesViolenciaPrejuicio;

    @Valid
    @NotNull
    private AgresorVictimaRequest agresorVictima;

    private String direccionLugar;
}