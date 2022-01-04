package fm.fmesnata.cryptomarket.account.repository;

import fm.fmesnata.cryptomarket.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
