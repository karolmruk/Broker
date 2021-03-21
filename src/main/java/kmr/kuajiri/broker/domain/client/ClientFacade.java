package kmr.kuajiri.broker.domain.client;

import kmr.kuajiri.broker.domain.client.boundry.Client;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientFacade {
    ClientRepository repository;

    public Client create(Client client) {
        return repository.save(new ClientEntity(client)).client();
    }

    public Set<Client> getWithTopic(Client.Topic topic) {
        return repository.findAllByTopic(topic.get()).stream().map(ClientEntity::client).collect(Collectors.toSet());
    }
}
