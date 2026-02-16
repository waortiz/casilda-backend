package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSolicitudRepository extends JpaRepository<TipoSolicitud, Integer> {
}
