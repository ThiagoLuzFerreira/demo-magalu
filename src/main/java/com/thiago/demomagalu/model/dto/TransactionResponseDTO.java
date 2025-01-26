package com.thiago.demomagalu.model.dto;

public class TransactionResponseDTO {

    private String operationResult;
    private String oracleTransactionId;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(String operationResult, String oracleTransactionId) {
        this.operationResult = operationResult;
        this.oracleTransactionId = oracleTransactionId;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOracleTransactionId() {
        return oracleTransactionId;
    }

    public void setOracleTransactionId(String oracleTransactionId) {
        this.oracleTransactionId = oracleTransactionId;
    }
}
