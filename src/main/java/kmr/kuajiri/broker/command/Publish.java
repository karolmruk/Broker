package kmr.kuajiri.broker.command;

import kmr.kuajiri.broker.domain.client.ClientFacade;
import kmr.kuajiri.broker.domain.client.boundry.Client;
import kmr.kuajiri.broker.domain.messages.MessageFacade;
import kmr.kuajiri.broker.domain.messages.boundry.Message;
import kmr.kuajiri.broker.domain.sender.SenderFacade;
import kmr.kuajiri.broker.domain.sender.boundry.Note;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/publish")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Publish {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    ClientFacade clientFacade;
    MessageFacade messageFacade;
    SenderFacade senderFacade;

    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void publish(@RequestBody @Validated Request request) {
        Message message = messageFacade.create(Message.create(request.topic, LocalDateTime.now().plusSeconds(request.seconds), request.memo));
        clientFacade.getWithTopic(new Client.Topic(request.topic))
                .forEach(client -> executor.execute(() -> senderFacade.send(note(client.getTarget(), message))));
    }

    private Note note(Client.Target target, Message message) {
        return Note.of(message.getId().get(), target.get(), message.getMemo().get());
    }

    @Value
    private static class Request {
        @NotBlank
        String topic;
        @Positive
        int seconds;
        @NotBlank
        String memo;
    }
}
