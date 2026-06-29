package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DiscapacidadPersonaRequest {
    @NotNull(message = "El ID del subtipo de discapacidad es obligatorio")
    private Integer idSubTipoDiscapacidad;

    private String descripcion;
}
