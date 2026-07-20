package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.CorreoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para gestionar correos de personas
 */
@Repository
public interface CorreoPersonaRepository extends JpaRepository<CorreoPersona, Long> {
    List<CorreoPersona> findByIdpersona(Long idpersona);
}
