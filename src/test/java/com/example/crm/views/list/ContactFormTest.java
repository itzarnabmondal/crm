package com.example.crm.views.list;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.crm.models.Company;
import com.example.crm.models.Contact;
import com.example.crm.models.Status;

public class ContactFormTest {

    private List<Company> companies;
    private List<Status> statuses;
    private Contact contactOne;
    private Company companyOne;
    private Company companyTwo;
    private Status statusOne;
    private Status statusTwo;

    @BeforeEach
    public void setupData() {

        companies = new ArrayList<>();
        companyOne = new Company();
        companyOne.setName("IBM");
        companyTwo = new Company();
        companyTwo.setName("Oracle");
        companies.add(companyOne);
        companies.add(companyTwo);

        statuses = new ArrayList<>();
        statusOne = new Status();
        statusOne.setName("Status One");
        statusTwo = new Status();
        statusTwo.setName("Status Two");
        statuses.add(statusOne);
        statuses.add(statusTwo);

        contactOne = new Contact();
        contactOne.setFirstName("Arnab");
        contactOne.setLastName("Mondal");
        contactOne.setEmail("arnab@null.net");
        contactOne.setStatus(statusOne);
        contactOne.setCompany(companyTwo);
    }

    @Test
    public void formFieldsPopulated() {
        ContactForm form = new ContactForm(companies, statuses);
        form.setContact(contactOne);
        assertEquals("Arnab", form.firstName.getValue());
        assertEquals("Mondal", form.lastName.getValue());
        assertEquals("arnab@null.net", form.email.getValue());
        assertEquals(companyTwo, form.company.getValue());
        assertEquals(statusOne, form.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies, statuses);
        Contact contact = new Contact();
        form.setContact(contact);
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.company.setValue(companyOne);
        form.email.setValue("john@doe.com");
        form.status.setValue(statusTwo);

        AtomicReference<Contact> savedContactRef = new AtomicReference<>(null);
        form.addSaveListener(e -> {
            savedContactRef.set(e.getContact());
        });
        form.save.click();
        Contact savedContact = savedContactRef.get();

        assertEquals("John", savedContact.getFirstName());
        assertEquals("Doe", savedContact.getLastName());
        assertEquals("john@doe.com", savedContact.getEmail());
        assertEquals(companyOne, savedContact.getCompany());
        assertEquals(statusTwo, savedContact.getStatus());
    }

}