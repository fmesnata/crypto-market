package fm.fmesnata.cryptomarket.investment.api;

import fm.fmesnata.cryptomarket.investment.InvestmentDto;
import fm.fmesnata.cryptomarket.investment.service.InvestRequest;
import fm.fmesnata.cryptomarket.investment.service.InvestmentService;
import fm.fmesnata.cryptomarket.investment.service.SaleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/investments")
public class InvestmentApi {
    private final InvestmentService investmentService;

    public InvestmentApi(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping
    public Flux<InvestmentDto> getAll(@RequestParam long accountId) {
        return investmentService.listInvestment(accountId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> invest(@RequestBody InvestRequest investRequest) {
        return investmentService.invest(investRequest);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> sell(@RequestBody SaleRequest saleRequest) {
        return investmentService.sell(saleRequest);
    }
}
