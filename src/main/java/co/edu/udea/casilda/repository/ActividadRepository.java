package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar Actividad.
 */
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
}
