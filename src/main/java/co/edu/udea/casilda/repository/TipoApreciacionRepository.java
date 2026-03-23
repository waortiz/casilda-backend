package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.TipoApreciacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para gestionar entidades TipoApreciacion
 */
@Repository
public interface TipoApreciacionRepository extends JpaRepository<TipoApreciacion, Integer> {

    /**
     * Obtiene los tipos de apreciación por el ID de apreciación
     */
    List<TipoApreciacion> findByApreciacionId(Integer apreciacionId);
}
