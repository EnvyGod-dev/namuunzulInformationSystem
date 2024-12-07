package org.birdnestbackend.userauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.Collection;
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                        .pathMatchers("/api/v1/auth/assign-role").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        return new ReactiveJwtAuthenticationConverter();
    }

    private static class ReactiveJwtAuthenticationConverter implements org.springframework.core.convert.converter.Converter<Jwt, Mono<AbstractAuthenticationToken>> {
        private final JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        ReactiveJwtAuthenticationConverter() {
            // Set authority prefix to match Spring Security conventions
            authoritiesConverter.setAuthorityPrefix("ROLE_");
        }

        @Override
        public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
            Collection<org.springframework.security.core.GrantedAuthority> authorities = authoritiesConverter.convert(jwt);
            return Mono.just(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(jwt.getSubject(), jwt, authorities));
        }
    }
}
