package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.RemisionAtencion;
import co.edu.udea.casilda.model.entity.RemisionAtencionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RemisionAtencionRepository extends JpaRepository<RemisionAtencion, RemisionAtencionId> {
    List<RemisionAtencion> findByIdAtencion(Long idAtencion);
    void deleteByIdAtencion(Long idAtencion);
}
