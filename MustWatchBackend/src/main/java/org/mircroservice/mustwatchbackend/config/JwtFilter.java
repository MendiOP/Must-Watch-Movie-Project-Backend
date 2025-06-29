package org.mircroservice.mustwatchbackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mircroservice.mustwatchbackend.Authentication.JwtService;
import org.mircroservice.mustwatchbackend.Authentication.MyUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String authHeader = request.getHeader("Authorization");
         String jwtToken = null;
         String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                 username = jwtService.extractUsername(jwtToken);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = (UserDetails) applicationContext.getBean(MyUserDetailsService.class).loadUserByUsername(username);
                if(jwtService.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
    }
}
