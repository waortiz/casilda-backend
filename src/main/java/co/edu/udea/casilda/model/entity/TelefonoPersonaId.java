package co.edu.udea.casilda.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoPersonaId implements Serializable {
    private Long idpersona;
    private Integer idtipo;
}
