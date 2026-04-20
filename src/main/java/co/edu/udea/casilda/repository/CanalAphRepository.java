package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.CanalAph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanalAphRepository extends JpaRepository<CanalAph, Integer> {
}
