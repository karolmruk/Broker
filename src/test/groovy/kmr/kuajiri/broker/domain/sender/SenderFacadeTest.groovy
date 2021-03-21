package kmr.kuajiri.broker.domain.sender

import kmr.kuajiri.broker.domain.sender.boundry.Note
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification

class SenderFacadeTest extends Specification {
    @Shared
    MockWebServer mockBackEnd
    SenderFacade senderFacade

    void setupSpec() {
        mockBackEnd = new MockWebServer()
        mockBackEnd.start()
    }

    void setup() {
        senderFacade = new SenderConfiguration().create()
        mockBackEnd = new MockWebServer()
        mockBackEnd.start()
    }

    def cleanupSpec() {
        mockBackEnd.shutdown()
    }

    def "call"() {
        given:
        Note note = Note.of("id", String.format("http://%s:%s", mockBackEnd.getHostName(), mockBackEnd.getPort()), "Message body")
        mockBackEnd.enqueue(new MockResponse().setResponseCode(HttpStatus.NO_CONTENT.value()))
        when:
        senderFacade.send(note)
        def recordedRequest = mockBackEnd.takeRequest();
        then:
        recordedRequest.method == "POST"
        recordedRequest.body.toString().contains(note.getId().get())
        recordedRequest.body.toString().contains(note.getBody().get())
    }
}
