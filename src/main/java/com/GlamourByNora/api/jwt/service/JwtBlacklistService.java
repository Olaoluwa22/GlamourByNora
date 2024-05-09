package com.GlamourByNora.api.jwt.service;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtBlacklistService {
     void invalidateToken(HttpServletRequest request);
     boolean isTokenNotBlacklisted(String token);
     void cleanupBlacklistedToken();
}
