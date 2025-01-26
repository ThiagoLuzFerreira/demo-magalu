package com.thiago.demomagalu.webclient.dto;

import java.util.UUID;

public class OracleEbsResponseDTO {

    private UUID numeroTransacao;
    private StatusTransacao statusTransacao;

    public OracleEbsResponseDTO() {
    }

    public OracleEbsResponseDTO(UUID numeroTransacao, StatusTransacao statusTransacao) {
        this.numeroTransacao = numeroTransacao;
        this.statusTransacao = statusTransacao;
    }

    public UUID getNumeroTransacao() {
        return numeroTransacao;
    }

    public void setNumeroTransacao(UUID numeroTransacao) {
        this.numeroTransacao = numeroTransacao;
    }

    public StatusTransacao getStatusTransacao() {
        return statusTransacao;
    }

    public void setStatusTransacao(StatusTransacao statusTransacao) {
        this.statusTransacao = statusTransacao;
    }
}
