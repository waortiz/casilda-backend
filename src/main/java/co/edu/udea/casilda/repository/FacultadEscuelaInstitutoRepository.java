package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.FacultadEscuelaInstituto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultadEscuelaInstitutoRepository extends JpaRepository<FacultadEscuelaInstituto, Integer> {
}
