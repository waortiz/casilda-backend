package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoAsignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades TipoAsignacion
 */
@Repository
public interface TipoAsignacionRepository extends JpaRepository<TipoAsignacion, Integer> {
}
