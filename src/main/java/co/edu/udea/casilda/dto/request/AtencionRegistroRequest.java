package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AtencionRegistroRequest {
    @NotBlank
    private String tipoSolicitud;

    @NotBlank
    private String personaAtiende;

    @NotBlank
    private String personaRegistra;

    @NotBlank
    private String tipoServicio;

    @NotBlank
    private String quienRemite;

    @NotBlank
    private String formaEntrevista;

    @NotBlank
    private String tipoDocumento;

    @NotBlank
    private String documento;

    @NotBlank
    private String primerNombre;

    @NotBlank
    private String primerApellido;

    @NotBlank
    private String sexo;

    @NotBlank
    private String etnia;

    @NotBlank
    private String eps;

    @NotBlank
    private String regimenSalud;

    @NotBlank
    private String campus;

    @NotBlank
    private String facultad;

    @NotBlank
    private String vinculo;

    @NotBlank
    private String tipoViolencia;

    @NotBlank
    private String subcategoriaViolencia;

    @NotBlank
    private String hechos;
}
