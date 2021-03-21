package kmr.kuajiri.broker.domain.sender;

import kmr.kuajiri.broker.domain.sender.boundry.Note;
import lombok.Value;

@Value
class Request {
    String id;
    String message;

    public Request(Note note) {
        this.id = note.getId().get();
        this.message = note.getBody().get();
    }
}
