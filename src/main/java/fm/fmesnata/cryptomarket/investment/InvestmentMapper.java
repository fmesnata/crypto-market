package fm.fmesnata.cryptomarket.investment;

import fm.fmesnata.cryptomarket.investment.model.Investment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvestmentMapper {
    @Mapping(source = "investment.cryptocurrency", target = "cryptocurrency")
    @Mapping(source = "investment.quantity", target = "quantity")
    @Mapping(source = "investment.amount", target = "amount")
    InvestmentDto map(Investment investment);
}
