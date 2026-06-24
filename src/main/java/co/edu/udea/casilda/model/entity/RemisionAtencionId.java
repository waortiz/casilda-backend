package co.edu.udea.casilda.model.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemisionAtencionId implements Serializable {
    private Long idAtencion;
    private Integer idTipoRemision;
}
