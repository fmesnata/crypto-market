package fm.fmesnata.cryptomarket.account.service;

import java.math.BigDecimal;

public record AccountDto(long id, String owner, BigDecimal balance) {}
