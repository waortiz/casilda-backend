package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaestroRequest {
    // El id es generado automáticamente por la base de datos
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // Campo opcional para maestros que requieren código
    private String codigo;
}
