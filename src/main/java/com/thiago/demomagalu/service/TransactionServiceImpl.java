package com.thiago.demomagalu.service;

import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.thiago.demomagalu.mapper.GenericModelMapper.parseObject;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionRequestDTO save(TransactionRequestDTO request) {

        Transaction transaction = parseObject(request, Transaction.class);
        transaction.setOracleTransactionId(UUID.randomUUID().toString());
        transaction.setStatus("SUCESSO");
        Transaction savedTransaction = repository.save(transaction);

        return parseObject(savedTransaction, TransactionRequestDTO.class);
    }

}
