package com.thiago.demomagalu.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequestDTO {

    private String account;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String costCenter;

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(String account, BigDecimal amount, LocalDate transactionDate, String costCenter) {
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.costCenter = costCenter;
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
}
