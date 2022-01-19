package fm.fmesnata.cryptomarket.crypto.api;

import fm.fmesnata.cryptomarket.crypto.exception.CryptocurrencyNotFoundException;
import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyDTO;
import fm.fmesnata.cryptomarket.crypto.service.CryptocurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(controllers = CryptocurrencyApi.class)
class CryptocurrencyApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CryptocurrencyService cryptocurrencyService;

    @Test
    void return_crypto() {
        Mockito.when(cryptocurrencyService.findByName("bitcoin"))
                .thenReturn(Mono.just(new CryptocurrencyDTO("bitcoin", "btc", new BigDecimal("4000"))));

        webTestClient.get()
                .uri("/cryptocurrencies?name=bitcoin")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("""
                        {"name": "bitcoin","code": "btc", "price": 4000}
                        """);
    }

    @Test
    void return_error() {
        Mockito.when(cryptocurrencyService.findByName("bitcoin"))
                .thenThrow(new CryptocurrencyNotFoundException());

        webTestClient.get()
                .uri("/cryptocurrencies?name=bitcoin")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .json("""
                        {"error": "Cette cryptomonnaie n'existe pas sur le marché"}
                        """);
    }

}
