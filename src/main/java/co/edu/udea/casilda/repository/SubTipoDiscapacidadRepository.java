package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.SubTipoDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para gestionar entidades SubTipoDiscapacidad
 */
@Repository
public interface SubTipoDiscapacidadRepository extends JpaRepository<SubTipoDiscapacidad, Integer> {

    /**
     * Obtiene los subtipos de discapacidad por el ID del tipo
     */
    List<SubTipoDiscapacidad> findByTipoId(Integer tipoId);
}
