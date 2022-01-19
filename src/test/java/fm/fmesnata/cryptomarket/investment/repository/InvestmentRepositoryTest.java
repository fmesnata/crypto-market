package fm.fmesnata.cryptomarket.investment.repository;

import fm.fmesnata.cryptomarket.account.model.Account;
import fm.fmesnata.cryptomarket.investment.model.Investment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
class InvestmentRepositoryTest {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void test_repo() {
        Account account = new Account();
        account.setOwner("farid");
        account.setBalance(new BigDecimal("200"));
        Account accountWithId = testEntityManager.persistFlushFind(account);

        Investment investment = new Investment();
        investment.setAccount(accountWithId);
        investment.setCryptocurrency("bitcoin");
        investment.setAmount(new BigDecimal("1521"));
        investment.setQuantity(3);
        testEntityManager.persistAndFlush(investment);

        // TODO changer l'account id pour montrer investissement vide
        // List<Investment> investments = investmentRepository.getByAccountId(5);
        List<Investment> investments = investmentRepository.getByAccountId(accountWithId.getAccountId());

        //Assertions.assertThat(investments).isEmpty();
        Assertions.assertThat(investments).hasSize(1);
        Assertions.assertThat(investments.get(0).getCryptocurrency()).isEqualTo("bitcoin");
        Assertions.assertThat(investments.get(0).getQuantity()).isEqualTo(3);
        Assertions.assertThat(investments.get(0).getAmount()).isEqualTo(new BigDecimal("1521"));
    }
}
