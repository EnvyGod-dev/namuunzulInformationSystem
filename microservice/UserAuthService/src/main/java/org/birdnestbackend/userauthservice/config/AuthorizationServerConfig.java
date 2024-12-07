package org.birdnestbackend.userauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class AuthorizationServerConfig {

    @Bean
    public SecurityWebFilterChain authorizationServerSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(this::configureAuthorization)
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt); // Updated to use the new JWT method

        return http.build();
    }

    private void configureAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authorize) {
        authorize
                .pathMatchers("/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/validate").permitAll() // Public endpoints
                .pathMatchers("/api/v1/auth/assign-role").hasRole("ADMIN") // Admin-only endpoint
                .anyExchange().authenticated(); // All other endpoints require authentication
    }
}
