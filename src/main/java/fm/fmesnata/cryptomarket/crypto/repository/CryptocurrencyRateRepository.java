package fm.fmesnata.cryptomarket.crypto.repository;

import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;

import java.util.List;
import java.util.Optional;

public interface CryptocurrencyRateRepository {
    List<Cryptocurrency> findAll();
    Optional<Cryptocurrency> findByCode(String code);
}
