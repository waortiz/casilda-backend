package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ModalidadViolenciaSexual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadViolenciaSexualRepository extends JpaRepository<ModalidadViolenciaSexual, Integer> {
}
