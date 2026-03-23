package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades TipoRemision
 */
@Repository
public interface TipoRemisionRepository extends JpaRepository<TipoRemision, Integer> {
}
