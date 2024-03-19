package co.com.microservicio.inscripciones.services;

import co.com.microservicio.inscripciones.exeptions.HttpException;
import co.com.microservicio.inscripciones.models.Assistant;
import co.com.microservicio.inscripciones.models.Inscription;
import co.com.microservicio.inscripciones.repository.AssistantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistantService {
    private final AssistantRepository assistantRepository;

    public List<Inscription> getInscriptionsById(int id){
        return assistantRepository.findByUserId(id).getInscriptions();
    }

    public Assistant createAssistant(int id) throws HttpException {
        Assistant assistant = this.findAssistantById(id);

        if(assistant != null){
            return null;
        }

        return this.assistantRepository.save(Assistant.builder().userId(id).build());

    }

    private Assistant findAssistantById(int id) throws HttpException {
        return this.assistantRepository.findByUserId(id);
    }
}
