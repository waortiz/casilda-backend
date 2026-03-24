package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ArchivoSeguimientoAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades ArchivoSeguimientoAtencion
 */
@Repository
public interface ArchivoSeguimientoAtencionRepository extends JpaRepository<ArchivoSeguimientoAtencion, Long> {
}
