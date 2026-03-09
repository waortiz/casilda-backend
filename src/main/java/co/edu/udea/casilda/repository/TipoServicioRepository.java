package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades TipoServicio
 */
@Repository
public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer> {
}
