package kmr.kuajiri.broker.domain.client;

import kmr.kuajiri.broker.domain.client.boundry.Client;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@EqualsAndHashCode(of = "uuid")
@FieldDefaults(level = AccessLevel.PRIVATE)
class ClientEntity {
    final String uuid;
    String id;
    String target;
    String topic;

    ClientEntity(Client client) {
        this.uuid = UUID.randomUUID().toString();
        this.target = client.getTarget().get();
        this.topic = client.getTopic().get();
    }

    Client.Id clientId() {
        return new Client.Id(this.id);
    }

    Client client() {
        return Client.of(this.id, this.target, this.topic);
    }

    public boolean interestedIn(String topic) {
        return this.topic.equals(topic);
    }
}
