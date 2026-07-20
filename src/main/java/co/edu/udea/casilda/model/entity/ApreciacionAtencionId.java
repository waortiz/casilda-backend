package co.edu.udea.casilda.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApreciacionAtencionId implements Serializable {
    @Column(name = "idatencion")
    private Long idAtencion;

    @Column(name = "idtipoapreciacion")
    private Integer idTipoApreciacion;
}
