package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemisionRegistroAlmaRequest {

    @NotNull
    private Integer idTipoRemision;

    private String cual;

    @NotNull
    private LocalDateTime fecha;
}
