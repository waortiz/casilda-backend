package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.MedioSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedioSolicitudRepository extends JpaRepository<MedioSolicitud, Integer> {
}
