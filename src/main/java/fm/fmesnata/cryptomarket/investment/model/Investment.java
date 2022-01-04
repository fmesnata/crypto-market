package fm.fmesnata.cryptomarket.investment.model;

import fm.fmesnata.cryptomarket.account.model.Account;
import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_id")
    private Long investmentId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

//    @ManyToOne
//    @JoinColumn(name = "crypto_id")
//    private Cryptocurrency cryptocurrency;

    private String cryptocurrency;

    private int quantity;

    private BigDecimal amount;

    public Long getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
