package com.fletrax.tracking.commonpackage.configuration.security;

import com.fletrax.tracking.commonpackage.utils.security.KeycloakRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

        @Bean
        public CorsWebFilter corsWebFilter() {
                CorsConfiguration corsConfig = new CorsConfiguration();
                // corsConfig.setAllowedOrigins(List.of("http://localhost:5432",
                // "http://localhost:9020",
                // "http://localhost:5173", "http://3.65.253.172:9020", "http://fletrax.test",
                // "http://63.176.57.186:5173"));
                corsConfig.setAllowedOrigins(List.of("*"));
                corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
                corsConfig.setExposedHeaders(List.of("Authorization"));
                // corsConfig.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfig);

                return new CorsWebFilter(source);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                var converter = new JwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/internal/**").permitAll()

                                .requestMatchers("/api/users/v3/api-docs").permitAll()
                                .requestMatchers("/api/devices/v3/api-docs").permitAll()
                                .requestMatchers("/api/intervals/v3/api-docs").permitAll()
                                .requestMatchers("/api/kafka/v3/api-docs").permitAll()
                                .requestMatchers("/api/statistics/v3/api-docs").permitAll()
                                .requestMatchers("/api/vehicles/v3/api-docs").permitAll()
                                .requestMatchers("/api/geofences/v3/api-docs").permitAll()
                                .requestMatchers("/api/drivers/v3/api-docs").permitAll()
                                .requestMatchers("/api/maintenances/v3/api-docs").permitAll()
                                .requestMatchers("/api/violations/v3/api-docs").permitAll()
                                .requestMatchers("/api/notifications/v3/api-docs").permitAll()
                                .requestMatchers("/api/documents/v3/api-docs").permitAll()

                                .requestMatchers("/api/kafka/download").permitAll()
                                .requestMatchers("/api/**").hasAnyRole("admin", "user", "fletrax_admin", "fletrax_user")
                                .anyRequest().authenticated())
                                .csrf(AbstractHttpConfigurer::disable)
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(converter)))
                                .cors(withDefaults());

                return http.build();
        }

}