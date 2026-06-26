package com.movieBookV2.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


//JwtUtil.java
@Component
public class JwtUtil {

 @Value("${jwt.secret}")
 private String secret;

 @Value("${jwt.expiration-ms}")
 private long expirationMs;

 private SecretKey getSigningKey() {
     return Keys.hmacShaKeyFor(secret.getBytes());
 }

 public String generateToken(String email, String role) {
     Map<String, Object> claims = new HashMap<>();
     claims.put("role", role);

     return Jwts.builder()
             .claims(claims)
             .subject(email)
             .issuedAt(new Date())
             .expiration(new Date(System.currentTimeMillis() + expirationMs))
             .signWith(getSigningKey())
             .compact();
 }

 public String extractEmail(String token) {
     return extractAllClaims(token).getSubject();
 }

 public boolean isTokenExpired(String token) {
     return extractAllClaims(token).getExpiration().before(new Date());
 }

 public boolean isTokenValid(String token, String email) {
     return email.equals(extractEmail(token)) && !isTokenExpired(token);
 }

 private Claims extractAllClaims(String token) {
     return Jwts.parser()
             .verifyWith(getSigningKey())
             .build()
             .parseSignedClaims(token)
             .getPayload();
 }
}