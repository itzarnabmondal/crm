package com.example.crm.repositories;

import com.example.crm.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Status entities.
 * Provides basic CRUD operations.
 */
public interface StatusRepository extends JpaRepository<Status, Long> {
    // No custom methods are defined here, 
    // but Spring Data JPA provides basic CRUD operations out of the box.
}