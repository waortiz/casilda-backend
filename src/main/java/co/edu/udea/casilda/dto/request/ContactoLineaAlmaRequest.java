package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactoLineaAlmaRequest {

    private LocalDateTime fecha;

    @NotNull
    private Integer idResultado;
}
