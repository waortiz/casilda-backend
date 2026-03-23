package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoCompromiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar TipoCompromiso
 */
@Repository
public interface TipoCompromisoRepository extends JpaRepository<TipoCompromiso, Integer> {
}
