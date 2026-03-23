package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ModalidadViolencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModalidadViolenciaRepository extends JpaRepository<ModalidadViolencia, Integer> {

	List<ModalidadViolencia> findByTipoViolenciaIdOrderByNombreAsc(Integer tipoViolenciaId);
}
