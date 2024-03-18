package co.com.microservicio.inscripciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListEventResponseDTO {
    private String message;
    private HttpStatus status;
    private List<EventDTO> data;
}
