package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoTelefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTelefonoRepository extends JpaRepository<TipoTelefono, Integer> {
}
