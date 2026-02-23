package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefonopersona")
@IdClass(TelefonoPersonaId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoPersona {
    
    @Id
    @Column(name = "idpersona", nullable = false)
    private Long idpersona;

    @Id
    @Column(name = "idtipo", nullable = false)
    private Integer idtipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersona", nullable = false, insertable = false, updatable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipo", nullable = false, insertable = false, updatable = false)
    private TipoTelefono tipoTelefono;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false)
    private String descripcion;
}
