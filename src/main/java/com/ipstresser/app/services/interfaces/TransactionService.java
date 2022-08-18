package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.TransactionServiceModel;

public interface TransactionService {

    boolean hasUserTransactions(String username);
    void saveTransaction(TransactionServiceModel transactionServiceModel);
}
