package br.com.joaogabriel.tasks.controller.payload;

import br.com.joaogabriel.tasks.model.enumerations.TaskState;

import java.io.Serializable;

public record TaskRequest(
       String title,
       String description,
       int priority
) implements Serializable {
}
