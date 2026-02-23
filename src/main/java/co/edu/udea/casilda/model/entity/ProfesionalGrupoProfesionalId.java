package co.edu.udea.casilda.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesionalGrupoProfesionalId implements Serializable {
    private Integer idgrupoprofesional;
    private Long idprofesional;
}
