package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.GrupoProfesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades GrupoProfesional
 */
@Repository
public interface GrupoProfesionalRepository extends JpaRepository<GrupoProfesional, Integer> {
}
