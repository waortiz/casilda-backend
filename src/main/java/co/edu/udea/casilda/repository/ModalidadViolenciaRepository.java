package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ModalidadViolencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadViolenciaRepository extends JpaRepository<ModalidadViolencia, Integer> {
}
