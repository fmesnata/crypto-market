package fm.fmesnata.cryptomarket.investment.repository;

import fm.fmesnata.cryptomarket.account.model.Account;
import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import fm.fmesnata.cryptomarket.investment.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    @Query("""
            select i
            from Investment i
            join i.account a
            where a.accountId = :accountId
            """)
    List<Investment> getByAccountId(@Param("accountId") long accountId);

    Optional<Investment> findByAccountAndCryptocurrency(Account account, String cryptocurrency);
}
