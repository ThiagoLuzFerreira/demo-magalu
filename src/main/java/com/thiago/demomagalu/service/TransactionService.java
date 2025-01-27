package com.thiago.demomagalu.service;

import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.model.dto.TransactionResponseDTO;

import java.util.Optional;

public interface TransactionService {

    TransactionResponseDTO save(TransactionRequestDTO request);

    TransactionRequestDTO findByOracleTransactionId(String oracleTransactionId);
}
