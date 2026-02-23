package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "correopersona")
@IdClass(CorreoPersonaId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorreoPersona {
    
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
    private TipoCorreo tipoCorreo;

    @Column(nullable = false, length = 200)
    private String correo;

    @Column(nullable = false)
    private String descripcion;
}
