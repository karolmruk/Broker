package kmr.kuajiri.broker.domain.messages;

import kmr.kuajiri.broker.domain.messages.boundry.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageFacade {
    Clock clock;
    MessageRepository messageRepository;

    public Message create(Message message) {
        return messageRepository.save(new MessageEntity(message)).message();
    }

    public Collection<Message> getForTopic(Message.Topic topic) {
        return messageRepository.findAllByTopicAndExpirationIsAfter(topic.get(), LocalDateTime.now(clock)).stream()
                .map(MessageEntity::message)
                .collect(Collectors.toSet());
    }
}

