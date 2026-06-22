package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad Atencion - Registro de una atención brindada a una solicitud.
 */
@Entity
@Table(name = "atencion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idestadoatencion", nullable = false)
    private EstadoAtencion estadoAtencion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcita", nullable = false)
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idetnia")
    private Etnia etnia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idciudadresidencia")
    private Municipio ciudadResidencia;

    @Column(name = "direccionresidencia", length = 500)
    private String direccionResidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprograma")
    private Programa programa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddependencia")
    private Dependencia dependencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfacultad")
    private FacultadEscuelaInstituto facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcampus")
    private Campus campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvinculoudea")
    private VinculoUdeA vinculoUdeA;

    @Column(name = "otrovinculo", length = 255)
    private String otroVinculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtiposervicio", nullable = false)
    private TipoServicio tipoServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlugarentrevista", nullable = false)
    private LugarEntrevista lugarEntrevista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idregimen", nullable = false)
    private Regimen regimen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ideps", nullable = false)
    private EPS eps;

    @Column(name = "logroacuerdo", nullable = false)
    private boolean logroAcuerdo;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuariocreacion")
    private Usuario usuarioCreacion;

    @Column(name = "fechaactualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuarioactualizacion")
    private Usuario usuarioActualizacion;

    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private java.util.List<ArchivoConsentimiento> archivos = new java.util.ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
