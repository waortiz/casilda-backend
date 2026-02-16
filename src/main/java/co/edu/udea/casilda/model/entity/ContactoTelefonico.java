package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad ContactoTelefonico - Registro de intentos de contacto telefónico.
 */
@Entity
@Table(name = "contactotelefonico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactoTelefonico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsolicitudatencion", nullable = false)
    private SolicitudAtencion solicitudAtencion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idjornada", nullable = false)
    private Jornada jornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idresultado", nullable = false)
    private ResultadoContactoTelefonico resultado;
}
