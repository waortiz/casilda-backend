package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.UnidadAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadAcademicaRepository extends JpaRepository<UnidadAcademica, Integer> {
}
