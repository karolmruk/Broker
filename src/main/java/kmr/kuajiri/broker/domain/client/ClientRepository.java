package kmr.kuajiri.broker.domain.client;

import java.util.Set;

interface ClientRepository {
    ClientEntity save(ClientEntity clientEntity);

    Set<ClientEntity> findAllByTopic(String topic);
}
