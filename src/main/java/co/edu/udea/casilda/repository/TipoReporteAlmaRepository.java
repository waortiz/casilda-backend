package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoReporteAlma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoReporteAlmaRepository extends JpaRepository<TipoReporteAlma, Integer> {
}
