package com.thiago.demomagalu.service;

import com.thiago.demomagalu.controller.dto.LoginRequestDTO;
import com.thiago.demomagalu.controller.dto.LoginResponseDTO;
import com.thiago.demomagalu.exception.handler.BadCredentialsException;
import com.thiago.demomagalu.model.User;
import com.thiago.demomagalu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private LoginRequestDTO validRequest;
    private LoginRequestDTO invalidUsernameRequest;
    private LoginRequestDTO invalidPasswordRequest;
    private User mockUser;
    private String mockJwtToken;

    @BeforeEach
    void setUp() {
        validRequest = new LoginRequestDTO("validUser", "correctPassword");
        invalidUsernameRequest = new LoginRequestDTO("invalidUser", "password");
        invalidPasswordRequest = new LoginRequestDTO("validUser", "wrongPassword");

        mockUser = new User();
        mockUser.setUserId(UUID.randomUUID());
        mockUser.setUsername("validUser");
        mockUser.setPassword("$2a$10$dummyHashedPassword");

        mockJwtToken = "mock.jwt.token";

        lenient().when(userRepository.findByUsername("validUser")).thenReturn(Optional.of(mockUser));
        lenient().when(userRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());
        lenient().when(bCryptPasswordEncoder.matches("correctPassword", mockUser.getPassword())).thenReturn(true);
        lenient().when(bCryptPasswordEncoder.matches("wrongPassword", mockUser.getPassword())).thenReturn(false);

        var claims = JwtClaimsSet.builder()
                .issuer("demo-magalu")
                .subject(mockUser.getUserId().toString())
                .expiresAt(Instant.now().plusSeconds(300L))
                .issuedAt(Instant.now())
                .build();

        Jwt mockJwt = Jwt.withTokenValue(mockJwtToken)
                .header("alg", "HS256")
                .claims(claimsMap -> claimsMap.putAll(claims.getClaims())) // ✅ FIXED
                .build();

        lenient().when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockJwt);
    }

    @Test
    void authenticate_ShouldReturnJwtToken_WhenCredentialsAreValid() {
        LoginResponseDTO response = tokenService.authenticate(validRequest);

        assertNotNull(response);
        assertEquals(mockJwtToken, response.accessToken());
        assertEquals(300L, response.expiresIn());

        verify(userRepository, times(1)).findByUsername("validUser");
        verify(bCryptPasswordEncoder, times(1)).matches("correctPassword", mockUser.getPassword());
        verify(jwtEncoder, times(1)).encode(any(JwtEncoderParameters.class));
    }

    @Test
    void authenticate_ShouldThrowBadCredentialsException_WhenUsernameIsInvalid() {
        assertThrows(BadCredentialsException.class, () -> tokenService.authenticate(invalidUsernameRequest));

        verify(userRepository, times(1)).findByUsername("invalidUser");
        verifyNoMoreInteractions(bCryptPasswordEncoder);
        verifyNoMoreInteractions(jwtEncoder);
    }

    @Test
    void authenticate_ShouldThrowBadCredentialsException_WhenPasswordIsInvalid() {
        assertThrows(BadCredentialsException.class, () -> tokenService.authenticate(invalidPasswordRequest));

        verify(userRepository, times(1)).findByUsername("validUser");
        verify(bCryptPasswordEncoder, times(1)).matches("wrongPassword", mockUser.getPassword());
        verifyNoMoreInteractions(jwtEncoder);
    }
}