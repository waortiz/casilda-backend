package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO de entrada para datos de agresor/victima asociados a un caso.
 */
@Data
public class AgresorVictimaRequest {

    @NotBlank
    private String primerNombre;

    private String segundoNombre;

    @NotBlank
    private String primerApellido;

    private String segundoApellido;

    @NotNull
    private Integer idVinculoUniversidad;

    @NotNull
    private Integer idVinculoVictima;
}
