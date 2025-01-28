package com.thiago.demomagalu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;
    @Column(unique = true)
    private String account;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String costCenter;
    private String oracleTransactionId;
    private String status;

    public Transaction() {
    }

    public Transaction(Long id, String account, BigDecimal amount, LocalDate transactionDate, String costCenter, String oracleTransactionId, String status) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.costCenter = costCenter;
        this.oracleTransactionId = oracleTransactionId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getOracleTransactionId() {
        return oracleTransactionId;
    }

    public void setOracleTransactionId(String oracleTransactionId) {
        this.oracleTransactionId = oracleTransactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}