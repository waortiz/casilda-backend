package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.GrupoAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoAtencionRepository extends JpaRepository<GrupoAtencion, Integer> {
}
