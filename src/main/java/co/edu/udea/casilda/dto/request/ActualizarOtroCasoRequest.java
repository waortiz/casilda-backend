package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * DTO para actualizar un caso existente desde "Otros Casos" dentro del registro de atención completa.
 */
@Data
public class ActualizarOtroCasoRequest {

    @NotNull
    private Long idCaso;

    @Valid
    @NotNull
    private CasoAtencionRequest caso;

    @Valid
    private List<HechoRequest> hechos;
}
