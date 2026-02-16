package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.OrientacionSexual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrientacionSexualRepository extends JpaRepository<OrientacionSexual, Integer> {
}
