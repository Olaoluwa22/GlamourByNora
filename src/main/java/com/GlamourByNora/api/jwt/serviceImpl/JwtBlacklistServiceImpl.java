package com.GlamourByNora.api.jwt.serviceImpl;

import com.GlamourByNora.api.jwt.service.JwtBlacklistService;
import com.GlamourByNora.api.jwt.service.JwtTokenService;
import com.GlamourByNora.api.model.JwtBlacklist;
import com.GlamourByNora.api.repository.JwtBlacklistRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;
    @Override
    public void invalidateToken(HttpServletRequest request) {
        String token = jwtTokenService.resolveToken(request);
        JwtBlacklist blacklist = new JwtBlacklist();
        blacklist.setToken(token);
        blacklist.setInvalidatedAt(new Date());
        jwtBlacklistRepository.save(blacklist);
    }
    @Override
    public boolean isTokenNotBlacklisted(String token) {
        Optional<JwtBlacklist> optionalToken = jwtBlacklistRepository.findTokenBlacklistByToken(token);
        return optionalToken.isEmpty();
    }
    @Override
    @Scheduled(fixedRate = 3*24*60*60*1000) //3days interval for a scheduled cleanup
    public void cleanupBlacklistedToken() {
        jwtBlacklistRepository.deleteAll();
    }
}