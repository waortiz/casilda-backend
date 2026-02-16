package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Dependencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependenciaRepository extends JpaRepository<Dependencia, Integer> {
}
