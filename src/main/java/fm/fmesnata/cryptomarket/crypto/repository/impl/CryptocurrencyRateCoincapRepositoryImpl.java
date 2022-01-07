package fm.fmesnata.cryptomarket.crypto.repository.impl;

import fm.fmesnata.cryptomarket.crypto.exception.CryptocurrencyNotFoundException;
import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyRateCoincapRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;

@Component("cryptocurrencyRateCoincapRepository")
public class CryptocurrencyRateCoincapRepositoryImpl implements CryptocurrencyRateCoincapRepository {

    private final WebClient webClient;

    public CryptocurrencyRateCoincapRepositoryImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<Cryptocurrency> findAll() {
        return webClient.get()
                .uri("/rates")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DataList.class)
                .flatMapMany(data -> Flux.fromIterable(data.data()))
                .map(coincapCryptocurrency -> {
                    Cryptocurrency cryptocurrency = new Cryptocurrency();
                    cryptocurrency.setName(coincapCryptocurrency.id());
                    cryptocurrency.setCode(coincapCryptocurrency.symbol());
                    cryptocurrency.setPrice(coincapCryptocurrency.rateUsd().setScale(2, RoundingMode.CEILING));
                    return cryptocurrency;
                })
                .retryWhen(Retry.backoff(5, Duration.ofMillis(400)));
    }

    @Override
    public Mono<Cryptocurrency> findByName(String name) {
        return webClient.get()
                .uri("/rates/{name}", name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DataValue.class)
                .retryWhen(getRetryCondition())
                .onErrorMap(throwable -> new CryptocurrencyNotFoundException())
                .map(coincapCryptocurrency -> {
                    Cryptocurrency cryptocurrency = new Cryptocurrency();
                    cryptocurrency.setName(coincapCryptocurrency.data().id());
                    cryptocurrency.setCode(coincapCryptocurrency.data().symbol());
                    cryptocurrency.setPrice(coincapCryptocurrency.data().rateUsd().setScale(2, RoundingMode.CEILING));
                    return cryptocurrency;
                });
    }

    private RetryBackoffSpec getRetryCondition() {
        return Retry.backoff(5, Duration.ofMillis(400)).filter(throwable -> {
            if (throwable instanceof WebClientResponseException webClientResponseException) {
                return !webClientResponseException.getStatusCode().is4xxClientError();
            }
            return true;
        });
    }
}

record CoincapCryptocurrency(String id, String symbol, BigDecimal rateUsd) {
}

record DataList(List<CoincapCryptocurrency> data) {
}

record DataValue(CoincapCryptocurrency data) {
}
