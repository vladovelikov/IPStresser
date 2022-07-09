package com.ipstresser.app.init;

import com.ipstresser.app.domain.entities.Cryptocurrency;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.repositories.CryptocurrencyRepository;
import com.ipstresser.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class CryptoInit implements CommandLineRunner {

    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final UserRepository userRepository;

    @Autowired
    public CryptoInit(CryptocurrencyRepository cryptocurrencyRepository, UserRepository userRepository) {
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = this.userRepository.findUserByUsername("vladimir").orElse(null);
        Cryptocurrency bitcoin = new Cryptocurrency("Bitcoin", "Bitcoin is a decentralized digital currency that you can buy, sell and exchange directly, without an intermediary like a bank. Bitcoin's creator, Satoshi Nakamoto, originally described the need for an electronic payment system based on cryptographic proof instead of trust.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://static.coindesk.com/wp-content/uploads/2018/11/dark-bitcoin-scaled.jpg");
        Cryptocurrency ethereum = new Cryptocurrency("Ethereum", "Ethereum is open access to digital money and data-friendly services for everyone – no matter your background or location. It is a community-built technology behind the cryptocurrency Ether (ETH) and thousands of applications you can use today.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://img.money.com/2022/01/Explainer-What-Is-Ethereum.jpg");

        this.cryptocurrencyRepository.saveAll(List.of(bitcoin, ethereum));
    }
}
