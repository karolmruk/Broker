package kmr.kuajiri.broker.domain.messages;

import kmr.kuajiri.broker.domain.messages.boundry.Message;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(of = "uuid")
@FieldDefaults(level = AccessLevel.PRIVATE)
class MessageEntity {
    String id;
    String uuid;
    String topic;
    LocalDateTime expiration;
    String memo;

    MessageEntity(Message message) {
        this.uuid = UUID.randomUUID().toString();
        this.expiration = message.getExpiration().get();
        this.topic = message.getTopic().get();
        this.memo = message.getMemo().get();
    }

    Message.Id messageId() {
        return new Message.Id(this.id);
    }

    boolean isTopic(String topic) {
        return this.topic.equals(topic);
    }

    boolean isActual(LocalDateTime now) {
        return this.expiration.isAfter(now);
    }

    Message message() {
        return Message.of(this.id, this.topic, this.expiration, this.memo);
    }
}
