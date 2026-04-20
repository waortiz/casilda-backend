package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ConvenioAph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenioAphRepository extends JpaRepository<ConvenioAph, Integer> {
}
