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

    @GetMapping
    public String test(){
        return "holi";
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createAssistant(@PathVariable String id) throws HttpException {
        return ResponseHandler.generateResponse("Created", HttpStatus.OK, assistantService.createAssistant(Integer.parseInt(id)));
    }

}
