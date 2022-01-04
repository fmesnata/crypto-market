package fm.fmesnata.cryptomarket.account.service;

import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<AccountDto> getById(long id);
}
