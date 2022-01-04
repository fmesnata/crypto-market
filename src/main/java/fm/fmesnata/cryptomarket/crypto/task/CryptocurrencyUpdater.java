package fm.fmesnata.cryptomarket.crypto.task;

import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component
public class CryptocurrencyUpdater {

//    private final CryptocurrencyRepository cryptocurrencyRepository;
//
//    public CryptocurrencyUpdater(CryptocurrencyRepository cryptocurrencyRepository) {
//        this.cryptocurrencyRepository = cryptocurrencyRepository;
//    }
//
//    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
//    void update() {
//        Mono.fromCallable(cryptocurrencyRepository::findAll)
//                .subscribeOn(Schedulers.boundedElastic())
//                .subscribe(cryptocurrencies -> {
//                    cryptocurrencies.forEach(cryptocurrency -> {
//                        double percentage = (ThreadLocalRandom.current().nextDouble(-20, 21)) / 100;
//                        BigDecimal price = cryptocurrency.getPrice().multiply(BigDecimal.valueOf(1 + percentage)).setScale(2, RoundingMode.CEILING);
//                        cryptocurrency.setPrice(price);
//                        cryptocurrencyRepository.save(cryptocurrency);
//                    });
//                });
//    }
}
