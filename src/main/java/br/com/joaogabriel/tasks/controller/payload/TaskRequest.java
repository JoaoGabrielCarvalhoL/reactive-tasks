package br.com.joaogabriel.tasks.controller.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record TaskRequest(
        @NotBlank
        String title,

        @NotBlank
        String description,

        @Min(1)
        int priority
) implements Serializable {
}
