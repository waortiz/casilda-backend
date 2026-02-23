package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Remision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades Remision
 */
@Repository
public interface RemisionRepository extends JpaRepository<Remision, Long> {
}
