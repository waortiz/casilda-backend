package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ResultadoContactoTelefonico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoContactoTelefonicoRepository extends JpaRepository<ResultadoContactoTelefonico, Integer> {
}
