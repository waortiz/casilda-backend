package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaVBGRequest {
    private Long idCaso;
    private List<Integer> modalidadesViolenciaPsicologica;
    private List<Integer> modalidadesViolenciaFisica;
    private List<Integer> modalidadesViolenciaSexual;
    private List<Integer> modalidadesViolenciaInstitucional;
    private List<Integer> modalidadesViolenciaEconomica;
    private List<Integer> modalidadesViolenciaInformatica;
    private List<Integer> modalidadesViolenciaPrejuicio;
}
