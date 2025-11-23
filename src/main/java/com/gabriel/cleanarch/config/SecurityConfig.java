package com.gabriel.cleanarch.config;

import com.gabriel.cleanarch.domain.user.User;
import com.gabriel.cleanarch.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserRepository userRepository;

    /**
     * Returns a UserDetailsService that loads users from the database by email.
     * The loaded user is then used to build a Spring Security User object.
     * The User object is built with the user's email as the username, the user's password as the password,
     * and the user's role as the authority.
     * If the user is not found, a UsernameNotFoundException is thrown.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        };
    }

    /**
     * Returns a SecurityFilterChain that disables CSRF protection and sets the session creation policy to STATELESS.
     * It also permits all requests to the /api/auth/**, /v3/api-docs/**, /swagger-ui/**, and /swagger-ui.html endpoints,
     * as well as all GET requests to the /health endpoint.
     * All other requests are authenticated using the JWT Authentication Filter.
     * @return a SecurityFilterChain
     * @throws Exception if there is an error building the SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/health").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Returns a DaoAuthenticationProvider that uses the UserDetailsService returned by userDetailsService()
     * and the PasswordEncoder returned by passwordEncoder() to authenticate users.
     * The DaoAuthenticationProvider is used by the AuthenticationManager to authenticate users.
     * @param passwordEncoder the PasswordEncoder to use for encoding and decoding passwords
     * @return a DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

        /**
         * Returns the AuthenticationManager used by the application to authenticate users.
         * The AuthenticationManager is built by the AuthenticationConfiguration.
         * @param config the AuthenticationConfiguration to use for building the AuthenticationManager
         * @return the AuthenticationManager used by the application
         * @throws Exception if there is an error building the AuthenticationManager
         */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

        /**
         * Returns a PasswordEncoder used by the application to encode and decode passwords.
         * The returned PasswordEncoder is a BCryptPasswordEncoder, which is a PasswordEncoder that uses the BCrypt algorithm
         * to encode and decode passwords.
         * @return the PasswordEncoder used by the application
         */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
