package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modalidadviolenciasexualcaso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadViolenciaSexualCaso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcaso", nullable = false)
    private Caso caso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmodalidadviolencia", nullable = false)
    private ModalidadViolenciaSexual modalidadViolenciaSexual;
}
