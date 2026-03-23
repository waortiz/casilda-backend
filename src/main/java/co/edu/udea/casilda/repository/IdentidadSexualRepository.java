package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.IdentidadSexual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentidadSexualRepository extends JpaRepository<IdentidadSexual, Integer> {
}
