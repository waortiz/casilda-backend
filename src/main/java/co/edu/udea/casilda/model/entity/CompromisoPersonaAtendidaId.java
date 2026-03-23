package co.edu.udea.casilda.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompromisoPersonaAtendidaId implements Serializable {
    private Long idatencion;
    private Integer idtipocompromiso;
}
