package com.GlamourByNora.api.jwt.service;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtBlacklistService {
    public void invalidateToken(HttpServletRequest request);
    public boolean isTokenNotBlacklisted(String token);
    public void cleanupBlacklistedToken();
}
