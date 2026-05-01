package com.example.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/users/login").permitAll()
            .requestMatchers("/users/register").permitAll()
            .anyRequest().authenticated()
		);
        http.csrf(csrf -> csrf.disable());

        http.formLogin(form -> form
            .loginPage("/users/login")
            .loginProcessingUrl("/users/login")
            .defaultSuccessUrl("/memos")
        );

        http.logout(logout -> logout
            .logoutSuccessUrl("/users/login")
        );

        http.httpBasic(Customizer.withDefaults());
	return http.build();
    }
}
