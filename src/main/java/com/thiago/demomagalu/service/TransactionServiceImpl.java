package com.thiago.demomagalu.service;

import com.thiago.demomagalu.exception.handler.BusinessException;
import com.thiago.demomagalu.exception.handler.DataIntegrityViolationException;
import com.thiago.demomagalu.exception.handler.ResourceNotFoundException;
import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.model.dto.TransactionResponseDTO;
import com.thiago.demomagalu.producer.TransactionProducer;
import com.thiago.demomagalu.repository.TransactionRepository;
import com.thiago.demomagalu.webclient.OracleEbsClient;
import com.thiago.demomagalu.webclient.dto.OracleEbsResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.thiago.demomagalu.mapper.GenericModelMapper.parseObject;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final OracleEbsClient client;
    private final TransactionProducer producer;

    public TransactionServiceImpl(TransactionRepository repository, OracleEbsClient client, TransactionProducer producer) {
        this.repository = repository;
        this.client = client;
        this.producer = producer;
    }

    @Override
    public TransactionResponseDTO save(TransactionRequestDTO request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Optional<Transaction> transactionAccount = repository.findByAccount(request.getAccount());
        if (transactionAccount.isPresent() && request.getAccount().equals(transactionAccount.get().getAccount())){
            throw new DataIntegrityViolationException("Account already registred");
        } else if (LocalDate.parse(request.getTransactionDate(), formatter).isAfter(LocalDate.now())) {
            throw new BusinessException("Transaction date cannot be greater than the current date ");
        }

        Transaction transaction = parseObject(request, Transaction.class);
        ResponseEntity<OracleEbsResponseDTO> ebsTransaction = client.getOracleEbsTransaction();
        transaction.setTransactionDate(LocalDate.parse(request.getTransactionDate(), formatter));
        transaction.setOracleTransactionId(ebsTransaction.getBody().getNumeroTransacao().toString());
        transaction.setStatus(ebsTransaction.getBody().getStatusTransacao().name());
        Transaction savedTransaction = repository.save(transaction);
        producer.publishTransactionMessage(savedTransaction);
        return parseObject(savedTransaction, TransactionResponseDTO.class);
    }

    @Override
    public TransactionRequestDTO findByOracleTransactionId(String transactionId) {
        Transaction transaction = repository.findByOracleTransactionId(transactionId).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado para transação Oracle EBS com o identificador ".concat(transactionId)));
        return parseObject(transaction, TransactionRequestDTO.class);
    }

}
