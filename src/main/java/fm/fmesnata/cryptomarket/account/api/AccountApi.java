package fm.fmesnata.cryptomarket.account.api;

import fm.fmesnata.cryptomarket.account.service.AccountDto;
import fm.fmesnata.cryptomarket.account.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/accounts")
public class AccountApi {
    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Mono<AccountDto> getById(@PathVariable long id) {
        return accountService.getById(id);
    }
}
