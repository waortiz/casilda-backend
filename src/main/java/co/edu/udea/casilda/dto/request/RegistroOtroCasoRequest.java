package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * DTO para registrar un caso adicional desde la seccion "Otros casos".
 */
@Data
public class RegistroOtroCasoRequest {

    @Valid
    @NotNull
    private CasoAtencionRequest caso;

    @Valid
    private List<HechoRequest> hechos;
}
