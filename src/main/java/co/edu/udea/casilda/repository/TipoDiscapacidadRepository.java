package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades TipoDiscapacidad
 */
@Repository
public interface TipoDiscapacidadRepository extends JpaRepository<TipoDiscapacidad, Integer> {
}
