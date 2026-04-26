package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar los hechos asociados a casos.
 */
@Repository
public interface HechoRepository extends JpaRepository<Hecho, Long> {

    /**
     * Lista hechos de un caso ordenados por fecha descendente.
     */
    List<Hecho> findByCasoIdOrderByFechaDesc(Long casoId);

    /**
     * Elimina todos los hechos asociados a un caso.
     */
    void deleteByCasoId(Long casoId);
}
