package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.AmbitoAph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbitoAphRepository extends JpaRepository<AmbitoAph, Integer> {
}
