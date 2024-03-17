package co.com.microservicio.inscripciones.controller;

import co.com.microservicio.inscripciones.DTO.AssistantDTO;
import co.com.microservicio.inscripciones.exeptions.HttpException;
import co.com.microservicio.inscripciones.responses.ResponseHandler;
import co.com.microservicio.inscripciones.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/inscription")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @GetMapping("/getAssistantByEventId/{id}")
    public ResponseEntity<?> getAssistantByEventId(@PathVariable Integer id) throws HttpException {
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, inscriptionService.getAssistantListFromEventId(id));
    }

    @GetMapping("/getInscriptions")
    public ResponseEntity<?> getInscriptions(){
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, inscriptionService.getInscriptions());
    }

    @PostMapping("/addAssistantByEventId/{id}")
    public ResponseEntity<?> addAssistantToEventByEventId(@PathVariable Integer id, @RequestBody AssistantDTO assistantDTO) throws HttpException {
        return ResponseHandler.generateResponse("signed up", HttpStatus.OK, inscriptionService.addAssistantByEventId(id, assistantDTO));
    }

    @PutMapping("/updateAssistantByEventId/{id}")
    public ResponseEntity<?> updateAssistantToEventByEventId(@PathVariable Integer id, @RequestBody AssistantDTO assistantDTO) throws HttpException {
        return ResponseHandler.generateResponse("signed up", HttpStatus.OK, inscriptionService.addAssistantByEventId(id, assistantDTO));
    }

    @PostMapping("/deleteAssistantByEventId/{id}")
    public ResponseEntity<?> deleteAssistantToEventByEventId(@PathVariable Integer id, @RequestBody AssistantDTO assistantDTO) throws HttpException {
        return ResponseHandler.generateResponse("deleted from event", HttpStatus.OK, inscriptionService.deleteAssistantToEventByEvent(id, assistantDTO));
    }

}
