package com.myinnovate.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myinnovate.demo.controller.dto.LoginRequest;
import com.myinnovate.demo.entity.User;
import com.myinnovate.demo.repo.UserRepo;
import com.myinnovate.demo.security.JwtService;
import com.myinnovate.demo.security.TokenBlacklistService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(
        UserRepo userRepo,
        JwtService jwtService,
        TokenBlacklistService tokenBlacklistService
    ) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        if (request.getName() == null || request.getPwd() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "name and pwd are required"));
        }

        User user = userRepo.findByName(request.getName()).orElse(null);
        if (user == null || !user.getPwd().equals(request.getPwd())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "invalid name or pwd"));
        }

        String token = jwtService.generateToken(user.getId(), user.getName());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("tokenType", "Bearer");
        result.put("userId", user.getId());
        result.put("name", user.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
        @RequestHeader(name = "Authorization", required = false) String authorization
    ) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("message", "missing bearer token"));
        }

        String token = authorization.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.badRequest().body(Map.of("message", "invalid token"));
        }

        tokenBlacklistService.blacklist(token, jwtService.extractExpiration(token));
        return ResponseEntity.ok(Map.of("message", "logout success"));
    }
}
