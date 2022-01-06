package fm.fmesnata.cryptomarket.crypto.repository;

import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CryptocurrencyRateCoincapRepository {
    Flux<Cryptocurrency> findAll();
    Mono<Cryptocurrency> findByName(String code);
}
