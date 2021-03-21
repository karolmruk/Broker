package kmr.kuajiri.broker.domain.messages.boundry;

import kmr.kuajiri.broker.util.Field;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {
    Id id;
    Topic topic;
    Expiration expiration;
    Memo memo;

    public static Message create(String topic, LocalDateTime expiration, String memo) {
        return new Message(null, new Topic(topic), new Expiration(expiration), new Memo(memo));
    }

    public static Message of(String id, String topic, LocalDateTime expiration, String memo) {
        return new Message(
                new Message.Id(id),
                new Message.Topic(topic),
                new Message.Expiration(expiration),
                new Message.Memo(memo)
        );
    }

    public static class Id extends Field<String> {
        public Id(String value) {
            super(value);
        }
    }

    public static class Topic extends Field<String> {
        public Topic(String value) {
            super(value);
        }
    }

    public static class Expiration extends Field<LocalDateTime> {
        public Expiration(LocalDateTime value) {
            super(value);
        }
    }

    public static class Memo extends Field<String> {
        public Memo(String value) {
            super(value);
        }
    }
}
