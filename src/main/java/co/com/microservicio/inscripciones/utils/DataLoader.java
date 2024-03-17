package co.com.microservicio.inscripciones.utils;

import co.com.microservicio.inscripciones.DTO.EventDTO;
import co.com.microservicio.inscripciones.DTO.ResponseDTO;
import co.com.microservicio.inscripciones.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final WebClient.Builder webClientBuilder;
    private final InscriptionService inscriptionService;

    @Value("${base-url}")
    private String baseUrl;
    @Override
    public void run(String... args) throws Exception {
        log.info("starting");

        ResponseDTO result = this.webClientBuilder.build()
                .get()
                .uri(baseUrl + "event")
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();

        List<EventDTO> events = (List<EventDTO>) result.getData();

        if(inscriptionService.countEvents() != 0) {
            log.info("Data already initialized");
            return;
        }

        this.inscriptionService.multipleSaveNewEvents(events);
        log.info("data loaded");
    }
}
