package co.com.microservicio.inscripciones.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InscriptionDTO {
    EventDTO event;
    BigDecimal totalQuotas;
    BigDecimal actualQuotas;
}
