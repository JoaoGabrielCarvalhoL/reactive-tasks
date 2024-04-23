package br.com.joaogabriel.tasks.client;

import br.com.joaogabriel.tasks.client.response.Address;
import br.com.joaogabriel.tasks.client.exception.CepNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ViaCepClient {

    private final WebClient webClient;

    private final String VIA_CEP_URI = "/{cep}/json";

    public ViaCepClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Address> getAddress(String zipCode) {
        return this.webClient.get()
                .uri(VIA_CEP_URI, zipCode)
                .retrieve()
                .bodyToMono(Address.class)
                .onErrorResume(error -> Mono.error(CepNotFoundException::new));
    }
}
