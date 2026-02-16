package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer> {
}
