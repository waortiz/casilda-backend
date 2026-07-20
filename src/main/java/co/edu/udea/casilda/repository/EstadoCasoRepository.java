package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.EstadoCaso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar estados de caso.
 */
@Repository
public interface EstadoCasoRepository extends JpaRepository<EstadoCaso, Integer> {

    /**
     * Busca un estado de caso por su nombre.
     */
    Optional<EstadoCaso> findByNombre(String nombre);
}
