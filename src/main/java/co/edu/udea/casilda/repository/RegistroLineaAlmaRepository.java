package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.RegistroLineaAlma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroLineaAlmaRepository extends JpaRepository<RegistroLineaAlma, Long> {
    List<RegistroLineaAlma> findAllByOrderByFechaCreacionDesc();
}
