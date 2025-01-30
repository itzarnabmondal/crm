package com.example.crm.models;

import jakarta.persistence.Entity;

/**
 * Represents a status entity in the CRM system.
 * A status can be associated with a contact.
 */
@Entity
public final class Status extends AbstractEntity {

    /**
     * The name of the status (e.g. "Lead", "Customer", "Partner").
     */
    private String name;

    /**
     * Default constructor.
     */
    public Status() {
    }

    /**
     * Constructor with a name parameter.
     * 
     * @param name the name of the status
     */
    public Status(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the status.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the status.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}