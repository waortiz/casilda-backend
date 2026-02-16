package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RepartoRequest {
    @NotBlank
    private String codigoCaso;

    @NotBlank
    private String tipoAsignacion;

    @NotBlank
    private String servicio;

    @NotBlank
    private String asignadoA;

    @NotBlank
    private String observaciones;
}
