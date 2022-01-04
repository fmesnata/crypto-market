package fm.fmesnata.cryptomarket.investment.exception;

public class BalanceInsufficientException extends RuntimeException {
    public BalanceInsufficientException() {
        super("Votre solde est insuffisant pour faire cet investissement");
    }
}
