package com.thiago.demomagalu.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    @NotNull(message = "Account cannot be null")
    private String account;
    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;
    @NotNull(message = "Transaction date cannot be null")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Transaction date must be in dd-MM-yyyy format")
    private String transactionDate;
    @NotNull(message = "Cost center cannot be null")
    private String costCenter;

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(String account, BigDecimal amount, String transactionDate, String costCenter) {
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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
}
