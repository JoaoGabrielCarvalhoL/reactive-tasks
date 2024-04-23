package br.com.joaogabriel.tasks.controller.payload;

import br.com.joaogabriel.tasks.client.response.Address;
import br.com.joaogabriel.tasks.model.enumerations.TaskState;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskResponse(String id,
                           String title,
                           String description,
                           int priority,
                           TaskState state,
                           Address address
) implements Serializable {
}
