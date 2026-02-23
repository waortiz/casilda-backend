package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.SolicitudAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar entidades SolicitudAtencion
 */
@Repository
public interface SolicitudAtencionRepository extends JpaRepository<SolicitudAtencion, Long> {
    
    /**
     * Busca todas las solicitudes de atención de un caso específico
     */
    List<SolicitudAtencion> findByCasoId(Long casoId);
}
