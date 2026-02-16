package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactoTelefonicoRequest {
    @NotBlank
    private String fecha;

    @NotBlank
    private String hora;

    @NotBlank
    private String jornada;

    @NotBlank
    private String resultado;

    @NotBlank
    private String observacion;
}
