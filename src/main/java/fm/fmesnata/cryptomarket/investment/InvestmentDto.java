package fm.fmesnata.cryptomarket.investment;

import java.math.BigDecimal;

public record InvestmentDto(String cryptocurrency, int quantity, BigDecimal amount) {}
