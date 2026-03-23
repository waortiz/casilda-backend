package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar TipoSeguimiento.
 */
@Repository
public interface TipoSeguimientoRepository extends JpaRepository<TipoSeguimiento, Integer> {
}
