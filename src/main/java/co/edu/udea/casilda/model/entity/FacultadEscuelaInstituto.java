package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facultadescuelainstituto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultadEscuelaInstituto {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombre;
}
