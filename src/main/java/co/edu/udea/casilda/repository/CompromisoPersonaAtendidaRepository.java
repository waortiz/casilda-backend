package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.CompromisoPersonaAtendida;
import co.edu.udea.casilda.model.entity.CompromisoPersonaAtendidaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar CompromisoPersonaAtendida
 */
@Repository
public interface CompromisoPersonaAtendidaRepository extends JpaRepository<CompromisoPersonaAtendida, CompromisoPersonaAtendidaId> {

    /**
     * Obtiene compromisos de persona atendida por ID de atención
     */
    List<CompromisoPersonaAtendida> findByIdatencion(Long idatencion);
}
