package fm.fmesnata.cryptomarket.crypto.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CryptocurrencyService {
    Flux<CryptocurrencyDTO> listAll();

    Mono<CryptocurrencyDTO> findByName(String name);
}
