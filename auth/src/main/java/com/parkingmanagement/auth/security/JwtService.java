package com.parkingmanagement.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final Map<String, String> refreshTokenStore = new ConcurrentHashMap<>();

    public JwtService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expiration)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String authenticate(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return generateToken((UserDetails) authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidas");
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String refreshAccessToken(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            String username = extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return generateToken(userDetails);
        } else {
            throw new BadCredentialsException("Refresh Token inválido");
        }
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String refreshToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        refreshTokenStore.put(userDetails.getUsername(), refreshToken);
        return refreshToken;
    }

    public boolean validateRefreshToken(String token) {
        try {
            String username = extractUsername(token);
            return token.equals(refreshTokenStore.get(username)) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }
}
