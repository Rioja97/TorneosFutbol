package com.example.GestionTorneos.config;

import com.example.GestionTorneos.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // ¡Importante!
import org.springframework.security.config.http.SessionCreationPolicy; // Importa SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring Security
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // Spring inyectará JwtAuthFilter automáticamente
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF: necesario para JWT sin sesiones
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT es stateless
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (no requieren autenticación)
                        .requestMatchers("/auth/**").permitAll()

                        // Rutas que requieren rol ADMIN o ESPECTADOR
                        .requestMatchers("/partido/**", "/estadistica/**").hasAnyRole("ADMIN", "ESPECTADOR")

                        // Rutas que requieren solo rol ADMIN
                        .requestMatchers("/equipo/**", "/jugador/**", "/torneo/**", "/entrenador/**").hasRole("ADMIN")

                        // Cualquier otra solicitud requiere autenticación (si no coincide con las anteriores)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Agrega tu filtro JWT

        return http.build();
    }

    // NOTA: No necesitamos PasswordEncoder o UserDetailsService aquí porque
    // la autenticación inicial (login) y la gestión de usuarios la haremos con nuestro AuthController
    // y la validación de tokens la hace JwtAuthFilter.
}