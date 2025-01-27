package com.thiago.demomagalu.service;

import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.repository.TransactionRepository;
import com.thiago.demomagalu.webclient.OracleEbsClient;
import com.thiago.demomagalu.webclient.dto.OracleEbsResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.thiago.demomagalu.mapper.GenericModelMapper.parseObject;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final OracleEbsClient client;

    public TransactionServiceImpl(TransactionRepository repository, OracleEbsClient client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    public TransactionRequestDTO save(TransactionRequestDTO request) {

        Transaction transaction = parseObject(request, Transaction.class);
        ResponseEntity<OracleEbsResponseDTO> ebsTransaction = client.getOracleEbsTransaction();
        transaction.setTransactionDate(LocalDate.parse(request.getTransactionDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        transaction.setOracleTransactionId(ebsTransaction.getBody().getNumeroTransacao().toString());
        transaction.setStatus(ebsTransaction.getBody().getStatusTransacao().name());
        Transaction savedTransaction = repository.save(transaction);

        return parseObject(savedTransaction, TransactionRequestDTO.class);
    }

    @Override
    public TransactionRequestDTO findByOracleTransactionId(String transactionId) {
        Transaction transaction = repository.findByOracleTransactionId(transactionId).orElse(null);
        return parseObject(transaction, TransactionRequestDTO.class);
    }

}
