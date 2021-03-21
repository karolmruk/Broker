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
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/join")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Join {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    ClientFacade clientFacade;
    MessageFacade messageFacade;
    SenderFacade senderFacade;

    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void join(@RequestBody @Validated Request request) {
        request.topics.forEach(topic -> clientFacade.create(Client.create(request.target, topic)));
        request.topics.stream()
                .flatMap(topic -> messageFacade.getForTopic(new Message.Topic(topic)).stream())
                .forEach(message -> executor.execute(() -> senderFacade.send(note(request.target, message))));
    }

    private Note note(String target, Message message) {
        return Note.of(message.getId().get(), target, message.getMemo().get());
    }

    @Value
    private static class Request {
        @URL
        String target;
        @NotEmpty
        Set<@NotBlank String> topics;
    }
}
