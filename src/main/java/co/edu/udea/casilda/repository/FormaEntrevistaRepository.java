package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.FormaEntrevista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaEntrevistaRepository extends JpaRepository<FormaEntrevista, Integer> {
}
