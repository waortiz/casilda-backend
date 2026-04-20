package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.CanalContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanalContactoRepository extends JpaRepository<CanalContacto, Integer> {
}
