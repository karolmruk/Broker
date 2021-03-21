package kmr.kuajiri.broker.domain.messages;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class MessageConfiguration {
    MessageFacade create(Clock clock) {
        return messageFacade(clock, new MessageInMemoryRepository());
    }

    @Bean
    MessageFacade messageFacade(Clock clock, MessageRepository messageRepository) {
        return new MessageFacade(clock, messageRepository);
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    MessageRepository messageRepository() {
        return new MessageInMemoryRepository();
    }
}
