package co.com.microservicio.inscripciones.controller;

import co.com.microservicio.inscripciones.DTO.AssistantDTO;
import co.com.microservicio.inscripciones.exeptions.HttpException;
import co.com.microservicio.inscripciones.responses.ResponseHandler;
import co.com.microservicio.inscripciones.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/inscription")
public class InscriptionController {

    private final InscriptionService inscriptionService;


    /**
     * Devuelve una lista de todas las inscripciones.
     *
     * @return ResponseEntity con una lista de todas las inscripciones.
     */
    @GetMapping("/getInscriptions")
    public ResponseEntity<?> getInscriptions(){
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, inscriptionService.getInscriptions());
    }

    /**
     * Añade un asistente a un evento específico.
     *
     * @param eventId El ID del evento al que se desea añadir el asistente.
     * @param userId El ID del usuario que se desea añadir como asistente al evento.
     * @return ResponseEntity con un mensaje indicando que el usuario ha sido inscrito correctamente en el evento.
     * @throws HttpException Si hay algún problema durante el procesamiento.
     */
    @PostMapping("/addAssistantInscription/{eventId}/{userId}")
    public ResponseEntity<?> addAssistantInscription(@PathVariable String eventId, @PathVariable String userId) throws HttpException {
        return ResponseHandler.generateResponse("signed up", HttpStatus.OK, inscriptionService.addAssistantInscription(Integer.parseInt(eventId), Integer.parseInt(userId)));
    }

    /**
     * Elimina un asistente de un evento específico.
     *
     * @param eventId El ID del evento del que se desea eliminar el asistente.
     * @param userId El ID del usuario que se desea eliminar como asistente del evento.
     * @return ResponseEntity con un mensaje indicando que el usuario ha sido eliminado correctamente del evento.
     * @throws HttpException Si hay algún problema durante el procesamiento.
     */
    @PostMapping("/deleteAssistantInscription/{eventId}/{userId}")
    public ResponseEntity<?> deleteAssistantToEventByEventId(@PathVariable String eventId, @PathVariable String userId) throws HttpException {
        return ResponseHandler.generateResponse("deleted from event", HttpStatus.OK, inscriptionService.deleteAssistantInscription(Integer.parseInt(eventId), Integer.parseInt(userId)));
    }

}
