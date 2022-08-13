package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.CryptocurrencyServiceModel;

import java.util.List;

public interface CryptocurrencyService {

    List<CryptocurrencyServiceModel> getAllCryptocurrencies();

    void deleteById(String id);
}
