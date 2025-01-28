package com.thiago.demomagalu.controller;

import com.thiago.demomagalu.controller.dto.LoginRequest;
import com.thiago.demomagalu.controller.dto.LoginResponse;
import com.thiago.demomagalu.exception.handler.BadCredentialsException;
import com.thiago.demomagalu.model.User;
import com.thiago.demomagalu.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/token")
    private ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request){
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

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
