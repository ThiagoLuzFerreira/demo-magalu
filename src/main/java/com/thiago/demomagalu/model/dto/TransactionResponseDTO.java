package com.thiago.demomagalu.model.dto;

public class TransactionResponseDTO {

    private String status;
    private String oracleTransactionId;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(String status, String oracleTransactionId) {
        this.status = status;
        this.oracleTransactionId = oracleTransactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOracleTransactionId() {
        return oracleTransactionId;
    }

    public void setOracleTransactionId(String oracleTransactionId) {
        this.oracleTransactionId = oracleTransactionId;
    }
}
