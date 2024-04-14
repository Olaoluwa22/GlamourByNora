package com.GlamourByNora.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/account/update-password").authenticated()
                        .requestMatchers("/account/**").permitAll()
                        .requestMatchers("/user/getAllUsers", "/user/page-list", "/user/{id}", "/user/delete/{id}").authenticated()
                        .requestMatchers("/user/getAllUsers", "/user/page-list", "/user/{id}", "/user/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/user/updateInfo/{id}").authenticated()
                        .requestMatchers("/product/create-a-product", "/product/update/{id}", "/product/delete/{id}").authenticated()
                        .requestMatchers("/product/create-a-product", "/product/update/{id}", "/product/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/product/list-of-products", "/product/{productId}", "/product/page-list").permitAll()
                        .requestMatchers("/cart/add-to-cart", "/cart/delete-from-cart").permitAll()
                        .requestMatchers("/cart/checkout").authenticated()
                        .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
