package co.com.microservicio.inscripciones.services;

import co.com.microservicio.inscripciones.DTO.*;
import co.com.microservicio.inscripciones.exeptions.HttpException;
import co.com.microservicio.inscripciones.models.Inscription;
import co.com.microservicio.inscripciones.repository.InscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final WebClient.Builder webClientBuilder;
    private final InscriptionRepository inscriptionRepository;
    private final ObjectMapper mapper;

    @Value("${base-url}")
    private String baseUrl;

    public List<AssistantDTO> getAssistantListFromEventId(Integer id) throws HttpException {
        return getEventById(id).getAssistants();
    }

    public List<InscriptionDTO> getInscriptions() {
        ListEventResponseDTO result = this.webClientBuilder.build()
                .get()
                .uri(baseUrl + "event")
                .retrieve()
                .bodyToMono(ListEventResponseDTO.class)
                .block();

        List<EventDTO> events = result.getData();


        List<InscriptionDTO> inscriptions = new ArrayList<>();

        for (EventDTO event : events) {
            Inscription inscription = this.inscriptionRepository.findInscriptionByEventId(event.getId());
            inscriptions.add(InscriptionDTO.builder()
                    .event(event)
                    .totalQuotas(inscription.getTotalQuotas())
                    .actualQuotas(inscription.getActualQuotas())
                    .build());
        }

        return inscriptions;
    }

    public Inscription addAssistantByEventId(Integer eventId, AssistantDTO assistantDTO) throws HttpException {
        AssistantDTO assistant = getAssistantById(assistantDTO.getId());
        EventDTO eventDTO = getEventById(eventId);

        if(isFull(eventId)){
            throw new HttpException("Event full", HttpStatus.OK);
        }

        if(assistant.getEvent() != null){
            reduceActualQuota(assistant.getEvent().getId());
        }

        assistantDTO.setEvent(eventDTO);

        updateAssistantInfo(assistant, eventId);

        return sumActualQuota(eventId);
    }

    private Inscription reduceActualQuota(Integer eventId){
        Inscription inscription = this.inscriptionRepository.findInscriptionByEventId(eventId);
        inscription.setActualQuotas(inscription.getActualQuotas().subtract(BigDecimal.ONE));
        return this.inscriptionRepository.save(inscription);
    }

    private Inscription sumActualQuota(Integer eventId){
        Inscription inscription = this.inscriptionRepository.findInscriptionByEventId(eventId);
        inscription.setActualQuotas(inscription.getActualQuotas().add(BigDecimal.ONE));
        return this.inscriptionRepository.save(inscription);
    }

    private boolean isFull(Integer eventId){
        Inscription inscription = this.inscriptionRepository.findInscriptionByEventId(eventId);
        return Objects.equals(inscription.getActualQuotas(), inscription.getTotalQuotas());
    }

    public Inscription deleteAssistantToEventByEvent(Integer eventId, AssistantDTO assistantDTO) throws HttpException {
        EventDTO eventDTO = getEventById(eventId);

        AssistantDTO assistant = eventDTO.getAssistants().stream().filter(assistantFound -> Objects.equals(assistantFound.getId(), assistantDTO.getId())).findFirst().orElse(null);

        if (assistant == null) {
            throw new HttpException("Assistant not in this event", HttpStatus.BAD_REQUEST);
        }

        assistantDTO.setEvent(null);

        updateAssistantInfo(assistant, eventId);

        return reduceActualQuota(eventId);

    }

    private void updateAssistantInfo(AssistantDTO assistant, Integer eventId) throws HttpException {
        ResponseDTO result = this.webClientBuilder.build()
                .put()
                .uri(baseUrl + "event" + assistant.getId())
                .bodyValue(assistant)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();

        try {
            AssistantDTO resultDTO = (AssistantDTO) result.getData();
        } catch (Exception e) {
            throw new HttpException("Failed", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    private AssistantDTO getAssistantById(Integer id) throws HttpException {
        ResponseDTO result = this.webClientBuilder.build()
                .get()
                .uri(baseUrl + "assistant/" + id)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();

        try {
            AssistantDTO assistant = mapper.convertValue(result.getData(), AssistantDTO.class);

            if (assistant == null) throw new HttpException("Not found", HttpStatus.NOT_FOUND);

            return (AssistantDTO) result.getData();
        }catch (Exception e ){
            throw new HttpException("Not found", HttpStatus.NOT_FOUND);
        }
    }

    private EventDTO getEventById(Integer id) throws HttpException {
        ResponseDTO result = this.webClientBuilder.build()
                .get()
                .uri(baseUrl + "event/" + id)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();

        if (result == null) throw new HttpException("Not found", HttpStatus.NOT_FOUND);

        return mapper.convertValue(result.getData(), EventDTO.class) ;
    }


    public void multipleSaveNewEvents(List<EventDTO> events) {
        List<Inscription> inscriptions = new ArrayList<>();
        for (EventDTO event : events) {
            inscriptions.add(Inscription.builder()
                    .eventId(event.getId())
                    .totalQuotas(new BigDecimal(100))
                    .actualQuotas(BigDecimal.ZERO)
                    .build());
        }

        this.inscriptionRepository.saveAll(inscriptions);
    }

    public long countEvents() {
        return inscriptionRepository.count();
    }
}
