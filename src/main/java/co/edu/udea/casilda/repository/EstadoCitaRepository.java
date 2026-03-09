package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades EstadoCita
 */
@Repository
public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {
}
