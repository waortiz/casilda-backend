package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para gestionar Atencion
 */
@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    Optional<Atencion> findByCitaId(Long citaId);
}
