package com.example.GestionTorneos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring Security
public class SecurityConfig {

    // 1. Configuración de la cadena de filtros de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (Cross-Site Request Forgery) para facilitar pruebas con Postman
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated() // Todas las solicitudes deben estar autenticadas
                )
                .httpBasic(org.springframework.security.config.Customizer.withDefaults()); // Habilita autenticación HTTP Basic

        return http.build();
    }

    // 2. Configuración de usuarios en memoria (para un ejemplo básico)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user") // Nombre de usuario
                .password(passwordEncoder.encode("123")) // Contraseña, ¡codificada!
                .roles("USER") // Rol del usuario
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN", "USER") // Admin tiene roles ADMIN y USER
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // 3. Bean para codificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Un buen algoritmo para codificar contraseñas
    }
}