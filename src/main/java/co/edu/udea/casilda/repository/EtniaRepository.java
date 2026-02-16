package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Etnia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtniaRepository extends JpaRepository<Etnia, Integer> {
}
