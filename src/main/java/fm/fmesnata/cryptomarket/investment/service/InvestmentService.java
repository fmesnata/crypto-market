package fm.fmesnata.cryptomarket.investment.service;

import fm.fmesnata.cryptomarket.investment.InvestmentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InvestmentService {

    Flux<InvestmentDto> listInvestment(long accountId);

    Mono<Void> invest(InvestRequest investRequest);

    Mono<Void> sell(SaleRequest saleRequest);
}
