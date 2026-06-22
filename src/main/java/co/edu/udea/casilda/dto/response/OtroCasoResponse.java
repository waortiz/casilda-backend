package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO response para listar casos adicionales en registro de atencion.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtroCasoResponse {
    private Long idCaso;
    private String id;
    private String tiempoHechos;
    private Integer tiempoOcurridoValor;
    private Integer idTiempoOcurridoUnidad;
    private String tipoViolencia;
    private String subcategoriaViolencia;
    private String descripcion;

    private Integer idOrientacionSexual;
    private Integer idIdentidadGenero;
    private Integer idFormaOcurrencia;
    private Integer idLugarOcurrencia;
    private Boolean violenciaGenero;
    private Boolean violenciaMisional;
    private Integer idActividadMisional;

    private List<Integer> modalidadesViolenciaPsicologica;
    private List<Integer> modalidadesViolenciaFisica;
    private List<Integer> modalidadesViolenciaSexual;
    private List<Integer> modalidadesViolenciaInstitucional;
    private List<Integer> modalidadesViolenciaEconomica;
    private List<Integer> modalidadesViolenciaInformatica;
    private List<Integer> modalidadesViolenciaPrejuicio;

    private String presuntoPrimerNombre;
    private String presuntoSegundoNombre;
    private String presuntoPrimerApellido;
    private String presuntoSegundoApellido;
    private Integer idVinculoUniversidad;
    private Integer idVinculoVictima;

    private List<HechoOtroCasoResponse> hechos;
}
