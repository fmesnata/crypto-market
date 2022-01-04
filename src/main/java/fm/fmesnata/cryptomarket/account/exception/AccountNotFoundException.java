package fm.fmesnata.cryptomarket.account.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Ce compte est introuvable");
    }
}
