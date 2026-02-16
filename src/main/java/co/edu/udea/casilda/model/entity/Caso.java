package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Caso - Representa un caso de violencia basada en género.
 * Relaciona a la persona víctima, características del caso y modalidades de violencia.
 */
@Entity
@Table(name = "caso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersona", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idorientacionsexual", nullable = false)
    private OrientacionSexual orientacionSexual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ididentidadgenero", nullable = false)
    private IdentidadGenero identidadGenero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddependencia", nullable = false)
    private Dependencia dependencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfacultad", nullable = false)
    private FacultadEscuelaInstituto facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcampus", nullable = false)
    private Campus campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvinvuloagresorvictima", nullable = false)
    private VinculoAgresorVictima vinculoAgresorVictima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvinculoudea", nullable = false)
    private VinculoUdeA vinculoUdeA;

    @Column(name = "circunstancias", length = 1000)
    private String circunstancias;

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ModalidadViolenciaCaso> modalidadesViolencia = new ArrayList<>();

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ModalidadViolenciaSexualCaso> modalidadesViolenciaSexual = new ArrayList<>();

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProgramaCaso> programas = new ArrayList<>();
}
