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
 * Relaciona a la persona víctima, características del caso y modalidades de
 * violencia.
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
    @JoinColumn(name = "idsolicitud", nullable = false)
    private SolicitudAtencion solicitudAtencion;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idorientacionsexual")
    private OrientacionSexual orientacionSexual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ididentidadgenero", nullable = false)
    private IdentidadGenero identidadGenero;

    @Column(name = "hacecuantooccurrio")
    private Integer hacecuantooccurrio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtiempoocurridounidad")
    private TiempoOcurridoUnidad tiempoOcurridoUnidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idformaocurrencia")
    private FormaOcurrencia formaOcurrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlugarocurrencia")
    private LugarOcurrencia lugarOcurrencia;

    @Column(name = "violenciabasadagenero")
    private Boolean violenciaBasadaGenero;

    @Column(name = "hechoviolenciaocurrioactividadesmisionales")
    private Boolean hechoViolenciaOcurrioActividadesMisionales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idactivadmisional")
    private ActividadMisional actividadMisional;

    @Column(name = "tipoviolenciapsicologica")
    private Boolean tipoViolenciaPsicologica;

    @Column(name = "tipoviolenciafisica")
    private Boolean tipoViolenciaFisica;

    @Column(name = "tipoviolenciasexual")
    private Boolean tipoViolenciaSexual;

    @Column(name = "tipoviolenciainstitucional")
    private Boolean tipoViolenciaInstitucional;

    @Column(name = "tipoviolenciaeconomicapatrimonial")
    private Boolean tipoViolenciaEconomicaPatrimonial;

    @Column(name = "tipoviolenciasexualinformatica")
    private Boolean tipoViolenciaSexualInformatica;

    @Column(name = "tipoviolenciaporprejuicio")
    private Boolean tipoViolenciaPorPrejuicio;

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ModalidadViolenciaCaso> modalidadesViolencia = new ArrayList<>();

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ModalidadViolenciaSexualCaso> modalidadesViolenciaSexual = new ArrayList<>();

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProgramaCaso> programas = new ArrayList<>();

    @OneToMany(mappedBy = "caso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PresuntoAgresor> presuntosAgresores = new ArrayList<>();

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
