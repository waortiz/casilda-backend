package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexoRepository extends JpaRepository<Sexo, Integer> {
}
