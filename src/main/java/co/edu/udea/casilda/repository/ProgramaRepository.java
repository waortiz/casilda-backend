package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
    List<Programa> findByIdunidadacademicaAndAplicapregradoTrue(Integer idunidadacademica);
    List<Programa> findByIdunidadacademicaAndAplicaposgradoTrue(Integer idunidadacademica);
}

