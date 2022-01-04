package fm.fmesnata.cryptomarket.crypto.repository;

import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
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
                .flatMapMany(data -> {
                    return Flux.fromIterable(data.data());
                })
                .map(coincapCryptocurrency -> {
                    Cryptocurrency cryptocurrency = new Cryptocurrency();
                    cryptocurrency.setName(coincapCryptocurrency.id());
                    cryptocurrency.setCode(coincapCryptocurrency.symbol());
                    cryptocurrency.setPrice(coincapCryptocurrency.rateUsd());
                    return cryptocurrency;
                })
                .retryWhen(Retry.backoff(5, Duration.ofMillis(400)));
    }

    @Override
    public Mono<Cryptocurrency> findByCode(String code) {
        return webClient.get()
                .uri("/rates/{code}", code)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DataValue.class)
                .map(coincapCryptocurrency -> {
                    Cryptocurrency cryptocurrency = new Cryptocurrency();
                    cryptocurrency.setName(coincapCryptocurrency.data().id());
                    cryptocurrency.setCode(coincapCryptocurrency.data().symbol());
                    cryptocurrency.setPrice(coincapCryptocurrency.data().rateUsd());
                    return cryptocurrency;
                })
                .retryWhen(Retry.backoff(5, Duration.ofMillis(400)));
    }
}

record CoincapCryptocurrency(String id, String symbol, BigDecimal rateUsd) {}

record DataList(List<CoincapCryptocurrency> data) {}

record DataValue(CoincapCryptocurrency data) {}

