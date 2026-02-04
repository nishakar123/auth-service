package com.nishakar.config;

import com.nishakar.commons.exception.UserNotFoundException;
import com.nishakar.entity.AuthUser;
import com.nishakar.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .formLogin(AbstractHttpConfigurer::disable) // Disable form login
                .httpBasic(AbstractHttpConfigurer::disable) // Disable basic auth
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth-service/v1/test/**").authenticated()
                                .anyRequest().permitAll()) // Allow all requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //This tells Spring Security not to create or use an HTTP session for storing authentication state.
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Insert my jwtTokenAuthenticationFilter right before Spring’s default username/password authentication filter.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(AuthUserRepository authUserRepository){
        return username -> authUserRepository.findByUsername(username)
                .map(user -> new User(user.getUsername(),
                        user.getPassword(),
                        getSimpleGrantedAuthority(user)
                ))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private static List<SimpleGrantedAuthority> getSimpleGrantedAuthority(AuthUser user) {
        final List<String> roles = user.getRoles();
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            simpleGrantedAuthorities.add(simpleGrantedAuthority);
        }
        return simpleGrantedAuthorities;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
