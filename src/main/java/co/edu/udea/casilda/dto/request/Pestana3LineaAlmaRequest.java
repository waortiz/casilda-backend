package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Pestana3LineaAlmaRequest {
    @NotNull(message = "El ID del registro es obligatorio")
    private Long id;

    @NotNull(message = "Los datos de la atención APH son obligatorios")
    @Valid
    private AtencionAphRequest atencionAph;
}
