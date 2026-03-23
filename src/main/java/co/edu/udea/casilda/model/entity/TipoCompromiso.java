package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipocompromiso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoCompromiso {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombre;
}
