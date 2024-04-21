package com.example.crm.security;

import com.example.crm.views.LoginView;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/* Enable Spring Security. */
@EnableWebSecurity
@Configuration
/*
 * Extend the VaadinWebSecurity class to configure Spring Security for Vaadin.
 */
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* Allows public access to the image directory using GET requests. */
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());
        super.configure(http);

        /* Allow access to LoginView. */
        setLoginView(http, LoginView.class);
    }

    @Bean
    UserDetailsService users() {

        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();

        /* Configure an in-memory users for testing */
        return new InMemoryUserDetailsManager(user, admin);
    }
}