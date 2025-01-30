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

/**
 * Configuration class for Spring Security.
 * Enables Spring Security and configures the security settings for the application.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    /**
     * Configures the HTTP security settings for the application.
     * 
     * @param http the HTTP security object to configure
     * @throws Exception if an error occurs during configuration
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* Allows public access to the image directory using GET requests. */
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());
        
        /* Calls the superclass configure method to apply the default Vaadin security configuration. */
        super.configure(http);

        /* Allows access to the LoginView. */
        setLoginView(http, LoginView.class);
    }

    /**
     * Creates a UserDetailsService bean that provides in-memory user details for testing.
     * 
     * @return the UserDetailsService bean
     */
    @Bean
    UserDetailsService users() {

        /* Creates a test user with the role "USER". */
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();

        /* Creates a test admin user with the roles "USER" and "ADMIN". */
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();

        /* Configures an in-memory user details manager with the test users. */
        return new InMemoryUserDetailsManager(user, admin);
    }
}