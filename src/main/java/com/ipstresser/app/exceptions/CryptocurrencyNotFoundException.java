package com.ipstresser.app.exceptions;

public class CryptocurrencyNotFoundException extends RuntimeException {

    public CryptocurrencyNotFoundException(String message) {
        super(message);
    }
}
