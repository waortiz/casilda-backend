package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.PresuntoAgresor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PresuntoAgresorRepository extends JpaRepository<PresuntoAgresor, Long> {
    List<PresuntoAgresor> findByCasoId(Long casoId);
    void deleteByCasoId(Long casoId);
}
