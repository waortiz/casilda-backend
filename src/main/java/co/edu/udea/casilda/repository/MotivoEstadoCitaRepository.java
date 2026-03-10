package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.MotivoEstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para motivos del estado de la cita
 */
@Repository
public interface MotivoEstadoCitaRepository extends JpaRepository<MotivoEstadoCita, Integer> {
}
