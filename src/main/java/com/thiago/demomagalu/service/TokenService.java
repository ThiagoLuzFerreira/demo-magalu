package com.thiago.demomagalu.service;

import com.thiago.demomagalu.controller.dto.LoginRequestDTO;
import com.thiago.demomagalu.controller.dto.LoginResponseDTO;

public interface TokenService {

    LoginResponseDTO authenticate(LoginRequestDTO request);
}
