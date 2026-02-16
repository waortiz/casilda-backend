package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jornada")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jornada {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombre;
}
