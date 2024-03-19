package co.com.microservicio.inscripciones.DTO;

import co.com.microservicio.inscripciones.models.Assistant;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InscriptionDTO {
    EventDTO event;
    BigDecimal totalQuotas;
    BigDecimal actualQuotas;
    List<Assistant> assistants;
}
