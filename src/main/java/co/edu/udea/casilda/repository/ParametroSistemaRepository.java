package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ParametroSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar parámetros configurables del sistema
 */
@Repository
public interface ParametroSistemaRepository extends JpaRepository<ParametroSistema, Integer> {

    /**
     * Busca un parámetro por su clave
     */
    Optional<ParametroSistema> findByClave(String clave);
}
