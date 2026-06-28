package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class Pestana5LineaAlmaRequest {
    @NotNull(message = "El ID del registro es obligatorio")
    private Long id;

    @NotNull(message = "La lista de remisiones es obligatoria")
    @Valid
    private List<RemisionRegistroAlmaRequest> remisiones;
}
