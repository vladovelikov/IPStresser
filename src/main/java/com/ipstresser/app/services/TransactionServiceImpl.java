package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Transaction;
import com.ipstresser.app.domain.models.service.TransactionServiceModel;
import com.ipstresser.app.repositories.TransactionRepository;
import com.ipstresser.app.services.interfaces.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveTransaction(TransactionServiceModel transactionServiceModel) {
        this.transactionRepository.save(
                this.modelMapper.map(transactionServiceModel, Transaction.class)
        );
    }
}
