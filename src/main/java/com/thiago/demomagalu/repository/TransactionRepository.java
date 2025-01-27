package com.thiago.demomagalu.repository;

import com.thiago.demomagalu.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByOracleTransactionId(String oracleTransactionId);

    Optional<Transaction> findByAccount(String account);
}
