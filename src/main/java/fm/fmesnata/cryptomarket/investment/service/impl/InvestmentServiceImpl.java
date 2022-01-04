package fm.fmesnata.cryptomarket.investment.service.impl;

import fm.fmesnata.cryptomarket.account.exception.AccountNotFoundException;
import fm.fmesnata.cryptomarket.account.model.Account;
import fm.fmesnata.cryptomarket.account.repository.AccountRepository;
import fm.fmesnata.cryptomarket.crypto.exception.CryptocurrencyNotFoundException;
import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyRateCoincapRepository;
import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyRateRepository;
import fm.fmesnata.cryptomarket.crypto.repository.CryptocurrencyRepository;
import fm.fmesnata.cryptomarket.investment.InvestmentDto;
import fm.fmesnata.cryptomarket.investment.exception.BalanceInsufficientException;
import fm.fmesnata.cryptomarket.investment.exception.InsufficientQuantityException;
import fm.fmesnata.cryptomarket.investment.exception.InvestmentNotFound;
import fm.fmesnata.cryptomarket.investment.model.Investment;
import fm.fmesnata.cryptomarket.investment.repository.InvestmentRepository;
import fm.fmesnata.cryptomarket.investment.service.InvestRequest;
import fm.fmesnata.cryptomarket.investment.service.InvestmentService;
import fm.fmesnata.cryptomarket.investment.service.SaleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;


@Service
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final AccountRepository accountRepository;
    private final TransactionTemplate transactionTemplate;
    private final CryptocurrencyRateCoincapRepository cryptocurrencyRateCoincapRepository;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 AccountRepository accountRepository,
                                 TransactionTemplate transactionTemplate,
                                 CryptocurrencyRateCoincapRepository cryptocurrencyRateCoincapRepository) {
        this.investmentRepository = investmentRepository;
        this.accountRepository = accountRepository;
        this.transactionTemplate = transactionTemplate;
        this.cryptocurrencyRateCoincapRepository = cryptocurrencyRateCoincapRepository;
    }

    @Override
    public Flux<InvestmentDto> listInvestment(long accountId) {
        return Mono.fromCallable(() -> transactionTemplate.execute(status -> investmentRepository.getByAccountId(accountId)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(investment ->
                        new InvestmentDto(
                                investment.getCryptocurrency(),
                                investment.getQuantity(),
                                investment.getAmount()
                        )
                );
    }

//    @Override
//    public Mono<Void> invest(InvestRequest investRequest) {
//        return Mono.fromCallable(() -> transactionTemplate.execute(status -> {
//                    Cryptocurrency cryptocurrency = cryptocurrencyRateRepository
//                            .findByCode(investRequest.cryptocurrency())
//                            .orElseThrow(CryptocurrencyNotFoundException::new);
//                    Account account = accountRepository
//                            .findById(investRequest.accountId())
//                            .orElseThrow(AccountNotFoundException::new);
//
//                    int quantity = investRequest.quantity();
//                    BigDecimal totalPrice = cryptocurrency.getPrice().multiply(BigDecimal.valueOf(quantity));
//
//                    if (account.getBalance().compareTo(totalPrice) < 0) {
//                        throw new BalanceInsufficientException();
//                    }
//
//                    Investment newInvestment = investmentRepository.findByAccountAndCryptocurrency(account, cryptocurrency)
//                            .map(investment -> {
//                                investment.setAmount(investment.getAmount().add(totalPrice));
//                                investment.setQuantity(investment.getQuantity() + investRequest.quantity());
//                                return investment;
//                            }).orElseGet(() -> {
//                                Investment investment = new Investment();
//                                investment.setCryptocurrency(cryptocurrency);
//                                investment.setAccount(account);
//                                investment.setQuantity(quantity);
//                                investment.setAmount(totalPrice);
//                                return investment;
//                            });
//                    account.setBalance(account.getBalance().subtract(totalPrice));
//                    accountRepository.save(account);
//                    return investmentRepository.save(newInvestment);
//                }))
//                .subscribeOn(Schedulers.boundedElastic())
//                .then();
//    }

    @Override
    public Mono<Void> invest(InvestRequest investRequest) {
        return cryptocurrencyRateCoincapRepository.findByCode(investRequest.cryptocurrency())
                .zipWhen(cryptocurrency -> Mono.fromCallable(() -> transactionTemplate.execute(status -> {
                            Account account = accountRepository
                                    .findById(investRequest.accountId())
                                    .orElseThrow(AccountNotFoundException::new);

                            int quantity = investRequest.quantity();
                            BigDecimal totalPrice = cryptocurrency.getPrice().multiply(BigDecimal.valueOf(quantity));

                            if (account.getBalance().compareTo(totalPrice) < 0) {
                                throw new BalanceInsufficientException();
                            }

                            Investment newInvestment = investmentRepository.findByAccountAndCryptocurrency(account, cryptocurrency.getName())
                                    .map(investment -> {
                                        investment.setAmount(investment.getAmount().add(totalPrice));
                                        investment.setQuantity(investment.getQuantity() + investRequest.quantity());
                                        return investment;
                                    }).orElseGet(() -> {
                                        Investment investment = new Investment();
                                        investment.setCryptocurrency(cryptocurrency.getName());
                                        investment.setAccount(account);
                                        investment.setQuantity(quantity);
                                        investment.setAmount(totalPrice);
                                        return investment;
                                    });
                            account.setBalance(account.getBalance().subtract(totalPrice));
                            accountRepository.save(account);
                            return investmentRepository.save(newInvestment);
                        }))
                        .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> sell(SaleRequest saleRequest) {
//        Mono<Cryptocurrency> cryptocurrencyMono = Mono.just(cryptocurrencyRateRepository.findByCode(saleRequest.cryptocurrency()))
//                .map(cryptocurrency -> cryptocurrency.orElseThrow(CryptocurrencyNotFoundException::new));
        Mono<Cryptocurrency> cryptocurrencyMono = cryptocurrencyRateCoincapRepository.findByCode(saleRequest.cryptocurrency());
        Mono<Account> accountMono = Mono.just(accountRepository.findById(saleRequest.accountId()))
                .map(account -> account.orElseThrow(AccountNotFoundException::new));

        return Mono.zip(cryptocurrencyMono, accountMono)
                .doOnNext(cryptoAndAccount -> {
                    int quantity = saleRequest.quantity();
                    Cryptocurrency cryptocurrency = cryptoAndAccount.getT1();
                    Account account = cryptoAndAccount.getT2();
                    Investment investment = investmentRepository.findByAccountAndCryptocurrency(account, cryptocurrency.getName())
                            .orElseThrow(InvestmentNotFound::new);
                    if (quantity > investment.getQuantity()) {
                        throw new InsufficientQuantityException();
                    }
                    BigDecimal totalPrice = cryptocurrency.getPrice().multiply(BigDecimal.valueOf(quantity));
                    account.setBalance(account.getBalance().add(totalPrice));
                    if (quantity == investment.getQuantity()) {
                        investmentRepository.delete(investment);
                    } else {
                        investment.setQuantity(investment.getQuantity() - quantity);
                        investment.setAmount(investment.getAmount().subtract(totalPrice));
                        investmentRepository.save(investment);
                    }
                    accountRepository.save(account);
                })
                .then();
    }
}
