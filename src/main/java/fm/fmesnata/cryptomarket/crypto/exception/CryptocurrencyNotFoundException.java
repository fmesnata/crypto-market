package fm.fmesnata.cryptomarket.crypto.exception;

public class CryptocurrencyNotFoundException extends RuntimeException {
    public CryptocurrencyNotFoundException() {
        super("Cette cryptomonnaie n'existe pas sur le marché");
    }
}
