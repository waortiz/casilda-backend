package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.RutaAtencion;
import co.edu.udea.casilda.model.entity.RutaAtencionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RutaAtencionRepository extends JpaRepository<RutaAtencion, RutaAtencionId> {
    List<RutaAtencion> findByIdAtencion(Long idAtencion);
    void deleteByIdAtencion(Long idAtencion);
}
