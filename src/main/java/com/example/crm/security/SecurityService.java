package com.example.crm.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Provides a service for managing security-related operations.
 * Allows access to the currently authenticated user and provides a method for logging out.
 */
@Component
public final class SecurityService {

    /**
     * The authentication context used to access the currently authenticated user.
     */
    private final AuthenticationContext authenticationContext;

    /**
     * Constructs a new SecurityService instance with the given authentication context.
     * 
     * @param authenticationContext the authentication context to use
     */
    public SecurityService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    /**
     * Returns the currently authenticated user as a UserDetails object.
     * 
     * @return the authenticated user
     */
    public UserDetails getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class).get();
    }

    /**
     * Logs out the currently authenticated user.
     */
    public void logout() {
        authenticationContext.logout();
    }
}