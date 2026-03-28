package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Remision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar entidades Remision
 */
@Repository
public interface RemisionRepository extends JpaRepository<Remision, Long> {

	/**
	 * Busca la remisión más reciente de una persona por número de documento.
	 */
	Optional<Remision> findTopByRemitente_NumeroDocumentoOrderByFechaCreacionDesc(String numeroDocumento);

	/**
	 * Busca la remisión más reciente de una persona por tipo y número de documento.
	 */
	Optional<Remision> findTopByRemitente_NumeroDocumentoAndRemitente_TipoIdentificacion_IdOrderByFechaCreacionDesc(
			String numeroDocumento,
			Integer tipoDocumentoId
	);
}
