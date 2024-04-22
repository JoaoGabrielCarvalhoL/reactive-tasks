package br.com.joaogabriel.tasks.handler.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ErrorResponse(
        String title,
        Integer httpStatusCode,
        String message,
        LocalDateTime occurredIn
) implements Serializable {
}
