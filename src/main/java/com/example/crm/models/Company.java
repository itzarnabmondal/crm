package com.example.crm.models;

import java.util.List;
import java.util.LinkedList;
import jakarta.persistence.Entity;
import jakarta.annotation.Nullable;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Formula;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a company entity in the CRM system.
 */
@Entity
public final class Company extends AbstractEntity {

    /**
     * The name of the company.
     * Cannot be blank.
     */
    @NotBlank
    private String name;

    /**
     * The list of employees (contacts) associated with the company.
     * Can be null.
     */
    @Nullable
    @OneToMany(mappedBy = "company")
    private List<Contact> employees = new LinkedList<>();

    /**
     * The total count of employees (contacts) associated with the company.
     * This field is calculated using a Hibernate formula.
     */
    @Formula("(select count(c.id) from Contact c where c.company_id = id)")
    private int employeesCount;

    /**
     * Returns the name of the company.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of employees (contacts) associated with the company.
     * 
     * @return the employees
     */
    public List<Contact> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees (contacts) associated with the company.
     * 
     * @param employees the employees to set
     */
    public void setEmployees(List<Contact> employees) {
        this.employees = employees;
    }

    /**
     * Returns the total count of employees (contacts) associated with the company.
     * 
     * @return the employees count
     */
    public int getEmployeeCount() {
        return employeesCount;
    }
}