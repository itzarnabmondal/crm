package com.example.crm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

/**
 * Represents a contact entity in the CRM system.
 */
@Entity
public final class Contact extends AbstractEntity {

    /**
     * The first name of the contact.
     * Cannot be empty.
     */
    @NotEmpty
    private String firstName;

    /**
     * The last name of the contact.
     * Cannot be empty.
     */
    @NotEmpty
    private String lastName;

    /**
     * The company associated with the contact.
     * Cannot be null.
     * The "employees" property of the company is ignored during JSON serialization.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties({ "employees" })
    private Company company;

    /**
     * The status of the contact.
     * Cannot be null.
     */
    @NotNull
    @ManyToOne
    private Status status;

    /**
     * The email address of the contact.
     * Must be a valid email address and cannot be empty.
     */
    @Email
    @NotEmpty
    private String email = "";

    /**
     * Returns the first name of the contact.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the contact.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the contact.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the contact.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the company associated with the contact.
     * 
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company associated with the contact.
     * 
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Returns the status of the contact.
     * 
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the contact.
     * 
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the email address of the contact.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the contact.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}