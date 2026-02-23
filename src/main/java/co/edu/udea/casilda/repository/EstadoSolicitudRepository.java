package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar estados de solicitud
 */
@Repository
public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitud, Integer> {
    
    /**
     * Busca un estado de solicitud por su nombre
     */
    Optional<EstadoSolicitud> findByNombre(String nombre);
}
