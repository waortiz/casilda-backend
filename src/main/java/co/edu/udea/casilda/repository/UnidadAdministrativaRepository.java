package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.UnidadAdministrativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadAdministrativaRepository extends JpaRepository<UnidadAdministrativa, Integer> {
}
