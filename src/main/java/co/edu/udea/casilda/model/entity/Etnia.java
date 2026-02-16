package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etnia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etnia {
    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nombre;
}
