package kmr.kuajiri.broker.domain.sender.boundry;

import kmr.kuajiri.broker.util.Field;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Note {
    Id id;
    Recipient recipient;
    Body body;

    public static Note of(String id, String recipient, String body) {
        return new Note(new Id(id), new Recipient(recipient), new Body(body));
    }

    public static class Id extends Field<String> {
        public Id(String value) {
            super(value);
        }
    }

    public static class Recipient extends Field<String> {
        public Recipient(String value) {
            super(value);
        }
    }

    public static class Body extends Field<String> {
        public Body(String value) {
            super(value);
        }
    }
}
