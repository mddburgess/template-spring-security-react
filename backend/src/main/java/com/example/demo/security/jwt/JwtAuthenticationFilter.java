package com.example.demo.security.jwt;

import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.security.data.UserCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final Key jwtSecretKey;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, Key jwtSecretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            var userCredential = new ObjectMapper()
                    .readValue(request.getInputStream(), UserCredential.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userCredential.getUsername(), userCredential.getPassword()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) {
        var scope = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());
        var token = Jwts.builder()
                .setSubject(authResult.getName())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, MINUTES)))
                .claim("scope", scope)
                .signWith(jwtSecretKey)
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
