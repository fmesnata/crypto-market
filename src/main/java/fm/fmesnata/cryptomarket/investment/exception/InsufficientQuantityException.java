package fm.fmesnata.cryptomarket.investment.exception;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException() {
        super("Vous ne pouvez pas vendre cette quantité de cryptomonnaie");
    }
}
