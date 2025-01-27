package com.thiago.demomagalu.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(LocalDateTime timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public ValidationError() {
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String field, String defaultMessage) {
        this.errors.add(new FieldMessage(field, defaultMessage));
    }
}
