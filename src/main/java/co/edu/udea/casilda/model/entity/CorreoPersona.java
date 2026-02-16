package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "correopersona",
    uniqueConstraints = @UniqueConstraint(columnNames = {"idpersona", "idtipo"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorreoPersona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersona", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipo", nullable = false)
    private TipoCorreo tipoCorreo;

    @Column(nullable = false, length = 200)
    private String correo;

    @Column(nullable = false)
    private String descripcion;
}
