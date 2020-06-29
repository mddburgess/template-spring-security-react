package com.example.demo.security.jwt;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static java.util.stream.Collectors.toList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final Key jwtSecretKey;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, Key jwtSecretKey) {
        super(authenticationManager);
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            var authentication = getAuthentication(header.substring(7));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            var username = claims.getSubject();
            if (username != null) {
                var scope = (List<String>) claims.get("scope", List.class);
                var authorities = scope.stream().map(SimpleGrantedAuthority::new).collect(toList());
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        } catch (JwtException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }
}
