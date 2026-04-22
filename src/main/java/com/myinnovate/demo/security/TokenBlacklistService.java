package com.myinnovate.demo.security;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {

    private final Map<String, Long> blacklisted = new ConcurrentHashMap<>();

    public void blacklist(String token, Date expiresAt) {
        blacklisted.put(token, expiresAt.getTime());
        cleanupExpired();
    }

    public boolean isBlacklisted(String token) {
        cleanupExpired();
        return blacklisted.containsKey(token);
    }

    private void cleanupExpired() {
        long now = System.currentTimeMillis();
        blacklisted.entrySet().removeIf(entry -> entry.getValue() <= now);
    }
}
