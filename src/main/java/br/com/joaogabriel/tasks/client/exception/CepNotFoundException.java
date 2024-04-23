package br.com.joaogabriel.tasks.client.exception;

public class CepNotFoundException extends RuntimeException {
    public CepNotFoundException(String message) {
        super(message);
    }

    public CepNotFoundException() {}
}
