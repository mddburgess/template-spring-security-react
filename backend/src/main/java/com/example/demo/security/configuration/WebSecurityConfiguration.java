package com.example.demo.security.configuration;

import java.security.Key;

import com.example.demo.security.jwt.JwtAuthenticationEntryPoint;
import com.example.demo.security.jwt.JwtAuthenticationFilter;
import com.example.demo.security.jwt.JwtAuthorizationFilter;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();
        http.csrf()
                .disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtSecretKey));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecretKey));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(
                new User("user", "{noop}password", createAuthorityList("notes:read")),
                new User("admin", "{noop}password", createAuthorityList("notes:read", "notes:write"))
        ));
        return daoAuthenticationProvider;
    }
}
