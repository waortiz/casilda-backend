package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO response para exponer hechos asociados a un caso en la pestaña Otros Casos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HechoOtroCasoResponse {
    private String fecha;
    private String lugar;
    private String descripcion;
}
