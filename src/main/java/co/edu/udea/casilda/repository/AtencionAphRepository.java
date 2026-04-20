package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.AtencionAph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtencionAphRepository extends JpaRepository<AtencionAph, Long> {
    Optional<AtencionAph> findByRegistroLineaAlmaId(Long registroLineaAlmaId);
}
