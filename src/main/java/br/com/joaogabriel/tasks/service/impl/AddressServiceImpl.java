package br.com.joaogabriel.tasks.service.impl;

import br.com.joaogabriel.tasks.client.ViaCepClient;
import br.com.joaogabriel.tasks.client.exception.CepNotFoundException;
import br.com.joaogabriel.tasks.client.response.Address;
import br.com.joaogabriel.tasks.service.AddressService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AddressServiceImpl implements AddressService {
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());
    private final ViaCepClient viaCepClient;

    public AddressServiceImpl(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    @Override
    public Mono<Address> getAddress(String zipCode) {
        return Mono.just(zipCode)
                .doOnNext(it -> logger.log(Level.INFO, "Getting Address by cep: {0}", zipCode))
                .flatMap(viaCepClient::getAddress)
                .doOnError(it -> Mono.error(CepNotFoundException::new));
    }
}
