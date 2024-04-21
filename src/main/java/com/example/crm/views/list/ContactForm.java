package com.example.crm.views.list;

import java.util.List;

import com.example.crm.models.Company;
import com.example.crm.models.Contact;
import com.example.crm.models.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

/* 
 * ContactForm extends FormLayout: a responsive layout 
 *   that shows form fields in one or two columns, 
 *   depending on the viewport width.
 */
public final class ContactForm extends FormLayout {

    /* Creates all the UI components as fields in the component. */
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    /*
     * BeanValidationBinder is a Binder that’s aware of bean validation annotations.
     * By passing it in the Contact.class, you define the type of object to which
     * you’re binding.
     */
    BeanValidationBinder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    public ContactForm(List<Company> companies, List<Status> statuses) {
        addClassName("contact-form");

        /*
         * bindInstanceFields() matches fields in Contact and ContactForm based on their
         * names.
         */
        binder.bindInstanceFields(this);

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        /*
         * Adds all the UI components to the layout. The buttons require a bit of
         * extra configuration. Create and call a new method, createButtonsLayout().
         */
        add(firstName, lastName, email, company, status, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {

        /*
         * Makes the buttons visually distinct from each other using built-in theme
         * variants.
         */
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        /* Defines keyboard shortcuts: Enter to save and Escape to close the editor. */
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        /* The save button calls the validateAndSave() method. */
        save.addClickListener(event -> validateAndSave());

        /* The delete button triggers a delete event and passes the active contact. */
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));

        /* The cancel button fires a close event. */
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        /*
         * Validates the form every time it changes. If it’s invalid,
         * it disables the save button to avoid invalid submissions.
         */
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        /*
         * Returns a HorizontalLayout containing the buttons to place them next to each
         * other.
         */
        return new HorizontalLayout(save, delete, close);
    }

    public void setContact(Contact contact) {
        /*
         * Calls binder.setBean() to bind the values from the contact to the UI fields.
         * The method also adds value change listeners to update changes in the UI back
         * to the domain object.
         */
        binder.setBean(contact);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            /* Fire a save event, so the parent component can handle the action. */
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {

        private Contact contact;

        /*
         * ContactFormEvent is a common superclass for all of the events.
         * It contains the contact that was edited or deleted.
         */
        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    /*
     * The add*Listener() methods that passes the well-typed event type to Vaadin’s
     * event bus to register the custom event types.
     * Select the com.vaadin import for Registration if IntelliJ asks.
     */
    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}