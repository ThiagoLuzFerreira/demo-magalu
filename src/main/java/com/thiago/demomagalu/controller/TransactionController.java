package com.thiago.demomagalu.controller;

import com.thiago.demomagalu.model.Transaction;
import com.thiago.demomagalu.model.dto.TransactionRequestDTO;
import com.thiago.demomagalu.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionRequestDTO> createTransaction(@RequestBody TransactionRequestDTO request){
        return ResponseEntity.ok().body(service.save(request));
    }
}
