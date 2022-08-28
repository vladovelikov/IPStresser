package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Transaction;
import com.ipstresser.app.domain.models.service.TransactionServiceModel;
import com.ipstresser.app.repositories.TransactionRepository;
import com.ipstresser.app.services.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void saveTransactionShouldSaveUpcomingTransaction() {
        Transaction transaction = new Transaction();
        TransactionServiceModel transactionServiceModel = new TransactionServiceModel();
        Mockito.when(this.modelMapper.map(transactionServiceModel, Transaction.class)).thenReturn(transaction);
        transactionService.saveTransaction(transactionServiceModel);
        Mockito.verify(transactionRepository).save(transaction);
    }
}
