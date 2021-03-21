package kmr.kuajiri.broker.domain.client;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class ClientInMemoryRepository implements ClientRepository {
    Map<String, ClientEntity> database = new ConcurrentHashMap<>();

    @Override
    @SneakyThrows
    public ClientEntity save(ClientEntity clientEntity) {
        String id = UUID.randomUUID().toString();
        FieldUtils.writeField(clientEntity, "id", id, true);
        database.put(id, clientEntity);
        return clientEntity;
    }

    @Override
    public Set<ClientEntity> findAllByTopic(String topic) {
        return database.values().stream().filter(clientEntity -> clientEntity.interestedIn(topic)).collect(Collectors.toSet());
    }
}
