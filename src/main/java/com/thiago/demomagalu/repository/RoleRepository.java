package com.thiago.demomagalu.repository;

import com.thiago.demomagalu.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
