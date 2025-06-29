package org.mircroservice.mustwatchbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

  /**
   * Configures the main security filter chain. 1 Disables CSRF for simplicity. 2 Ensures every
   * request is authenticated. 3 Uses HTTP Basic authentication by default. 4 Configures stateless
   * session management.
   *
   * @param httpSecurity the HTTP security to configure
   * @return a built SecurityFilterChain instance
   * @throws Exception in case of configuration errors
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers("register", "login")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(
            session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
