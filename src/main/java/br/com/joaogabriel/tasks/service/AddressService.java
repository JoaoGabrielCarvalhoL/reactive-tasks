package br.com.joaogabriel.tasks.service;

import br.com.joaogabriel.tasks.client.response.Address;
import reactor.core.publisher.Mono;

public interface AddressService {

    Mono<Address> getAddress(String zipCode);
}
