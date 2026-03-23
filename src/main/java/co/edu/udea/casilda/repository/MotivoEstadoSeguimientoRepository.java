package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.MotivoEstadoSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar MotivoEstadoSeguimiento.
 */
@Repository
public interface MotivoEstadoSeguimientoRepository extends JpaRepository<MotivoEstadoSeguimiento, Integer> {
}
