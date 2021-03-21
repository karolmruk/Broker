package kmr.kuajiri.broker.domain.client

import kmr.kuajiri.broker.domain.client.boundry.Client
import spock.lang.Specification

class ClientFacadeTest extends Specification {
    ClientFacade clientFacade

    def setup() {
        clientFacade = new ClientConfiguration().create()
    }

    def "create"() {
        given:
        Client expected = Client.create("http://localhost:3000/message", "games")
        when:
        def result = clientFacade.create(expected)
        then:
        result.getId() != null
        result.getTarget() == expected.getTarget()
        result.getTopic() == expected.getTopic()

    }

    def "targets for topic"() {
        given:
        Client c1 = createClient("http://localhost:3000/message", "games")
        Client c2 = createClient("http://localhost:3000/message", "games")
        Client c3 = createClient("http://localhost:3000/message", "movies")
        when:
        def targets = clientFacade.getWithTopic(c1.getTopic())
        then:
        targets.contains(c1)
        targets.contains(c2)
        !targets.contains(c3)
    }

    Client createClient(String target, String topic) {
        return clientFacade.create(Client.create(target, topic))
    }
}
