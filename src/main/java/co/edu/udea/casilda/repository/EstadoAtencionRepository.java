package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.EstadoAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar estados de atencion.
 */
@Repository
public interface EstadoAtencionRepository extends JpaRepository<EstadoAtencion, Integer> {

    /**
     * Busca un estado de atencion por su nombre.
     */
    Optional<EstadoAtencion> findByNombre(String nombre);
}
