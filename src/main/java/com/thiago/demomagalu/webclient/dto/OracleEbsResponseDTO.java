package com.thiago.demomagalu.webclient.dto;

import java.util.UUID;

public class OracleEbsResponseDTO {

    private UUID numeroTransacao;
    private TransactionStatus statusTransacao;

    public OracleEbsResponseDTO() {
    }

    public OracleEbsResponseDTO(UUID numeroTransacao, TransactionStatus statusTransacao) {
        this.numeroTransacao = numeroTransacao;
        this.statusTransacao = statusTransacao;
    }

    public UUID getNumeroTransacao() {
        return numeroTransacao;
    }

    public void setNumeroTransacao(UUID numeroTransacao) {
        this.numeroTransacao = numeroTransacao;
    }

    public TransactionStatus getStatusTransacao() {
        return statusTransacao;
    }

    public void setStatusTransacao(TransactionStatus statusTransacao) {
        this.statusTransacao = statusTransacao;
    }
}
