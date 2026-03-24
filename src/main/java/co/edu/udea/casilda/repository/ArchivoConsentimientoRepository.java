package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ArchivoConsentimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades ArchivoConsentimiento
 */
@Repository
public interface ArchivoConsentimientoRepository extends JpaRepository<ArchivoConsentimiento, Long> {
}
