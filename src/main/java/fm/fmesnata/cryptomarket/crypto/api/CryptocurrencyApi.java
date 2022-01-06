package fm.fmesnata.cryptomarket.crypto.api;

import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyDTO;
import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/cryptocurrencies")
public class CryptocurrencyApi {

    private final CryptocurrencyService cryptocurrencyService;

    public CryptocurrencyApi(CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GetMapping
    public Flux<CryptocurrencyDTO> listAll() {
        return cryptocurrencyService.listAll();
    }

    @GetMapping(params = "name")
    public Mono<CryptocurrencyDTO> findByName(@RequestParam String name) {
        return cryptocurrencyService.findByName(name);
    }
}
