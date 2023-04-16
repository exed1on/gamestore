package com.cdpr.gamestore_test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.builder().username("admin").password(passwordEncoder().encode("password")).roles("ADMIN").build());
        return manager;
    }

    /**
     * Configuration of security.
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF as we do not serve browser clients
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/games/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/games/update").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/games/delete").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .httpBasic();
        return http.build();
    }
}