package com.thiago.demomagalu.service;

import com.thiago.demomagalu.controller.dto.LoginRequest;
import com.thiago.demomagalu.controller.dto.LoginResponse;
import com.thiago.demomagalu.exception.handler.BadCredentialsException;
import com.thiago.demomagalu.model.User;
import com.thiago.demomagalu.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService{

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenServiceImpl(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.username());
        if(user.isEmpty() || !user.get().isLoginCorrect(request, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Username or password is incorrect");
        }

        Instant now = Instant.now();
        Long expiresIn = 300L;
        var claims = JwtClaimsSet.builder()
                .issuer("demo-magalu")
                .subject(user.get().getUserId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
    }
}
