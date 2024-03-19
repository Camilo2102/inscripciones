package co.com.microservicio.inscripciones.controller;

import co.com.microservicio.inscripciones.exeptions.HttpException;
import co.com.microservicio.inscripciones.responses.ResponseHandler;
import co.com.microservicio.inscripciones.services.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assistant")
public class AssistantController {
    private final AssistantService assistantService;

    /**
     * Crea un nuevo asistente con el ID especificado.
     *
     * @param id El ID del asistente que se desea crear.
     * @return ResponseEntity con un mensaje indicando que el asistente ha sido creado correctamente.
     * @throws HttpException Si hay algún problema durante el procesamiento.
     */
    @PostMapping("/{id}")
    public ResponseEntity<?> createAssistant(@PathVariable String id) throws HttpException {
        return ResponseHandler.generateResponse("Created", HttpStatus.OK, assistantService.createAssistant(Integer.parseInt(id)));
    }

}
