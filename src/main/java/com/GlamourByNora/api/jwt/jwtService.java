package com.GlamourByNora.api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

import java.util.Base64;
import java.util.Date;
import java.util.List;

public class jwtService {
    private static String secretKey = "456464DVSVGG23653RJHFKG3RGVFEW653467GIRAFFE34TIFF4463737823TFGFMG";

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    public String createToken(String email, List<String> roles){
        Claims claims = Jwts.claims();
        claims.setSubject(email);
        claims.put("roles", roles);
        Date issuedDate = new Date();
        Date expiresAt = new Date(issuedDate.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String getUsername(String token){
        return null;
    }
    public boolean getTokenExpiration(String token){
        return false;
    }
    public boolean isTokenValid(String token){
        return false;
    }


}
