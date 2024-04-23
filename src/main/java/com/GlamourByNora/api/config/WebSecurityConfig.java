package com.GlamourByNora.api.config;

import com.GlamourByNora.api.jwt.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/account/update-password").authenticated()
                        .requestMatchers("/account/**").permitAll()
                        .requestMatchers("/user/getAllUsers", "/user/page-list", "/user/{id}", "/user/delete/{id}").authenticated()
                        .requestMatchers("/user/getAllUsers", "/user/page-list", "/user/{id}", "/user/delete/{id}").hasRole("Admin")
                        .requestMatchers("/user/updateInfo/{id}").authenticated()
                        .requestMatchers("/product/create-a-product", "/product/update/{id}", "/product/delete/{id}").authenticated()
                        .requestMatchers("/product/create-a-product", "/product/update/{id}", "/product/delete/{id}").hasRole("Admin")
                        .requestMatchers("/product/list-of-products", "/product/{productId}", "/product/page-list").permitAll()
                        .requestMatchers("/cart/*").permitAll()
                        .requestMatchers("/payment/checkout").permitAll()
                        .requestMatchers("/payment/**").authenticated()
                        .requestMatchers("/admin/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("Admin")
                        .requestMatchers("/order_management/**").authenticated()
                        .requestMatchers("/order_management/**").hasRole("Admin")
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