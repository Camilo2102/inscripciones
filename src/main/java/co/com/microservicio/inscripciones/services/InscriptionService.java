package co.com.microservicio.inscripciones.services;

import co.com.microservicio.inscripciones.DTO.*;
import co.com.microservicio.inscripciones.exeptions.HttpException;
import co.com.microservicio.inscripciones.models.Assistant;
import co.com.microservicio.inscripciones.models.Inscription;
import co.com.microservicio.inscripciones.repository.AssistantRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final WebClient.Builder webClientBuilder;
    private final InscriptionRepository inscriptionRepository;
    private final AssistantRepository assistantRepository;
    private final ObjectMapper mapper;

    @Value("${base-url}")
    private String baseUrl;

    public List<AssistantDTO> getAssistantListFromEventId(Integer id) throws HttpException {
        return null;
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
                    .assistants(inscription.getAssistants())
                    .build());
        }

        return inscriptions;
    }


    public void reloadEvents(){
        ListEventResponseDTO result = this.webClientBuilder.build()
                .get()
                .uri(baseUrl + "event")
                .retrieve()
                .bodyToMono(ListEventResponseDTO.class)
                .block();

        List<EventDTO> events = (List<EventDTO>) result.getData();

        this.multipleSaveNewEvents(events);
    }


    public void multipleSaveNewEvents(List<EventDTO> events) {
        List<Inscription> inscriptions = new ArrayList<>();
        for (EventDTO event : events) {
            Inscription inscriptionFound = this.inscriptionRepository.findInscriptionByEventId(event.getId());

            if(inscriptionFound != null) continue;

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

    public Inscription addAssistantInscription(int eventId, int userId) {
        Inscription inscription = this.inscriptionRepository.findInscriptionByEventId(eventId);

        Assistant inscribed = inscription.getAssistants().stream().filter(assistant1 -> assistant1.getUserId() == userId).findFirst().orElse(null);

        if(inscribed != null) return null;

        Assistant assistant = this.assistantRepository.findByUserId(userId);

        inscription.getAssistants().add(assistant);

        inscription.setActualQuotas(inscription.getActualQuotas().add(BigDecimal.ONE));

        return this.inscriptionRepository.save(inscription);
    }

    public Inscription deleteAssistantInscription(int eventId, int userId) {
        Inscription inscription = this.inscriptionRepository.findInscriptionByEventId(eventId);

        List<Assistant> assistants = inscription.getAssistants().stream().filter(assistant1 -> assistant1.getUserId() != userId).collect(Collectors.toList());

        inscription.setAssistants(assistants);
        inscription.setActualQuotas(inscription.getActualQuotas().subtract(BigDecimal.ONE));

        return this.inscriptionRepository.save(inscription);
    }
}
