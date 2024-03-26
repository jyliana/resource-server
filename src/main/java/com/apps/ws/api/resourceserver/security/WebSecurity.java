package com.apps.ws.api.resourceserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurity {

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

    http
//            .cors(a -> corsConfigurationSource())
            .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/users/status/check")
                    // .hasAuthority("SCOPE_profile")
                    .hasRole("developer")
                    // .hasAnyAuthority("ROLE_developer")
                    // .hasAnyRole("developer","user")
                    .anyRequest().authenticated())
            .oauth2ResourceServer(
                    oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

    return http.build();
  }

//  @Bean
//  public CorsConfigurationSource corsConfigurationSource() {
//    var corsConfiguration = new CorsConfiguration();
//    corsConfiguration.setAllowedOrigins(List.of("*"));
//    corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
//    corsConfiguration.setAllowedHeaders(List.of("*"));
//
//    var source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", corsConfiguration);
//
//    return source;
//  }
}
