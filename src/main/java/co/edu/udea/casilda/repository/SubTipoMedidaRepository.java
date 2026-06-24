package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.SubTipoMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubTipoMedidaRepository extends JpaRepository<SubTipoMedida, Integer> {
    List<SubTipoMedida> findByTipoMedidaId(Integer tipoMedidaId);
}
