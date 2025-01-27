package com.thiago.demomagalu.repository;

import com.thiago.demomagalu.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
