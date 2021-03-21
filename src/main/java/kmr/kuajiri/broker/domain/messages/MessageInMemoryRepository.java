package kmr.kuajiri.broker.domain.messages;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class MessageInMemoryRepository implements MessageRepository {
    Map<String, MessageEntity> database = new ConcurrentHashMap<>();

    @Override
    @SneakyThrows
    public MessageEntity save(MessageEntity messageEntity) {
        String id = UUID.randomUUID().toString();
        FieldUtils.writeField(messageEntity, "id", id, true);
        database.put(id, messageEntity);
        return messageEntity;
    }

    @Override
    public Set<MessageEntity> findAllByTopicAndExpirationIsAfter(String topic, LocalDateTime now) {
        return database.values().stream()
                .filter(message -> message.isTopic(topic))
                .filter(message -> message.isActual(now))
                .collect(Collectors.toSet());
    }
}
