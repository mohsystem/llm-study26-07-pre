package com.um.springbootprojstructure.config;

import com.um.springbootprojstructure.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/refresh", "/api/auth/logout").permitAll()
                        .requestMatchers("/api/prompts/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .headers(h -> h.frameOptions(frame -> frame.disable())) // H2 console
                .addFilterBefore(accessJwtFilter(jwtService),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    OncePerRequestFilter accessJwtFilter(JwtService jwtService) {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {

                String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (auth != null && auth.startsWith("Bearer ")) {
                    String token = auth.substring("Bearer ".length());
                    try {
                        Jws<Claims> jws = jwtService.parseAndValidate(token);
                        jwtService.assertTokenType(jws, "access");

                        String publicRef = jws.getPayload().getSubject();
                        String role = jws.getPayload().get("role", String.class);

                        var authorities = role == null
                                ? List.<SimpleGrantedAuthority>of()
                                : List.of(new SimpleGrantedAuthority("ROLE_" + role));

                        var authentication = new UsernamePasswordAuthenticationToken(publicRef, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    } catch (JwtException ex) {
                        SecurityContextHolder.clearContext();
                    }
                }

                filterChain.doFilter(request, response);
            }
        };
    }
}
