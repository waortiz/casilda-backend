package co.edu.udea.casilda.repository;

import co.edu.udea.casilda.model.entity.ContactoTelefonico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoTelefonicoRepository extends JpaRepository<ContactoTelefonico, Long> {
    List<ContactoTelefonico> findBySolicitudAtencionIdOrderByFechaDesc(Long solicitudId);
}
