package fm.fmesnata.cryptomarket.crypto.service;

import fm.fmesnata.cryptomarket.crypto.model.Cryptocurrency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CryptocurrencyMapper {

    CryptocurrencyDTO map(Cryptocurrency cryptocurrency);
}
