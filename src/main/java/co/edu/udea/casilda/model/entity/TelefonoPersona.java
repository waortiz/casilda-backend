package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "telefonopersona",
    uniqueConstraints = @UniqueConstraint(columnNames = {"idpersona", "idtipo"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoPersona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersona", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipo", nullable = false)
    private TipoTelefono tipoTelefono;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false)
    private String descripcion;
}
