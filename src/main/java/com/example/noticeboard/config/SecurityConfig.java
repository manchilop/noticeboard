package com.example.noticeboard.config;

import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    public InMemoryUserDetailsManager setUpUsers(){

        UserDetails akhilUser =
                User
                        .withUsername("akhil")
                        .password("akhil")
                        .roles("admin", "user")
                        .build();

        UserDetails anilUser =
                User
                        .withUsername("anil")
                        .password("anil")
                        .roles("user")
                        .build();

        return new InMemoryUserDetailsManager(akhilUser, anilUser);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
