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
    private String resultado;

    @NotBlank
    private String observacion;

    /** Fecha de la cita a crear (yyyy-MM-dd). Solo requerido cuando aplica asignación de cita. */
    private String fechaCita;

    /** Hora de la cita a crear (HH:mm). Solo requerido cuando aplica asignación de cita. */
    private String horaCita;
}
