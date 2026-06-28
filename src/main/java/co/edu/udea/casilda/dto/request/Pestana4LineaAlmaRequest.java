package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class Pestana4LineaAlmaRequest {
    @NotNull(message = "El ID del registro es obligatorio")
    private Long id;

    @NotNull(message = "La lista de contactos es obligatoria")
    @Valid
    private List<ContactoLineaAlmaRequest> contactos;
}
