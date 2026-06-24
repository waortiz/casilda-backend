package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ResponsableMedidaProteccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsableMedidaProteccionRepository extends JpaRepository<ResponsableMedidaProteccion, Integer> {
}
