package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.CompromisoProfesional;
import co.edu.udea.casilda.model.entity.CompromisoProfesionalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar CompromisoProfesional
 */
@Repository
public interface CompromisoProfesionalRepository extends JpaRepository<CompromisoProfesional, CompromisoProfesionalId> {

    /**
     * Obtiene compromisos profesionales por ID de atención
     */
    List<CompromisoProfesional> findByIdatencion(Long idatencion);
}
