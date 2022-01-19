package fm.fmesnata.cryptomarket.crypto.repository.impl;

import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyCoincapRepository;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CryptocurrencyCoincapRepositoryImplTest {

    public static MockWebServer mockWebServer;

    @Autowired
    private CryptocurrencyCoincapRepository cryptocurrencyCoincapRepository;

    @BeforeAll
    static void init() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(createDispatcher2());
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("coincap.url", () -> "http://localhost:" + mockWebServer.getPort());
    }

    @AfterAll
    static void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

//    @Test
//    void test() {
//        Flux<Cryptocurrency> all = cryptocurrencyCoincapRepository.findAll();
//
//        StepVerifier.create(all)
//                .expectNextMatches(cryptocurrency -> cryptocurrency.getName().equals("bitcoin")
//                        && cryptocurrency.getPrice().compareTo(new BigDecimal("2000")) == 0)
//                .verifyComplete();
//    }

    @Test
    void testFindByName() {
        Mono<Cryptocurrency> all = cryptocurrencyCoincapRepository.findByName("bitcoin");

        StepVerifier.create(all)
                .expectNextMatches(cryptocurrency -> cryptocurrency.getName().equals("bitcoin")
                        && cryptocurrency.getPrice().compareTo(new BigDecimal("2000")) == 0)
                .verifyComplete();
    }

    @Test
    void testError() {
        Mono<Cryptocurrency> all = cryptocurrencyCoincapRepository.findByName("toto");

        StepVerifier.create(all)
                .expectErrorMatches(error -> error.getMessage().equals("Cette cryptomonnaie n'existe pas sur le marché"))
                .verify();
    }


//    private static Dispatcher createDispatcher() {
//        return new Dispatcher() {
//            @Override
//            public MockResponse dispatch(RecordedRequest request) {
//                if (request.getPath().equals("/rates")) {
//                    return new MockResponse().setResponseCode(200).setBody("""
//                            {
//                                "data": [{
//                                "id": "bitcoin",
//                                "rateUsd": 2000
//                                }]
//                            }
//                            """)
//                            .setHeader("Content-Type", "application/json");
//                }
//                return new MockResponse().setResponseCode(404);
//            }
//        };
//    }

    private static Dispatcher createDispatcher2() {
        return new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if (request.getPath().equals("/rates/bitcoin")) {
                    return new MockResponse().setResponseCode(200).setBody("""
                            {
                                "data": {
                                "id": "bitcoin",
                                "rateUsd": 2000
                                }
                            }
                            """)
                            .setHeader("Content-Type", "application/json");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
    }

}
