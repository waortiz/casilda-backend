package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RemisionAtencionRequest {
    private Integer idTipoRemision;
    private String cual;
    private LocalDateTime fecha;
}
