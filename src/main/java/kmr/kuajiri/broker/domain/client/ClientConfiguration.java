package kmr.kuajiri.broker.domain.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientConfiguration {
    ClientFacade create() {
        return clientFacade(new ClientInMemoryRepository());
    }

    @Bean
    ClientFacade clientFacade(ClientRepository clientRepository) {
        return new ClientFacade(clientRepository);
    }

    @Bean
    ClientRepository clientRepository() {
        return new ClientInMemoryRepository();
    }
}
