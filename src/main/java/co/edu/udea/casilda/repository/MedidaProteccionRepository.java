package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.MedidaProteccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedidaProteccionRepository extends JpaRepository<MedidaProteccion, Long> {
    List<MedidaProteccion> findByAtencionId(Long atencionId);
    void deleteByAtencionId(Long atencionId);
}
