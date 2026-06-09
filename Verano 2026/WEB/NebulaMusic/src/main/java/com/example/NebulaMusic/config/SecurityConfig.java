package com.example.NebulaMusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/iniciar-sesion", "/registro").permitAll()
                        .requestMatchers("/crear-cuenta", "/autentication").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/index", "/index.html", "/iniciar-sesion",
                                "/iniciar-sesion.html", "/registro", "/registro.html").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}