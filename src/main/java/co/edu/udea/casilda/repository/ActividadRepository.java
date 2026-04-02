package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar Actividad.
 */
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

	/**
	 * Obtiene actividades por acción ordenadas por nombre.
	 */
	List<Actividad> findByAccionIdOrderByNombreAsc(Integer accionId);
}
