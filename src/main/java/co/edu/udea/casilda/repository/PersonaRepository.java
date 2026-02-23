package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar entidades Persona
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    
    /**
     * Busca una persona por su número de documento
     */
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
    
    /**
     * Verifica si existe una persona con el número de documento dado
     */
    boolean existsByNumeroDocumento(String numeroDocumento);
}
