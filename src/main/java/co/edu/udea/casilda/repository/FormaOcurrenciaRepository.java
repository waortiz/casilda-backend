package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.FormaOcurrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaOcurrenciaRepository extends JpaRepository<FormaOcurrencia, Integer> {
}
