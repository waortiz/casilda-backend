package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad Remision - Remisión de personas a servicios o dependencias.
 */
@Entity
@Table(name = "remision")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idremitente", nullable = false)
    private Persona remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcargo", nullable = false)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddependencia", nullable = false)
    private Dependencia dependencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfacultad", nullable = false)
    private FacultadEscuelaInstituto facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcampus", nullable = false)
    private Campus campus;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
}
