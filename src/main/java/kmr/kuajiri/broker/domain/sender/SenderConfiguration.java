package kmr.kuajiri.broker.domain.sender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SenderConfiguration {
    SenderFacade create() {
        return senderFacade();
    }

    @Bean
    SenderFacade senderFacade() {
        return new SenderFacade();
    }
}
