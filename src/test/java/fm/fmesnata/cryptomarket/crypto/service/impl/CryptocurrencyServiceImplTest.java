package fm.fmesnata.cryptomarket.crypto.service.impl;

import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyCoincapRepository;
import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyDTO;
import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CryptocurrencyServiceImplTest {

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @MockBean
    private CryptocurrencyCoincapRepository cryptocurrencyCoincapRepository;

    @Test
    void test_listAll() {
        Cryptocurrency data = new Cryptocurrency();
        data.setName("bitcoin");
        data.setPrice(new BigDecimal("123"));
        Mockito.when(cryptocurrencyCoincapRepository.findByName("bitcoin"))
                .thenReturn(Mono.just(data));

        Mono<CryptocurrencyDTO> bitcoin = cryptocurrencyService.findByName("bitcoin");

        StepVerifier.create(bitcoin)
                .expectNextMatches(cryptocurrencyDTO -> cryptocurrencyDTO.name().equals("bitcoin")
                        && cryptocurrencyDTO.price().equals(new BigDecimal("123"))
                ).verifyComplete();
    }

}
