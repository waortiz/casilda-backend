package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompromisoProfesionalRequest {

    @NotNull
    private Long idatencion;

    @NotNull
    private LocalDateTime fechacompromiso;

    @NotNull
    private Integer idgrupoprofesional;

    @NotNull
    private Integer idtipocompromiso;
}
