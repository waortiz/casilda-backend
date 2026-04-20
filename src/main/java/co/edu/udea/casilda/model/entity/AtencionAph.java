package co.edu.udea.casilda.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "atencionaph")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtencionAph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idregistrolinealma", nullable = false)
    private RegistroLineaAlma registroLineaAlma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcanalaph", nullable = false)
    private CanalAph canalAph;

    @Column(name = "fechahora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idconvenioaph", nullable = false)
    private ConvenioAph convenioAph;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idambitoaph", nullable = false)
    private AmbitoAph ambitoAph;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprotocoloaph", nullable = false)
    private ProtocoloAph protocoloAph;

    @Column(name = "practicotriage", nullable = false)
    private boolean practicoTriage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idresultadotriage")
    private ResultadoTriage resultadoTriage;

    @Column(name = "notaomotivotriage")
    private String notaOMotivoTriage;

    @Column(name = "aceptapsicologia", nullable = false)
    private boolean aceptaPsicologia;

    @Column(name = "requiereremision", nullable = false)
    private boolean requiereRemision;

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
