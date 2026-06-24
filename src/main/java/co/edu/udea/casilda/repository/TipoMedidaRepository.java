package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMedidaRepository extends JpaRepository<TipoMedida, Integer> {
}
