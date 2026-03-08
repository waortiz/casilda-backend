package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades Asignacion
 */
@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
}
