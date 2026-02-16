package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.EPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EPSRepository extends JpaRepository<EPS, Integer> {
}
