package com.example.crm.repositories;

import java.util.List;
import com.example.crm.models.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Contact entities.
 * Provides basic CRUD operations and custom search functionality.
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {

    /**
     * Searches for contacts by first name or last name.
     * The search term is matched in a case-insensitive manner.
     * 
     * @param searchTerm the term to search for
     * @return a list of matching contacts
     */
    @Query("select c from Contact c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Contact> search(@Param("searchTerm") String searchTerm);
}