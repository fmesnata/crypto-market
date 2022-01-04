package fm.fmesnata.cryptomarket.account.service.impl;

import fm.fmesnata.cryptomarket.account.exception.AccountNotFoundException;
import fm.fmesnata.cryptomarket.account.repository.AccountRepository;
import fm.fmesnata.cryptomarket.account.service.AccountDto;
import fm.fmesnata.cryptomarket.account.service.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<AccountDto> getById(long id) {
        return accountRepository.findById(id)
                .map(account -> Mono.just(new AccountDto(
                        account.getAccountId(),
                        account.getOwner(),
                        account.getBalance())))
                .orElseThrow(AccountNotFoundException::new);
    }
}
