package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.RemisionRegistroAlma;
import co.edu.udea.casilda.model.entity.RemisionRegistroAlmaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemisionRegistroAlmaRepository extends JpaRepository<RemisionRegistroAlma, RemisionRegistroAlmaId> {
    List<RemisionRegistroAlma> findByIdregistrolinealmaOrderByFechaDesc(Long idRegistroLineaAlma);
}
