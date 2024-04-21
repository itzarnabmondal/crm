package com.example.crm.models;

import java.util.List;
import java.util.LinkedList;
import jakarta.persistence.Entity;
import jakarta.annotation.Nullable;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Formula;
import jakarta.validation.constraints.NotBlank;

@Entity
public final class Company extends AbstractEntity {

    @NotBlank
    private String name;

    @Nullable
    @OneToMany(mappedBy = "company")
    private List<Contact> employees = new LinkedList<>();

    /*
     * The Formula is a Hibernate feature that allows you to specify SQL snippets to
     * fetch special fields.
     * The query gets the count of employees without needing to fetch all of the
     * employees.
     * Note that in a larger application you’ll probably want to do this in some
     * alternative way
     * since all Company entity loads now triggers an additional SQL query,
     * even though the employeeCount field is only needed in this DashboardView
     * class.
     */
    @Formula("(select count(c.id) from Contact c where c.company_id = id)")
    private int employeesCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Contact> employees) {
        this.employees = employees;
    }

    public int getEmployeeCount() {
        return employeesCount;
    }
}