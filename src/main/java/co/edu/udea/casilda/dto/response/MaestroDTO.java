package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO genérico para respuestas de maestros.
 * Utilizado para retornar datos simples de tablas de maestros.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaestroDTO {
    private Long id;
    private String codigo;      // Opcional - para maestros que tienen código
    private String nombre;      // Obligatorio - todos los maestros tienen nombre
}
