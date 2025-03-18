package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/home/**").hasRole("USER")
                        .requestMatchers("/admin/posts/**", "/admin/posts","/admin/users", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/home","/login", "/register", "/logout","/css/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            System.out.println("User Roles: " + authentication.getAuthorities());
                            boolean isAdmin = authentication.getAuthorities().stream()
                                            .anyMatch(auth -> auth.getAuthority().equals("ADMIN") || auth.getAuthority().equals("ROLE_ADMIN"));

                            String redirectUrl = isAdmin ? "/admin" : "/home";
                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll()
                )
                .logout(config -> config
                        .logoutSuccessUrl("/home"));

        return http.build();
    }


}

