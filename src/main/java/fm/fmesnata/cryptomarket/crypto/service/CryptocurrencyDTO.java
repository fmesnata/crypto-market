package fm.fmesnata.cryptomarket.crypto.service;

import java.math.BigDecimal;

public record CryptocurrencyDTO(long id, String name, String code, BigDecimal price) {}
