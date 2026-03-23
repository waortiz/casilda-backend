package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.LugarOcurrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LugarOcurrenciaRepository extends JpaRepository<LugarOcurrencia, Integer> {
}
