package com.thiago.demomagalu.controller;

import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.model.dto.TransactionResponseDTO;
import com.thiago.demomagalu.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(TransactionController.class)  // Only tests the controller layer
class TransactionControllerTest {

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private TransactionService transactionService;  // Mock the service layer

    private TransactionRequestDTO request;
    private TransactionResponseDTO expectedResponse;

    @BeforeEach
    void setUp() {
        request = new TransactionRequestDTO();
        request.setAccount("someAccount");
        request.setAmount(new BigDecimal("100.00"));
        request.setTransactionDate("29-01-2025");
        request.setCostCenter("costCenter123");

        expectedResponse = new TransactionResponseDTO();
        expectedResponse.setStatus("Success");
        expectedResponse.setOracleTransactionId("12345");

    }

    @Test
    void createTransaction_ShouldReturnCreated() {
        when(transactionService.save(any(TransactionRequestDTO.class))).thenReturn(expectedResponse);

        ResponseEntity<TransactionResponseDTO> response = transactionController.createTransaction(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("12345", response.getBody().getOracleTransactionId());
        assertEquals("Success", response.getBody().getStatus());
    }

    @Test
    void getTransaction_ShouldReturnTransaction_WhenValidOracleTransactionId() {
        String oracleTransactionId = "oracle123"; // Mock oracle transaction ID
        when(transactionService.findByOracleTransactionId(oracleTransactionId)).thenReturn(request);

        ResponseEntity<TransactionRequestDTO> response = transactionController.getTransaction(oracleTransactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

}
