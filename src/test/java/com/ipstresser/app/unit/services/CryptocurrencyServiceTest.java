package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Cryptocurrency;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.CryptocurrencyNotFoundException;
import com.ipstresser.app.repositories.CryptocurrencyRepository;
import com.ipstresser.app.services.CryptocurrencyServiceImpl;
import com.ipstresser.app.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CryptocurrencyServiceTest {


    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private CryptocurrencyServiceImpl cryptocurrencyService;

    private Cryptocurrency cryptocurrencyOne;

    private Cryptocurrency cryptocurrencyTwo;

    private CryptocurrencyServiceModel cryptoServiceModelOne;

    private CryptocurrencyServiceModel cryptoServiceModelTwo;

    private User user;


    @BeforeEach
    public void init() {
        this.user = new User();
        this.user.setUsername("vladimir");
        this.user.setId("1");

        this.cryptocurrencyOne = new Cryptocurrency("Bitcoin",
                "Bitcoin was the first cryptocurrency to successfully record transactions on a secure, decentralized blockchain-based network. Launched in early 2009 by its pseudonymous creator Satoshi Nakamoto, Bitcoin is the largest cryptocurrency measured by market capitalization and amount of data stored on its blockchain.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://static.coindesk.com/wp-content/uploads/2018/11/dark-bitcoin-scaled.jpg");

        this.cryptocurrencyTwo = new Cryptocurrency("Ethereum", "Ethereum is open access to digital money and data-friendly services for everyone – no matter your background or location. It's a community-built technology behind the cryptocurrency Ether (ETH) and thousands of applications you can use today.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://www.investopedia.com/thmb/eLkTSBXs8esM5-XGr2IdhEI5pi8=/735x0/shutterstock_1030451626-5bfc30d646e0fb0026026b76.jpg");

        this.cryptoServiceModelOne = new CryptocurrencyServiceModel();
        this.cryptoServiceModelOne.setTitle(this.cryptocurrencyOne.getTitle());
        this.cryptoServiceModelOne.setAuthor(this.user);

        this.cryptoServiceModelTwo = new CryptocurrencyServiceModel();
        this.cryptoServiceModelTwo.setTitle(this.cryptocurrencyTwo.getTitle());
        this.cryptoServiceModelTwo.setAuthor(this.user);

    }


    @Test
    public void getAllCryptocurrenciesShouldReturnAllEntities() {
        Mockito.when(this.cryptocurrencyRepository.findAll()).thenReturn(List.of(cryptocurrencyOne, cryptocurrencyTwo));
        Mockito.when(this.modelMapper.map(this.cryptocurrencyRepository.findAll(), CryptocurrencyServiceModel[].class))
                .thenReturn(List.of(this.cryptoServiceModelOne, this.cryptoServiceModelTwo).toArray(CryptocurrencyServiceModel[]::new));

        List<CryptocurrencyServiceModel> actual = this.cryptocurrencyService.getAllCryptocurrencies();

        assertEquals(2, actual.size());

    }

    @Test
    public void getCryptocurrencyByNameShouldReturnCorrect() {
        Mockito.when(this.cryptocurrencyRepository.findByTitle("Ethereum")).thenReturn(Optional.of(this.cryptocurrencyTwo));
        Mockito.when(this.modelMapper.map(this.cryptocurrencyOne, CryptocurrencyServiceModel.class))
                .thenReturn(cryptoServiceModelOne);

        CryptocurrencyServiceModel actual = this.cryptocurrencyService.getCryptocurrencyByName("Ethereum");

        assertEquals(cryptoServiceModelOne.getTitle(), actual.getTitle());
        assertEquals(cryptoServiceModelOne.getAuthor(), actual.getAuthor());
        Mockito.verify(this.cryptocurrencyRepository).findByTitle("Ethereum");

    }

    @Test
    public void getCryptocurrencyByNameShouldThrowCryptocurrencyNotFoundException(){
        Mockito.when(this.cryptocurrencyRepository.findByTitle("Exception")).thenReturn(Optional.empty());

        assertThrows(CryptocurrencyNotFoundException.class,()->{
            this.cryptocurrencyService.getCryptocurrencyByName("Exception");
        });
    }

    @Test
    public void registerCryptocurrencyShouldWorkCorrect() {
        Mockito.when(this.userService.getUserByUsername("vladimir")).thenReturn(new UserServiceModel());
        Mockito.when(this.modelMapper.map(this.userService.getUserByUsername("vladimir"), User.class))
                .thenReturn(this.user);
        Mockito.when(this.modelMapper.map(this.cryptoServiceModelOne, Cryptocurrency.class)).thenReturn(this.cryptocurrencyOne);

        this.cryptocurrencyService.registerCryptocurrency(cryptoServiceModelOne, "vladimir");
        Mockito.verify(this.cryptocurrencyRepository).save(this.cryptocurrencyOne);
    }


    @Test
    @WithMockUser(roles = {"ADMIN","ROOT"})
    public void deleteByIdShouldWorkCorrect() {
        this.cryptocurrencyRepository.deleteById("1");
        Mockito.verify(this.cryptocurrencyRepository).deleteById("1");
    }
}
