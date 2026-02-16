package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.VinculoUdeA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinculoUdeARepository extends JpaRepository<VinculoUdeA, Integer> {
}
