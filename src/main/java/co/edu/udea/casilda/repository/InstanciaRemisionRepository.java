package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.InstanciaRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstanciaRemisionRepository extends JpaRepository<InstanciaRemision, Integer> {
    List<InstanciaRemision> findByTipoRemisionId(Integer tipoRemisionId);
}
