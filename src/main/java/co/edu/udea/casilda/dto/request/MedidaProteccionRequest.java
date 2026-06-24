package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MedidaProteccionRequest {
    private Integer tipoMedidaId;
    private Integer subtipoMedidaId;
    private Integer responsableId;
    private LocalDateTime fechaRegistro;
    private String descripcion;
}
