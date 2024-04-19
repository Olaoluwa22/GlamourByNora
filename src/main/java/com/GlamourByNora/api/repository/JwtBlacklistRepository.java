package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    Optional<JwtBlacklist> findTokenBlacklistByToken(String token);
}