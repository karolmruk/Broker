package kmr.kuajiri.broker.command;

import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping(path = "/client")
class Client {
    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void publish(@RequestBody @Validated Request request) {
        log.info(request.toString());
    }

    @Value
    @ToString
    private static class Request {
        @NotBlank
        String id;
        @NotBlank
        String message;
    }
}
