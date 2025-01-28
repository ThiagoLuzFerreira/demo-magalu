package com.thiago.demomagalu.controller;

import com.thiago.demomagalu.controller.dto.LoginRequestDTO;
import com.thiago.demomagalu.controller.dto.LoginResponseDTO;
import com.thiago.demomagalu.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    private ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(tokenService.authenticate(request));
    }
}
