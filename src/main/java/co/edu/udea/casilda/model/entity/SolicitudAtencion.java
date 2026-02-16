package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad SolicitudAtencion - Solicitud de atención registrada en el sistema.
 * Relaciona un caso con la solicitud de servicios.
 */
@Entity
@Table(name = "solicitudatencion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcaso", nullable = false)
    private Caso caso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idremision")
    private Remision remision;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtiposolicitud", nullable = false)
    private TipoSolicitud tipoSolicitud;

    @OneToMany(mappedBy = "solicitudAtencion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContactoTelefonico> contactosTelefonicos = new ArrayList<>();

    @OneToMany(mappedBy = "solicitudAtencion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Asignacion> asignaciones = new ArrayList<>();

    @OneToMany(mappedBy = "solicitudAtencion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Atencion> atenciones = new ArrayList<>();
}
