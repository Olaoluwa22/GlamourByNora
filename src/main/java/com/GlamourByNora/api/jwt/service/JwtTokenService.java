package com.GlamourByNora.api.jwt.service;

import com.GlamourByNora.api.serviceImpl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
@Component
public class JwtTokenService {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private static final String SECRETKEY = "456464DVSVGG23653RJHFKG3RGVFEW653467GIRAFFE34TIFF4463737823TFGFMG";

    public String createToken(String email, List<String> roles){
        Claims claims = Jwts.claims();
        claims.setSubject(email);
        claims.put("roles", roles);
        Date issuedDate = new Date();
        Date expiresAt = new Date(issuedDate.getTime() + (1000 * 60 * 10));
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expiresAt)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
    public String getUsername(String token){
        return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean isTokenExpired(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }
    public String resolveToken(HttpServletRequest request) {
       String bearerToken = request.getHeader("Authorization");
       if(bearerToken != null && bearerToken.startsWith("Bearer")){
           return bearerToken.substring(7);
       }
       return null;
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public void expireThisToken(String token){
        Jws<Claims> parsedClaimsJws = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
        Claims claim = parsedClaimsJws.getBody();
        claim.setExpiration(new Date());
        Jwts.builder()
                .setClaims(claim)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}