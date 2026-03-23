package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Accion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar Accion.
 */
@Repository
public interface AccionRepository extends JpaRepository<Accion, Integer> {
}
