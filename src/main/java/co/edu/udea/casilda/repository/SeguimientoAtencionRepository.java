package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.SeguimientoAtencion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar SeguimientoAtencion.
 */
@Repository
public interface SeguimientoAtencionRepository extends JpaRepository<SeguimientoAtencion, Long> {

    /**
     * Obtiene seguimientos por id de atencion.
     */
    List<SeguimientoAtencion> findByAtencionIdOrderByFechaDesc(Long atencionId);
}
