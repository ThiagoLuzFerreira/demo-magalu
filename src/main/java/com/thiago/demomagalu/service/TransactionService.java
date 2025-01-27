package com.thiago.demomagalu.service;

import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.model.dto.TransactionResponseDTO;

public interface TransactionService {

    TransactionResponseDTO save(TransactionRequestDTO request);

    TransactionRequestDTO findByOracleTransactionId(String oracleTransactionId);
}
