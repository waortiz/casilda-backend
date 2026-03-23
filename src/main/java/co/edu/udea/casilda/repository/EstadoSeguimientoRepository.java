package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.EstadoSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar EstadoSeguimiento.
 */
@Repository
public interface EstadoSeguimientoRepository extends JpaRepository<EstadoSeguimiento, Integer> {
}
