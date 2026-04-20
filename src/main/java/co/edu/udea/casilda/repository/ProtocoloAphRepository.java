package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ProtocoloAph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocoloAphRepository extends JpaRepository<ProtocoloAph, Integer> {
}
