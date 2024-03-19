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


    @GetMapping("/getAssistantByEventId/{id}")
    public ResponseEntity<?> getAssistantByEventId(@PathVariable Integer id) throws HttpException {
        inscriptionService.reloadEvents();
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, inscriptionService.getAssistantListFromEventId(id));
    }

    @GetMapping("/getInscriptions")
    public ResponseEntity<?> getInscriptions(){
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, inscriptionService.getInscriptions());
    }

    @PostMapping("/addAssistantInscription/{eventId}/{userId}")
    public ResponseEntity<?> addAssistantInscription(@PathVariable String eventId, @PathVariable String userId) throws HttpException {
        return ResponseHandler.generateResponse("signed up", HttpStatus.OK, inscriptionService.addAssistantInscription(Integer.parseInt(eventId), Integer.parseInt(userId)));
    }

    @PostMapping("/deleteAssistantInscription/{eventId}/{userId}")
    public ResponseEntity<?> deleteAssistantToEventByEventId(@PathVariable String eventId, @PathVariable String userId) throws HttpException {
        return ResponseHandler.generateResponse("deleted from event", HttpStatus.OK, inscriptionService.deleteAssistantInscription(Integer.parseInt(eventId), Integer.parseInt(userId)));
    }

}
