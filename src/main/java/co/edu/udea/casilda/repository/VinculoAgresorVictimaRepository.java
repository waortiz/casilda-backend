package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.VinculoAgresorVictima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinculoAgresorVictimaRepository extends JpaRepository<VinculoAgresorVictima, Integer> {
}
