package fm.fmesnata.cryptomarket.crypto.service.impl;

import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyRateCoincapRepository;
import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyDTO;
import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyRateCoincapRepository cryptocurrencyRateCoincapRepository;

    public CryptocurrencyServiceImpl(
            CryptocurrencyRateCoincapRepository cryptocurrencyRateCoincapRepository) {
        this.cryptocurrencyRateCoincapRepository = cryptocurrencyRateCoincapRepository;
    }

    @Override
    public Flux<CryptocurrencyDTO> listAll() {
        return cryptocurrencyRateCoincapRepository.findAll()
                .map(cryptocurrency -> new CryptocurrencyDTO(
                        cryptocurrency.getName(),
                        cryptocurrency.getCode(),
                        cryptocurrency.getPrice())
                );
    }

    @Override
    public Mono<CryptocurrencyDTO> findByName(String name) {
        return cryptocurrencyRateCoincapRepository.findByName(name)
                .map(cryptocurrency -> new CryptocurrencyDTO(
                        cryptocurrency.getName(),
                        cryptocurrency.getCode(),
                        cryptocurrency.getPrice())
                );
    }
}
