package com.example.crm.views.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.crm.models.Contact;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

@SpringBootTest
public class ListViewTest {
    
    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Contact> grid = listView.grid;
        Contact firsContact = getFirstItem(grid);

        ContactForm form = listView.form;

        assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firsContact);
        assertTrue(form.isVisible());
        assertEquals(firsContact.getFirstName(), form.firstName.getValue());
    }

    @SuppressWarnings("unchecked")
    private Contact getFirstItem(Grid<Contact> grid) {
        return( (ListDataProvider<Contact>) grid.getDataProvider()).getItems().iterator().next();
    }
}