package com.GlamourByNora.api.jwt;

import com.GlamourByNora.api.serviceImpl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;
import java.util.List;

public class JwtService {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
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
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean isTokenExpired(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
}