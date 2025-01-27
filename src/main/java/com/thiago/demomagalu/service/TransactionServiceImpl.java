package com.thiago.demomagalu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.demomagalu.exception.handler.BusinessException;
import com.thiago.demomagalu.exception.handler.DataIntegrityViolationException;
import com.thiago.demomagalu.exception.handler.ResourceNotFoundException;
import com.thiago.demomagalu.model.AuditLog;
import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.model.dto.TransactionResponseDTO;
import com.thiago.demomagalu.producer.TransactionProducer;
import com.thiago.demomagalu.repository.AuditLogRepository;
import com.thiago.demomagalu.repository.TransactionRepository;
import com.thiago.demomagalu.webclient.OracleEbsClient;
import com.thiago.demomagalu.webclient.dto.OracleEbsResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.thiago.demomagalu.mapper.GenericModelMapper.parseObject;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final OracleEbsClient client;
    private final TransactionProducer producer;
    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, OracleEbsClient client, TransactionProducer producer, AuditLogRepository auditLogRepository, ObjectMapper objectMapper) {
        this.transactionRepository = transactionRepository;
        this.client = client;
        this.producer = producer;
        this.auditLogRepository = auditLogRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public TransactionResponseDTO save(TransactionRequestDTO request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Optional<Transaction> transactionAccount = transactionRepository.findByAccount(request.getAccount());
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
        Transaction savedTransaction = transactionRepository.save(transaction);
        producer.publishTransactionMessage(savedTransaction);
        TransactionResponseDTO response = parseObject(savedTransaction, TransactionResponseDTO.class);
        persistAuditLog("/api/v1/transactions", "POST", request, response);

        return response;
    }

    @Override
    public TransactionRequestDTO findByOracleTransactionId(String transactionId) {
        Transaction transaction = transactionRepository.findByOracleTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado para transação Oracle EBS com o identificador ".concat(transactionId)));
        return parseObject(transaction, TransactionRequestDTO.class);
    }

    private void persistAuditLog(String endpoint, String httpMethod, Object request, Object response) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            String responseJson = objectMapper.writeValueAsString(response);

            AuditLog auditLog = new AuditLog(endpoint, httpMethod, requestJson, responseJson, LocalDateTime.now());
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao persistir log de auditoria", e);
        }
    }
}
