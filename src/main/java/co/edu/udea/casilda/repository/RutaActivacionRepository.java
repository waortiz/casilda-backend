package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.RutaActivacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades RutaActivacion
 */
@Repository
public interface RutaActivacionRepository extends JpaRepository<RutaActivacion, Integer> {
}
