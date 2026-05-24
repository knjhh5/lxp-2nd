package com.gnjhh.lxp_2nd.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                                    "/auth/login",
                                    "/auth/signup",
                                    "/courses",
                                    "/courses/**")
                            .permitAll();
                    auth.requestMatchers(
                                    "/enrollments",
                                    "/members/me/enrollments",
                                    "/members/me/enrollments/{courseId}")
                            .authenticated();
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
