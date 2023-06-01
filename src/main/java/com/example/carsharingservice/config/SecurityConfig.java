package com.example.carsharingservice.config;

import com.example.carsharingservice.security.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST,"/register", "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/health").permitAll()
                                .requestMatchers(HttpMethod.POST, "/cars", "/rentals/{id}/return")
                                    .hasRole("MANAGER")
                                .requestMatchers(HttpMethod.POST, "/rentals", "/payments")
                                    .hasAnyRole("MANAGER", "CUSTOMER")
                                .requestMatchers(HttpMethod.GET,
                                        "/users/me",
                                        "/cars/{id}")
                                    .hasAnyRole("MANAGER", "CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/rentals", "/rentals/{id}")
                                .hasRole("MANAGER")
                                .requestMatchers(HttpMethod.GET, "/payments/success",
                                        "/payments/cancel",
                                        "/payments/success?**")
                                    .permitAll()
                                .requestMatchers(HttpMethod.GET,"/cars").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/cars/{id}").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/users/{id}/role")
                                    .hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/users/me")
                                    .hasAnyRole("MANAGER", "CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE, "/cars/{id}").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/swagger-ui.html",
                                        "/swagger-ui/", "/v3/api-docs/",
                                        "/swagger-resources/**", "/swagger-ui/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .build();
    }
}
