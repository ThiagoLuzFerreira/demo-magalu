package com.thiago.demomagalu.service;

import com.thiago.demomagalu.controller.dto.LoginRequest;
import com.thiago.demomagalu.controller.dto.LoginResponse;

public interface TokenService {

    LoginResponse authenticate(LoginRequest request);
}
