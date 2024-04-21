package com.example.crm.views.list;

import org.springframework.context.annotation.Scope;

import com.example.crm.models.Contact;
import com.example.crm.services.CrmService;
import com.example.crm.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import jakarta.annotation.security.PermitAll;

@SpringComponent
@Scope("prototype")
@PermitAll
/*
 * ListView still matches the empty path, but now uses MainLayout as its parent.
 */
@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Flow CRM")
/*
 * The view extends VerticalLayout, which places all child components
 * vertically.
 */
public final class ListView extends VerticalLayout {

    private TextField filterText = new TextField();
    private Button addContactButton = new Button("Add Contact");

    /* The Grid component is typed with Contact. */
    Grid<Contact> grid = new Grid<>(Contact.class);

    /*
     * Creates a reference to the form so you have access to it from other methods.
     */
    ContactForm form;
    private CrmService service;

    /*
     * Auto-wire CrmService through the constructor.
     * Save it in a field, so you can access it from other methods.
     */
    public ListView(CrmService service) {

        this.service = service;
        addClassName("list-view");
        setSizeFull();

        /*
         * The grid configuration is extracted to a separate method to keep the
         * constructor easier to read.
         */
        configureGrid();

        /* Create a method for initializing the form. */
        configureForm();

        /* Add the toolbar and grid to the VerticalLayout */
        add(getToolbar(), getContent());

        /* Call updateList() once you have constructed the view. */
        updateList();

        /*
         * The closeEditor() call at the end of the constructor:
         * sets the form contact to null, clearing out old values;
         * hides the form; and removes the "editing" CSS class from the view.
         */
        closeEditor();
    }

    private void configureGrid() {

        grid.addClassNames("contact-grid");
        grid.setSizeFull();

        /* Define which properties of Contact the grid should show. */
        grid.setColumns("firstName", "lastName", "email");

        /* Define custom columns for nested objects. */
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");

        /*
         * Configure the columns to adjust automatically their size to fit their
         * content.
         */
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        /*
         * addValueChangeListener() adds a listener to the grid.
         * The Grid component supports multi- and single-selection modes.
         * You only need to select a single Contact, so you can use the asSingleSelect()
         * method.
         * The getValue() method returns the Contact in the selected row, or null if
         * there is no selection.
         */
        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private void configureForm() {

        /* Use the service to fetch companies and statuses. */
        form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
        form.setWidth("25em");

        /*
         * The save event listener calls saveContact().
         * It uses contactService to save the contact in the event to the database,
         * updates the list, and closes the editor.
         */
        form.addSaveListener(this::saveContact);

        /*
         * The delete event listener calls deleteContact().
         * In the process, it also uses contactService to delete the contact from the
         * database,
         * updates the list, and closes the editor.
         */
        form.addDeleteListener(this::deleteContact);

        /*
         * The close event listener closes the editor.
         */
        form.addCloseListener(e -> closeEditor());
    }

    private HorizontalLayout getToolbar() {

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);

        /*
         * Configure the search field to fire value-change events
         * only when the user stops typing.
         * This way you avoid unnecessary database calls, but the listener
         * is still fired without the user leaving the focus from the field.
         */
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        /* Call updateList() any time the filter changes. */
        filterText.addValueChangeListener(e -> updateList());

        /* Call addContact() when the user clicks on the "Add contact" button. */
        addContactButton.addClickListener(click -> addContact());

        /*
         * The toolbar uses a HorizontalLayout to place the TextField and Button next to
         * each other.
         */
        var toolbar = new HorizontalLayout(filterText, addContactButton);

        /*
         * Adding some class names to components makes it easier to style the
         * application later using CSS.
         */
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    /* The method returns a HorizontalLayout that wraps the form and the grid. */
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);

        /*
         * Use setFlexGrow() to specify that the Grid should have twice the space of the
         * form.
         */
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);

        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    /*
     * updateList() sets the grid items by calling the service with the value from
     * the filter text field.
     */
    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    /*
     * editContact() sets the selected contact in the ContactForm
     * and hides or shows the form, depending on the selection.
     * It also sets the "editing" CSS class name when editing.
     */
    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    /* addContact() clears the grid selection and creates a new Contact */
    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }
}