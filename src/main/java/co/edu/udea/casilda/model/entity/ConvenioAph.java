package co.edu.udea.casilda.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "convenioaph")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioAph {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombre;
}
