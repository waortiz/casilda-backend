package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TiempoOcurridoUnidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiempoOcurridoUnidadRepository extends JpaRepository<TiempoOcurridoUnidad, Integer> {
}
