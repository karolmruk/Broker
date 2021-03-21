package kmr.kuajiri.broker.domain.messages;

import java.time.LocalDateTime;
import java.util.Set;

interface MessageRepository {
    MessageEntity save(MessageEntity messageEntity);

    Set<MessageEntity> findAllByTopicAndExpirationIsAfter(String topic, LocalDateTime now);
}
