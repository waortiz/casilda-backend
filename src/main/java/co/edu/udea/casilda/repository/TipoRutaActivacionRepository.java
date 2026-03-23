package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoRutaActivacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades TipoRutaActivacion
 */
@Repository
public interface TipoRutaActivacionRepository extends JpaRepository<TipoRutaActivacion, Integer> {
}
