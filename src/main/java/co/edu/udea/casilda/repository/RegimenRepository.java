package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Regimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegimenRepository extends JpaRepository<Regimen, Integer> {
}
