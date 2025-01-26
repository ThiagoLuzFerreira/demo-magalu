package com.thiago.demomagalu.service;

import com.thiago.demomagalu.model.dto.TransactionRequestDTO;

public interface TransactionService {

    TransactionRequestDTO save(TransactionRequestDTO request);
}
