package com.ipstresser.app.web.controllers;

import com.ipstresser.app.domain.models.view.CryptocurrencyViewModel;
import com.ipstresser.app.services.interfaces.CryptocurrencyService;
import com.ipstresser.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {

    private final CryptocurrencyService cryptocurrencyService;
    private final ModelMapper modelMapper;

    @Autowired
    public CurrencyController(CryptocurrencyService cryptocurrencyService, ModelMapper modelMapper) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("All currencies")
    @GetMapping
    public String allCurrencies(Model model) {
        model.addAttribute("cryptos", List.of(this.modelMapper.map(this.cryptocurrencyService.getAllCryptocurrencies(), CryptocurrencyViewModel[].class)));
        return "currencies";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCryptocurrency(@PathVariable String id) {
        this.cryptocurrencyService.deleteById(id);
        return "redirect:/currencies";
    }


}
