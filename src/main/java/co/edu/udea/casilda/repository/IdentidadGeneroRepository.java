package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.IdentidadGenero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentidadGeneroRepository extends JpaRepository<IdentidadGenero, Integer> {
}
