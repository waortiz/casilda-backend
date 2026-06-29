package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TelefonoPersona;
import co.edu.udea.casilda.model.entity.TelefonoPersonaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar teléfonos de personas
 */
@Repository
public interface TelefonoPersonaRepository extends JpaRepository<TelefonoPersona, TelefonoPersonaId> {
    List<TelefonoPersona> findByIdpersona(Long idpersona);
}
