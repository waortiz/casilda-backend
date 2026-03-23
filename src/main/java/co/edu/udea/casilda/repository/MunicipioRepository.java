package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

	/**
	 * Lista los municipios asociados a un departamento.
	 */
	List<Municipio> findByDepartamentoIdOrderByNombreAsc(Integer departamentoId);

	/**
	 * Lista los municipios asociados al codigo de un departamento.
	 */
	List<Municipio> findByDepartamentoCodigoOrderByNombreAsc(String codigoDepartamento);
}
