package com.nishakar.config;

import com.nishakar.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final ApplicationContext applicationContext;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtTokenAuthenticationFilter -> doFilterInternal !");
        try {
            String authHeader = request.getHeader("Authorization");
            String jwtToken = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                jwtToken = authHeader.substring(7);
                username = jwtService.extractUsername(jwtToken);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = applicationContext.getBean(UserDetailsService.class).loadUserByUsername(username);
                if(jwtService.validateJwtToken(jwtToken, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Exception caught in while authenticating the request : {}", String.valueOf(ex));
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
