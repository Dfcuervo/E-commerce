package com.ecommerce.orderservice.infrastructure.adapter.controller;

import com.ecommerce.orderservice.application.dto.AuthRequestDto;
import com.ecommerce.orderservice.application.dto.AuthResponseDto;
import com.ecommerce.orderservice.infrastructure.config.ApiResponse;
import com.ecommerce.orderservice.infrastructure.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@RequestBody AuthRequestDto request) {
        log.info("[POST/login] Searching user");
        if ("admin".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
            String token = jwtTokenProvider.generateToken(request.getUsername());
            AuthResponseDto responseDto = new AuthResponseDto(token);
            log.info("[POST/login] Token generated successfully");
            return ResponseEntity.ok(new ApiResponse<>(responseDto, "success", "Successful login"));
        } else {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(null, "error", "Invalid credentials"));
        }
    }
}
