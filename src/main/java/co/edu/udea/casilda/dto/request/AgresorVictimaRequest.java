package co.edu.udea.casilda.dto.request;

import lombok.Data;

/**
 * DTO de entrada para datos de agresor/victima asociados a un caso.
 */
@Data
public class AgresorVictimaRequest {

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private Integer idVinculoUniversidad;

    private Integer idVinculoVictima;
}
