package fm.fmesnata.cryptomarket.config;

import fm.fmesnata.cryptomarket.account.exception.AccountNotFoundException;
import fm.fmesnata.cryptomarket.crypto.exception.CryptocurrencyNotFoundException;
import fm.fmesnata.cryptomarket.investment.exception.BalanceInsufficientException;
import fm.fmesnata.cryptomarket.investment.exception.InsufficientQuantityException;
import fm.fmesnata.cryptomarket.investment.exception.InvestmentNotFound;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler({
            AccountNotFoundException.class,
            CryptocurrencyNotFoundException.class,
            BalanceInsufficientException.class,
            InsufficientQuantityException.class,
            InvestmentNotFound.class
    })
    public ResponseEntity<Object> buildClientErrorResponse(RuntimeException e) {
        return ResponseEntity.unprocessableEntity()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Object() {
                    public final String error = e.getMessage();
                });
    }
}
