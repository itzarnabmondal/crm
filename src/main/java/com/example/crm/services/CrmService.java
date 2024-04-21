package com.example.crm.services;

import java.util.List;
import com.example.crm.models.Status;
import com.example.crm.models.Company;
import com.example.crm.models.Contact;
import org.springframework.stereotype.Service;
import com.example.crm.repositories.StatusRepository;
import com.example.crm.repositories.CompanyRepository;
import com.example.crm.repositories.ContactRepository;

/* 
 * The @Service annotation makes this a 
 * Spring-managed service that you can inject into your view.
 */
@Service
public final class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    /* Use Spring constructor injection to auto-wire the database repositories. */
    public CrmService(ContactRepository contactRepository, CompanyRepository companyRepository,
            StatusRepository statusRepository) {
        this.contactRepository = contactRepository;
        this.statusRepository = statusRepository;
        this.companyRepository = companyRepository;
    }

    public void saveContact(Contact contact) {
        /*
         * Service classes often include validation and other business rules before
         * persisting data.
         * You check here that you aren’t trying to save a null object.
         */
        if (contact == null) {
            return;
        }
        contactRepository.save(contact);
    }

    public List<Contact> findAllContacts(String stringFilter) {
        /*
         * Check if there’s an active filter: return either all contacts,
         * or use the repository to filter based on the string.
         */
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public Long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }
}