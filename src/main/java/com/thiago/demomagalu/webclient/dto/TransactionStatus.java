package com.thiago.demomagalu.webclient.dto;

public enum TransactionStatus {

    SUCESSO(0, "Transação realizada com sucesso"), FALHA(1, "Falha ao realizar a transação");

    private Integer codigo;
    private String descricao;

    TransactionStatus(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
