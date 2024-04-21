package com.example.crm.views;

import com.example.crm.security.SecurityService;
import com.example.crm.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

/* AppLayout is a Vaadin layout with a header and a responsive drawer. */
public final class MainLayout extends AppLayout {

    private final SecurityService securityService;

    /* Autowire the SecurityService and save it in a field. */
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Flow CRM");

        /*
         * Instead of styling the text with raw CSS, use Lumo Utility Classes shipped
         * with the default theme.
         */
        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

        String u = securityService.getAuthenticatedUser().getUsername();

        /* Create a logout button that calls the logout() method in the service. */
        Button logout = new Button("Log out " + u, e -> securityService.logout());

        /* DrawerToggle is a menu button that toggles the visibility of the sidebar. */
        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        /* Centers the components in the header along the vertical axis. */
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        /*
         * Call header.expand(logo) to make the logo take up all of the extra space in
         * the layout.
         * This can push the logout button to the far right.
         */
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);

        /*
         * Adds the header layout to the application layout’s nav bar, the section at
         * the top of the screen.
         */
        addToNavbar(header);
    }

    private void createDrawer() {
        /*
         * Wraps the router link in a VerticalLayout and adds it to the AppLayout
         * drawer.
         */
        addToDrawer(new VerticalLayout(
                /*
                 * Creates a RouterLink with the text "List" and ListView.class as the
                 * destination view.
                 */
                new RouterLink("List", ListView.class),
                new RouterLink("Dashboard", DashboardView.class)));
    }
}