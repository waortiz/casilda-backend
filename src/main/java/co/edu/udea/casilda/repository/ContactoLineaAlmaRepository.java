package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ContactoLineaAlma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoLineaAlmaRepository extends JpaRepository<ContactoLineaAlma, Long> {
    List<ContactoLineaAlma> findByRegistroLineaAlmaIdOrderByFechaCreacionDesc(Long idRegistroLineaAlma);
}
