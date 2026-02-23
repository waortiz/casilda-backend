package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Caso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar entidades Caso
 */
@Repository
public interface CasoRepository extends JpaRepository<Caso, Long> {
    
    /**
     * Busca un caso por su código único
     */
    Optional<Caso> findByCodigo(String codigo);
    
    /**
     * Verifica si existe un caso con el código dado
     */
    boolean existsByCodigo(String codigo);
    
    /**
     * Cuenta los casos creados en un año específico
     * Usado para generar códigos secuenciales por año (ACO-2026-NNNN)
     */
    @Query("SELECT COUNT(c) FROM Caso c WHERE YEAR(c.fechaCreacion) = ?1")
    long countByYear(int year);
}
