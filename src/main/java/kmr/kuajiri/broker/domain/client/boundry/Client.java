package kmr.kuajiri.broker.domain.client.boundry;

import kmr.kuajiri.broker.util.Field;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Client {
    Id id;
    Target target;
    Topic topic;

    public static Client create(String target, String topic) {
        return new Client(null, new Target(target), new Topic(topic));
    }

    public static Client of(String id, String target, String topic) {
        return new Client(new Id(id), new Target(target), new Topic(topic));
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

    public static class Target extends Field<String> {
        public Target(String value) {
            super(value);
        }
    }
}
