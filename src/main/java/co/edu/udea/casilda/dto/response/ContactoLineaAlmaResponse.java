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
public class ContactoLineaAlmaResponse {
    private Long id;
    private Long idRegistroLineaAlma;
    private LocalDateTime fecha;
    private Integer idResultado;
    private String resultado;
    private LocalDateTime fechaCreacion;
}
