package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompromisoPersonaAtendidaRequest {

    @NotNull
    private Long idatencion;

    @NotNull
    private LocalDateTime fechacompromiso;

    @NotNull
    private Integer idtipocompromiso;
}
