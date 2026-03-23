package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Apreciacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar entidades Apreciacion
 */
@Repository
public interface ApreciacionRepository extends JpaRepository<Apreciacion, Integer> {
}
