package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO con la información de persona encontrada por documento.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaSearchResponse {

    private Long id;

    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;

    private Integer tipoDocumentoId;
    private String numeroDocumento;
    private String fechaNacimiento;

    private List<CorreoBusquedaResponse> correos;
    private List<TelefonoBusquedaResponse> telefonos;

    private Integer sexoId;
    private Integer ciudadNacimientoId;
    private Integer departamentoNacimientoId;

    private List<DiscapacidadPersonaResponse> discapacidades;
}
