package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ApreciacionAtencion;
import co.edu.udea.casilda.model.entity.ApreciacionAtencionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApreciacionAtencionRepository extends JpaRepository<ApreciacionAtencion, ApreciacionAtencionId> {
    List<ApreciacionAtencion> findByIdIdAtencion(Long idAtencion);
    void deleteByIdIdAtencion(Long idAtencion);
}
