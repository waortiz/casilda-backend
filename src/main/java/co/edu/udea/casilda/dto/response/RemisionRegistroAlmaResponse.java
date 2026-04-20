package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemisionRegistroAlmaResponse {
    private Integer idTipoRemision;
    private String tipoRemision;
    private String cual;
    private LocalDateTime fecha;
}
