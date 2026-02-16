package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Asignacion - Asignación de profesionales a solicitudes de atención.
 */
@Entity
@Table(name = "asignacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsolicitudatencion", nullable = false)
    private SolicitudAtencion solicitudAtencion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profesionalasignacion",
            joinColumns = @JoinColumn(name = "idasignacion"),
            inverseJoinColumns = @JoinColumn(name = "idprofesional")
    )
    private List<Profesional> profesionales = new ArrayList<>();
}
