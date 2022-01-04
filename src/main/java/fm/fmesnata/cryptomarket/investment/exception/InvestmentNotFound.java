package fm.fmesnata.cryptomarket.investment.exception;

public class InvestmentNotFound extends RuntimeException {
    public InvestmentNotFound() {
        super("Vous n'avez pas d'investissement de ce type");
    }
}
