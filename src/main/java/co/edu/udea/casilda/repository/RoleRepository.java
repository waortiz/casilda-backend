package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Rol, Integer> {
}
