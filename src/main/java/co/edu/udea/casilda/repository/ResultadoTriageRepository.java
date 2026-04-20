package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ResultadoTriage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoTriageRepository extends JpaRepository<ResultadoTriage, Integer> {
}
