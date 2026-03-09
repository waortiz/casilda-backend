package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar entidades Cita
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Lista las citas de una solicitud de atención ordenadas por fecha descendente
     */
    List<Cita> findBySolicitudAtencionIdOrderByFechaDesc(Long solicitudId);
}
