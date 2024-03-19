package co.com.microservicio.inscripciones.repository;

import co.com.microservicio.inscripciones.models.Assistant;
import co.com.microservicio.inscripciones.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, String> {
    Assistant findByUserId(int userId);
}
