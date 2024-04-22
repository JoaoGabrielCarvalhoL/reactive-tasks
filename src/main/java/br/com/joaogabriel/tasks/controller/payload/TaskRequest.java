package br.com.joaogabriel.tasks.controller.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record TaskRequest(
        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        int priority
) implements Serializable {
}
