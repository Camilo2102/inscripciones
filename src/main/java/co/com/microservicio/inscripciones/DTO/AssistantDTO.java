package co.com.microservicio.inscripciones.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssistantDTO {

    private Integer id;

    private String name;

    private String email;

    @JsonIgnore
    private EventDTO event;
}
