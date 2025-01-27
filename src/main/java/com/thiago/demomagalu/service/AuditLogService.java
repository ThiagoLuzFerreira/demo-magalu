package com.thiago.demomagalu.service;

import com.thiago.demomagalu.model.AuditLog;

public interface AuditLogService {

    void save(String endpoint, String httpMethod, Object request, Object response);
}
