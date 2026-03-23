package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

	/**
	 * Busca un departamento por su codigo.
	 */
	Optional<Departamento> findByCodigo(String codigo);
}
