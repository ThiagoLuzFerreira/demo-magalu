package com.thiago.demomagalu.repository;

import com.thiago.demomagalu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
