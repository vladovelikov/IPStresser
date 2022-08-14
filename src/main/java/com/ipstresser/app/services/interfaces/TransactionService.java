package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.TransactionServiceModel;

public interface TransactionService {

    void saveTransaction(TransactionServiceModel transactionServiceModel);
}
