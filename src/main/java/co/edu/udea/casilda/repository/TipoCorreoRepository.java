package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoCorreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCorreoRepository extends JpaRepository<TipoCorreo, Integer> {
}
