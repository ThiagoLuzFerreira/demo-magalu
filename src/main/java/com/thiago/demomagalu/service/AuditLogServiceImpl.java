package com.thiago.demomagalu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.demomagalu.model.AuditLog;
import com.thiago.demomagalu.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogServiceImpl implements AuditLogService{

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, ObjectMapper objectMapper) {
        this.auditLogRepository = auditLogRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(String endpoint, String httpMethod, Object request, Object response) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            String responseJson = objectMapper.writeValueAsString(response);

            AuditLog auditLog = new AuditLog(endpoint, httpMethod, requestJson, responseJson, LocalDateTime.now());
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao persistir log de auditoria", e);
        }
    }
}
