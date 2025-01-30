package com.example.crm.repositories;

import com.example.crm.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Company entities.
 * Provides basic CRUD operations.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // No custom methods are defined here, 
    // but Spring Data JPA provides basic CRUD operations out of the box.
}