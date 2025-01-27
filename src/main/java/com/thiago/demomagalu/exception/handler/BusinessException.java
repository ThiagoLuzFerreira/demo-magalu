package com.thiago.demomagalu.exception.handler;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
