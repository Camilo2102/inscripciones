package co.com.microservicio.inscripciones.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO {
    private Integer id;

    private String name;

    private LocalDate date;

    private String location;

    private String description;

    private Integer id_User;

}
