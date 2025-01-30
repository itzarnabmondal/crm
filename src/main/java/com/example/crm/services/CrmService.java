package com.example.crm.services;

import java.util.List;
import com.example.crm.models.Status;
import com.example.crm.models.Company;
import com.example.crm.models.Contact;
import org.springframework.stereotype.Service;
import com.example.crm.repositories.StatusRepository;
import com.example.crm.repositories.CompanyRepository;
import com.example.crm.repositories.ContactRepository;

/**
 * Provides business logic for managing CRM data.
 * Acts as an intermediary between the UI and the database repositories.
 */
@Service
public final class CrmService {

    /**
     * The repository for managing contact data.
     */
    private final ContactRepository contactRepository;

    /**
     * The repository for managing company data.
     */
    private final CompanyRepository companyRepository;

    /**
     * The repository for managing status data.
     */
    private final StatusRepository statusRepository;

    /**
     * Constructs a new CrmService instance with the given repositories.
     * 
     * @param contactRepository the contact repository
     * @param companyRepository the company repository
     * @param statusRepository the status repository
     */
    public CrmService(ContactRepository contactRepository, CompanyRepository companyRepository,
            StatusRepository statusRepository) {
        this.contactRepository = contactRepository;
        this.statusRepository = statusRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Saves a contact to the database.
     * 
     * @param contact the contact to save
     */
    public void saveContact(Contact contact) {
        /*
         * Ensures that a null contact is not saved to the database.
         */
        if (contact == null) {
            return;
        }
        contactRepository.save(contact);
    }

    /**
     * Retrieves a list of contacts from the database.
     * 
     * @param stringFilter the filter string to apply to the search
     * @return the list of contacts
     */
    public List<Contact> findAllContacts(String stringFilter) {
        /*
         * If no filter is applied, returns all contacts.
         * Otherwise, uses the repository to filter the contacts based on the filter string.
         */
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    /**
     * Returns the total count of contacts in the database.
     * 
     * @return the contact count
     */
    public Long countContacts() {
        return contactRepository.count();
    }

    /**
     * Deletes a contact from the database.
     * 
     * @param contact the contact to delete
     */
    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    /**
     * Retrieves a list of companies from the database.
     * 
     * @return the list of companies
     */
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * Retrieves a list of statuses from the database.
     * 
     * @return the list of statuses
     */
    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }
}