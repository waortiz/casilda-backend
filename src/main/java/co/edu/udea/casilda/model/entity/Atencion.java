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

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsolicitudatencion", nullable = false)
    private SolicitudAtencion solicitudAtencion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idregimen", nullable = false)
    private Regimen regimen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ideps", nullable = false)
    private EPS eps;

    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private java.util.List<ArchivoConsentimiento> archivos = new java.util.ArrayList<>();
}
