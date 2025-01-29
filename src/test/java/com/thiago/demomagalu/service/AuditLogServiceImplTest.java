package com.thiago.demomagalu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.demomagalu.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceImplTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuditLogServiceImpl auditLogService;

    private final String endpoint = "/api/test";
    private final String httpMethod = "POST";
    private final Object request = new Object();
    private final Object response = new Object();
    private final String requestJson = "{\"key\":\"value\"}";
    private final String responseJson = "{\"result\":\"success\"}";

    @Test
    void save_ShouldPersistAuditLog_WhenSerializationSucceeds() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(request)).thenReturn(requestJson);
        when(objectMapper.writeValueAsString(response)).thenReturn(responseJson);

        auditLogService.save(endpoint, httpMethod, request, response);

        verify(auditLogRepository, times(1)).save(argThat(auditLog ->
                auditLog.getEndpoint().equals(endpoint) &&
                        auditLog.getHttpMethod().equals(httpMethod) &&
                        auditLog.getRequestPayload().equals(requestJson) &&
                        auditLog.getResponsePayload().equals(responseJson) &&
                        auditLog.getTimestamp() != null
        ));
    }


    @Test
    void save_ShouldThrowRuntimeException_WhenSerializationFails() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Serialization error") {});

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            auditLogService.save(endpoint, httpMethod, request, response);
        });

        assertTrue(exception.getMessage().contains("Erro ao persistir log de auditoria"));
        verify(auditLogRepository, never()).save(any());
    }
}