package kmr.kuajiri.broker.domain.sender;

import kmr.kuajiri.broker.domain.sender.boundry.Note;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class SenderFacade {
    WebClient webClient;

    SenderFacade() {
        this.webClient = WebClient.create();
    }

    @SneakyThrows
    public void send(Note note) {
        webClient.post()
                .uri(note.getRecipient().get())
                .bodyValue(new Request(note))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
