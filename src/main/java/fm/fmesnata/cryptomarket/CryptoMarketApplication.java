package fm.fmesnata.cryptomarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class CryptoMarketApplication {

    public static void main(String[] args) {
        //BlockHound.install();
        SpringApplication.run(CryptoMarketApplication.class, args);
    }

}
