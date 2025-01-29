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
import com.thiago.demomagalu.webclient.dto.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private OracleEbsClient client;

    @Mock
    private TransactionProducer producer;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionRequestDTO request;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        request = new TransactionRequestDTO();
        request.setAccount("123456");
        request.setTransactionDate("29-01-2025");

        transaction = new Transaction();
        transaction.setAccount(request.getAccount());
        transaction.setTransactionDate(LocalDate.now());
    }

    @Test
    void shouldSaveTransactionSuccessfully() {
        OracleEbsResponseDTO ebsResponse = new OracleEbsResponseDTO();
        ebsResponse.setNumeroTransacao(UUID.randomUUID());
        ebsResponse.setStatusTransacao(TransactionStatus.SUCESSO);

        when(transactionRepository.findByAccount(request.getAccount())).thenReturn(Optional.empty());
        when(client.getOracleEbsTransaction()).thenReturn(ResponseEntity.ok(ebsResponse));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponseDTO response = transactionService.save(request);

        assertNotNull(response);
        verify(producer).publishTransactionMessage(any(Transaction.class));
        verify(auditLogService).save(any(), any(), any(), any());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenAccountAlreadyExists() {
        when(transactionRepository.findByAccount(request.getAccount())).thenReturn(Optional.of(transaction));

        assertThrows(DataIntegrityViolationException.class, () -> transactionService.save(request));
    }

    @Test
    void shouldThrowBusinessExceptionWhenTransactionDateIsFuture() {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setAccount("123456");
        request.setTransactionDate("30-01-2025");

        assertThrows(BusinessException.class, () -> transactionService.save(request));
    }

    @Test
    void shouldFindByOracleTransactionIdSuccessfully() {
        when(transactionRepository.findByOracleTransactionId("1001"))
                .thenReturn(Optional.of(transaction));

        TransactionRequestDTO result = transactionService.findByOracleTransactionId("1001");
        assertNotNull(result);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTransactionIdNotFound() {
        when(transactionRepository.findByOracleTransactionId("9999"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.findByOracleTransactionId("9999"));
    }
}