package com.thiago.demomagalu.repository;

import com.thiago.demomagalu.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
