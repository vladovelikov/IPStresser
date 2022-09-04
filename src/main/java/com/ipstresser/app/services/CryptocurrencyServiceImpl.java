package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Cryptocurrency;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;
import com.ipstresser.app.exceptions.CryptocurrencyNotFoundException;
import com.ipstresser.app.repositories.CryptocurrencyRepository;
import com.ipstresser.app.services.interfaces.CryptocurrencyService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CryptocurrencyServiceImpl(CryptocurrencyRepository cryptocurrencyRepository, UserService userService, ModelMapper modelMapper) {
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CryptocurrencyServiceModel> getAllCryptocurrencies() {
        List<Cryptocurrency> cryptocurrencies = this.cryptocurrencyRepository.findAll();
        return List.of(this.modelMapper.map(cryptocurrencies, CryptocurrencyServiceModel.class));
    }

    @Override
    public CryptocurrencyServiceModel registerCryptocurrency(CryptocurrencyServiceModel cryptocurrencyServiceModel, String username) {
        User user=this.modelMapper.map(this.userService.getUserByUsername(username),User.class);

        Cryptocurrency cryptocurrency=this.modelMapper.map(cryptocurrencyServiceModel,Cryptocurrency.class);
        cryptocurrency.setAuthor(user);
        cryptocurrency.setAddedOn(LocalDateTime.now(ZoneId.systemDefault()));
        this.cryptocurrencyRepository.save(cryptocurrency);
        return this.modelMapper.map(cryptocurrency,CryptocurrencyServiceModel.class);
    }

    @Override
    public CryptocurrencyServiceModel getCryptocurrencyByName(String name) {
        return this.modelMapper.map(
                this.cryptocurrencyRepository.findByTitle(name).orElseThrow(() -> new CryptocurrencyNotFoundException("Cryptocurrency is not found.")),
                CryptocurrencyServiceModel.class);
    }


    @Override
    public void deleteById(String id) {
        this.cryptocurrencyRepository.deleteById(id);
    }
}
