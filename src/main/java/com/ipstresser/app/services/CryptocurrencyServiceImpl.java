package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Cryptocurrency;
import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;
import com.ipstresser.app.exceptions.CryptocurrencyNotFoundException;
import com.ipstresser.app.repositories.CryptocurrencyRepository;
import com.ipstresser.app.services.interfaces.CryptocurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CryptocurrencyServiceImpl(CryptocurrencyRepository cryptocurrencyRepository, ModelMapper modelMapper) {
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CryptocurrencyServiceModel> getAllCryptocurrencies() {
        List<Cryptocurrency> cryptocurrencies = this.cryptocurrencyRepository.findAll();
        return List.of(this.modelMapper.map(cryptocurrencies, CryptocurrencyServiceModel.class));
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
