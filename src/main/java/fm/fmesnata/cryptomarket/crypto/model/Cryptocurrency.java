package fm.fmesnata.cryptomarket.crypto.model;

import javax.persistence.*;
import java.math.BigDecimal;

//@Entity
public class Cryptocurrency {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "crypto_id")
    private Long cryptoId;

    private String name;

    private String code;

    private BigDecimal price;

    public Long getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Long cryptoId) {
        this.cryptoId = cryptoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
