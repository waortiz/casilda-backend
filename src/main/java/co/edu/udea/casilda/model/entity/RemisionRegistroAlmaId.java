package co.edu.udea.casilda.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemisionRegistroAlmaId implements Serializable {
    private Long idregistrolinealma;
    private Integer idtiporemision;
}
