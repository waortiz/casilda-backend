package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ActorRemitente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRemitenteRepository extends JpaRepository<ActorRemitente, Integer> {
}
