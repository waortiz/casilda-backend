package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modalidadviolencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadViolencia {
    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nombre;
}
