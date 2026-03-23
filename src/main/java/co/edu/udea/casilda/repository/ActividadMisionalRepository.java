package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ActividadMisional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadMisionalRepository extends JpaRepository<ActividadMisional, Integer> {
}
