package com.thiago.demomagalu.service;

import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionRequestDTO;

import java.util.Optional;

public interface TransactionService {

    TransactionRequestDTO save(TransactionRequestDTO request);

    TransactionRequestDTO findByOracleTransactionId(String oracleTransactionId);
}
