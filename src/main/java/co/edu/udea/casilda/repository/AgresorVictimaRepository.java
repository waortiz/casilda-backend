package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.AgresorVictima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar entidades AgresorVictima.
 */
@Repository
public interface AgresorVictimaRepository extends JpaRepository<AgresorVictima, Long> {

    /**
     * Busca el registro de agresor/victima asociado a un caso.
     */
    Optional<AgresorVictima> findByCasoId(Long casoId);
}
