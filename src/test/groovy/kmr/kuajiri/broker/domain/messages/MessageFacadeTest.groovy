package kmr.kuajiri.broker.domain.messages

import kmr.kuajiri.broker.domain.TestClock
import kmr.kuajiri.broker.domain.messages.boundry.Message
import spock.lang.Specification

import java.time.*

class MessageFacadeTest extends Specification {
    MessageFacade messageFacade
    TestClock clock

    def setup() {
        clock = new TestClock(Clock.fixed(Instant.ofEpochMilli(946684800), ZoneOffset.UTC))
        messageFacade = new MessageConfiguration().create(clock)
    }

    def "create"() {
        Message expected = Message.create("games", LocalDateTime.MAX, "Awesome game!")
        when:
        def result = messageFacade.create(expected)
        then:
        result.getId() != null
        result.getTopic() == expected.getTopic()
        result.getExpiration() == expected.getExpiration()
        result.getMemo() == expected.getMemo()
    }

    def "get memo for topics"() {
        given:
        Message m0 = createMessage("games", LocalDateTime.MAX, "Memo 0!")
        Message m1 = createMessage("games", LocalDateTime.MAX, "Memo 1!")
        Message m2 = createMessage("movies", LocalDateTime.MAX, "Memo 2!")
        when:
        def messages = messageFacade.getForTopic(m0.getTopic())
        then:
        messages.contains(m0)
        messages.contains(m1)
        !messages.contains(m2)
    }

    def "get only not expired messages"() {
        given:
        Message m0 = createMessage("games", LocalDateTime.now(clock).plusSeconds(120), "Memo 0!")
        Message m1 = createMessage("games", LocalDateTime.now(clock).plusSeconds(60), "Memo 1!")
        when:
        def messages1 = messageFacade.getForTopic(m0.getTopic())
        then:
        messages1.contains(m0)
        messages1.contains(m1)
        when:
        clock.tick(Duration.ofSeconds(90))
        def messages2 = messageFacade.getForTopic(m0.getTopic())
        then:
        messages2.contains(m0)
        !messages2.contains(m1)
        when:
        clock.tick(Duration.ofSeconds(60))
        def messages3 = messageFacade.getForTopic(m0.getTopic())
        then:
        !messages3.contains(m0)
        !messages3.contains(m1)
    }

    Message createMessage(String topic, LocalDateTime expiration, String memo) {
        return messageFacade.create(Message.create(topic, expiration, memo))
    }
}
